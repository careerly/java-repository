/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.careerly.common.support.msgconverter;

import com.careerly.common.support.response.CsvResponse;
import com.google.common.collect.Iterables;
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
import java.util.Collections;
import java.util.List;

/**
 * 实现描述：Spring MVC用，csv响应转换器
 */
public class CsvHttpMessageConverter extends AbstractHttpMessageConverter<CsvResponse> {

    private String seperator = ";";

    public CsvHttpMessageConverter() {
        super(new MediaType("application", "vnd.ms-excel", Charset.forName("UTF-8")));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.http.converter.AbstractHttpMessageConverter#readInternal(java.lang.Class,
     * org.springframework.http.HttpInputMessage)
     */
    @Override
    protected CsvResponse readInternal(Class<? extends CsvResponse> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException();
    }

    public void setSeperator(String seperator) {
        this.seperator = seperator;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.http.converter.AbstractHttpMessageConverter#supports(java.lang.Class)
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        return CsvResponse.class.isAssignableFrom(clazz);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.http.converter.AbstractHttpMessageConverter#writeInternal(java.lang.Object,
     * org.springframework.http.HttpOutputMessage)
     */
    @Override
    protected void writeInternal(CsvResponse t, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        ServletServerHttpResponse response = (ServletServerHttpResponse) outputMessage;
        response.getServletResponse().setCharacterEncoding("UTF-8");
        outputMessage.getHeaders().set("Content-Disposition", "attachment; filename=\"" + t.getFileName() + "\"");
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(outputMessage.getBody(), Charset.forName("UTF-8")));
        try {
            for (List<String> line : Iterables.concat(Collections.singleton(t.getHead()), t.getBody())) {
                for (int i = 0; i < line.size(); i++) {
                    if (line.get(i) != null) {
                        bw.write("\"");
                        bw.write(line.get(i));
                        bw.write("\"");
                    }
                    if (i < line.size() - 1) {
                        bw.write(seperator);
                    }
                }
                bw.newLine();
            }
        } finally {
            bw.close();
        }
    }

}
