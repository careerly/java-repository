/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.careerly.common.support.msgconverter;

import com.careerly.common.support.response.JsonpResponse;
import com.careerly.utils.JsonUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * 实现描述：Jsonp协议的消息转换器
 */
public class JsonpHttpMessageConverter extends AbstractHttpMessageConverter<JsonpResponse> {

    public JsonpHttpMessageConverter() {
        super(new MediaType("application", "javascript", Charset.forName("UTF-8")));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.http.converter.AbstractHttpMessageConverter#supports(java.lang.Class)
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        return JsonpResponse.class.isAssignableFrom(clazz);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.http.converter.AbstractHttpMessageConverter#readInternal(java.lang.Class,
     * org.springframework.http.HttpInputMessage)
     */
    @Override
    protected JsonpResponse readInternal(Class<? extends JsonpResponse> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.http.converter.AbstractHttpMessageConverter#writeInternal(java.lang.Object,
     * org.springframework.http.HttpOutputMessage)
     */
    @Override
    protected void writeInternal(JsonpResponse t, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {

        ServletServerHttpResponse response = (ServletServerHttpResponse) outputMessage;
        response.getServletResponse().setCharacterEncoding("UTF-8");
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(outputMessage.getBody(), Charset.forName("UTF-8")));
        try {
            if (StringUtils.isNotBlank(t.getCallback())) {
                bw.write(StringEscapeUtils.escapeHtml(t.getCallback()));
                bw.write("(");
            }
            bw.write(JsonUtils.marshalToString(t.getJson()));
            if (StringUtils.isNotBlank(t.getCallback())) {
                bw.write(")");
            }
        } finally {
            bw.close();
        }
    }

}
