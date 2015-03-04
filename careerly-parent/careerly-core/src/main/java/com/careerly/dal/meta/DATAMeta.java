/*
 * Copyright (c) JForum Team. All rights reserved.
 * 
 * The software in this package is published under the terms of the LGPL 
 * license a copy of which has been included with this distribution in the 
 * license.txt file.
 * 
 * The JForum Project
 * http://www.jforum.net
 */
package com.careerly.dal.meta;


import java.io.Serializable;

/**
 * 实现描述: 基本的数据接口
 *
 * @author shengyahuang
 * @version v1.0.0
 * @see
 * @since 15-3-2 下午6:19
 */
public interface DATAMeta<T> {

    /**
     * 获取一个实例对象
     *
     * @param c
     * @param id
     * @return
     */
    public T get(Class<T> c,Serializable id);

    /**
     * 增加一个实例对象
     *
     * @param entity
     */
    public void add(T entity);

    /**
     * 移除一个实例对象
     *
     * @param entity
     */
    public void remove(T entity);

    /**
     * 更新一个实例对象
     *
     * @param entity
     */
    public void update(T entity);
}
