/*
 * Copyright 2014 Qunar.com All right reserved. This software is the
 * confidential and proprietary information of Qunar.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Qunar.com.
 */
package com.careerly.common.support.msgconverter;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import java.io.IOException;

/**
 * 实现描述：是否启用json格式化，便于调试
 */
public class JacksonHttpMessageConverter extends MappingJacksonHttpMessageConverter {

    //是否需要格式化json[true,false]
    private boolean prettyPrinting;

    public void setPrettyPrinting(boolean prettyPrinting) {
        this.prettyPrinting = prettyPrinting;
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {

        if (!prettyPrinting) {
            super.writeInternal(object, outputMessage);
            return;
        }

        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        JsonGenerator jsonGenerator = getObjectMapper().getJsonFactory()
                .createJsonGenerator(outputMessage.getBody(), encoding).useDefaultPrettyPrinter();
        try {
            getObjectMapper().writeValue(jsonGenerator, object);
        } catch (IOException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

}
