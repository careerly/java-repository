/*
 * Copyright 2013 Qunar.com All right reserved. This software is the
 * confidential and proprietary information of Qunar.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Qunar.com.
 */
package com.careerly.common.support.response;


/**
 * 实现描述：JSONP协议返回值
 */
public class JsonpResponse {

    private final String callback;
    private final StandardJsonObject json;

    public JsonpResponse(String callback, StandardJsonObject json) {
        this.callback = callback;
        this.json = json;
    }

    /**
     * @return the callback
     */
    public String getCallback() {
        return callback;
    }

    /**
     * @return the json
     */
    public StandardJsonObject getJson() {
        return json;
    }

}
