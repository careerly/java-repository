/*
 * Copyright 2014 Qunar.com All right reserved. This software is the
 * confidential and proprietary information of Qunar.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Qunar.com.
 */
package com.careerly.common.support;

import com.careerly.common.thread.NamedThreadFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.*;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 实现描述：配置文件发生变化后，动态reload
 *
 * @author chaoyi.he
 * @version v1.0.0
 * @see 
 * @since 2014年1月6日 下午2:19:43
 */
public class ReloadablePropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer implements
        BeanFactoryAware, InitializingBean, DisposableBean, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ReloadablePropertySourcesPlaceholderConfigurer.class);
    private AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor;
    private ConfigurableListableBeanFactory beanFactory;
    private PropertyStringValueResolver valueResolver;
    private Resource[] locations;
    private Map<Resource, Long> lastModifiedResources = Maps.newHashMap();
    private List<String> beanNames = Lists.newArrayList();
    private ScheduledExecutorService scheduler;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void setLocations(Resource[] locations) {
        super.setLocations(locations);
        this.locations = locations;
    }

    public void setAutowiredAnnotationBeanPostProcessor(
            AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor) {
        this.autowiredAnnotationBeanPostProcessor = autowiredAnnotationBeanPostProcessor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notEmpty(locations, "properties reload, locations not given");
        // 启动时记录所有配置文件的最近修改时间
        for (Resource resource : locations) {
            long modified = resource.lastModified();
            String modifiedTime = new DateTime(modified).toString("yyyy-MM-dd HH:mm:ss");
            logger.info("properties monitor, file: {}, modified: {}", resource.getFile().getPath(), modifiedTime);
            lastModifiedResources.put(resource, modified);
        }
        // 只动态加载以Constant结尾的常量类
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            if (beanName.endsWith("Constant")) {
                logger.info("properties monitor, bean: {}", beanName);
                beanNames.add(beanName);
            }
        }
        // 启动定时任务，每10秒检查一次。以后升级JDK7，可以使用nio2 Watch Service
        scheduler = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("properties-reload"));
        scheduler.scheduleWithFixedDelay(this, 120L, 10L, TimeUnit.SECONDS);
    }

    @Override
    public void destroy() throws Exception {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        try {
            boolean changed = false;
            for (Resource resource : lastModifiedResources.keySet()) {
                long lastModified = lastModifiedResources.get(resource);
                long modified = resource.lastModified();
                if (modified > lastModified) {
                    String modifiedTime = new DateTime(modified).toString("yyyy-MM-dd HH:mm:ss");
                    logger.info("properties changed, file: {}, modified: {}", resource.getFile().getPath(),
                            modifiedTime);
                    changed = true;
                    lastModifiedResources.put(resource, modified);
                }
            }
            // 只要有一个配置文件变化，就开始重新加载
            if (changed) {
                reload();
            }
        } catch (IOException e) {
            logger.error("properties reload failure", e);
        }
    }

    private void reload() throws IOException {
        logger.info("properties changed, reloading..");

        // 构造一个Properties源
        Properties properties = super.mergeProperties();
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addLast(new PropertiesPropertySource(LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME, properties));
        ConfigurablePropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySources);
        // 由于BeanFactory不支持removeEmbeddedValueResolver，只能将老Resolver过期，再新加一个
        valueResolver.expire();
        valueResolver = new PropertyStringValueResolver(propertyResolver);
        beanFactory.addEmbeddedValueResolver(valueResolver);

        // 触发重新加载动作
        for (String beanName : beanNames) {
            Object bean = beanFactory.getBean(beanName);
            autowiredAnnotationBeanPostProcessor.processInjection(bean);
        }
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
            ConfigurablePropertyResolver propertyResolver) throws BeansException {
        // 重载父类方法，修改ValueResolver为自定义的，支持expire操作的
        valueResolver = new PropertyStringValueResolver(propertyResolver);
        doProcessProperties(beanFactoryToProcess, valueResolver);
    }

    private class PropertyStringValueResolver implements StringValueResolver {

        private PropertyResolver propertyResolver;
        private boolean expired = false;

        public PropertyStringValueResolver(PropertyResolver propertyResolver) {
            this.propertyResolver = propertyResolver;
        }

        @Override
        public String resolveStringValue(String strVal) {
            if (expired) {
                return strVal;
            }
            String resolved = ignoreUnresolvablePlaceholders ? propertyResolver.resolvePlaceholders(strVal)
                    : propertyResolver.resolveRequiredPlaceholders(strVal);
            return (resolved.equals(nullValue) ? null : resolved);
        }

        public void expire() {
            expired = true;
        }

    }

}
