/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.careerly.common.support.msgconverter;

import com.google.common.base.Charsets;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.io.IOException;

/**
 * 实现描述：指定Content-Type的StringHttpMessageConverter
 */
public class PlainStringHttpMessageConverter extends StringHttpMessageConverter {

    private static final MediaType CONTENT_TYPE = new MediaType("text", "plain", Charsets.UTF_8);

    public PlainStringHttpMessageConverter() {
        super(Charsets.UTF_8);
        setWriteAcceptCharset(false);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.http.converter.StringHttpMessageConverter#writeInternal(java.lang.String,
     * org.springframework.http.HttpOutputMessage)
     */
    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
        outputMessage.getHeaders().setContentType(CONTENT_TYPE);
        super.writeInternal(s, outputMessage);
    }

}
