/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.careerly.common.support.resolver;

import com.careerly.common.exception.BusinessException;
import com.careerly.common.support.response.StandardJsonObject;
import com.careerly.utils.JsonUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * <pre>
 * 实现描述：
 * 1.如果是数据接口（Data，API）
 * 1.1.出现业务异常（BusinessException），便将业务错误封装为message做为一个ErrorJsonObject返回
 * 1.2.如果是非业务异常（除了BusinessException以外的Exception），则返回一个默认的"api error"的ErrorJsonObject返回
 * 2.如果是页面跳转（Page）
 * 2.1.出现BusinessException便跳转到带有业务错误信息的error页面
 * 2.2.如果是非业务异常（除了BusinessException以外的Exception），则返回一个默error页面
 * </pre>
 */
public class ControllerExceptionResolver extends SimpleMappingExceptionResolver {

    private static final String API_CONTROLLER = "ApiController";
    private static final String DATA_CONTROLLER = "DataController";
    private final static Logger logger = LoggerFactory.getLogger(ControllerExceptionResolver.class);

    @Override
    public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                           Exception ex) {

        ControllerExceptionResolver.logger.error(
                String.format("ERROR ## [%s] happend error,the trace is ", request.getServletPath()), ex);


        String fullClazzName = StringUtils.substringAfterLast(handler.getClass().getName(), ".");
        String clazzName = StringUtils.substringBefore(fullClazzName, "$");

        if (clazzName.endsWith(ControllerExceptionResolver.DATA_CONTROLLER)
                || clazzName.endsWith(ControllerExceptionResolver.API_CONTROLLER)) /** 如果是数据接口（Data，API） **/ {
            if (ex instanceof BusinessException) /** 出现业务异常（BusinessException），便将业务错误封装为message做为一个ErrorJsonObject返回 **/ {
                BusinessException be = (BusinessException) ex;
                resolveDataException(request, response, handler,
                        StandardJsonObject.newErrorJsonObject(be.getErrorCode(), be.getLocalizedMessage()));
            } else/** 如果是非业务异常（除了BusinessException以外的Exception），则返回一个默认的"api error"的ErrorJsonObject返回 **/ {
                resolveDataException(request, response, handler,
                        StandardJsonObject.newErrorJsonObject("抱歉，您请求的数据出错啦!"));
            }

            return null;
        } else /** 如果是页面跳转（Page） **/ {
            if (ex instanceof BusinessException)/** 出现BusinessException便跳转到带有业务错误信息的error页面 **/ {
                ModelAndView mv = new ModelAndView();
                mv.addObject("errMsg", ex.getLocalizedMessage());
                mv.setViewName("views/error");
                return mv;
            } else /** 如果是非业务异常（除了BusinessException以外的Exception），则返回一个默error页面 **/ {
                return super.doResolveException(request, response, handler, ex);
            }
        }

    }

    /**
     * @param request
     * @param response
     * @param handler
     * @return
     */
    private void resolveDataException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                      StandardJsonObject errorJsonObject) {
        try {
            String data = null;
            String callbackValue = request.getParameter("callback");
            if (StringUtils.isNotBlank(callbackValue)) {
                StringBuilder builder = new StringBuilder();
                builder.append(StringEscapeUtils.escapeHtml(callbackValue)).append("(");
                builder.append(JsonUtils.marshalToString(errorJsonObject));
                builder.append(")");
                data = builder.toString();
            } else {
                data = JsonUtils.marshalToString(errorJsonObject);
            }

            response.setContentType("application/json;charset=UTF-8");
            Writer writer = response.getWriter();
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            ControllerExceptionResolver.logger.error("ERROR ## write message happened error, the trace ", e);
        }
    }

}
