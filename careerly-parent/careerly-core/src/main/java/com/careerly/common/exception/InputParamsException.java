/*
 * Copyright 2014 Qunar.com All right reserved. This software is the
 * confidential and proprietary information of Qunar.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Qunar.com.
 */
package com.careerly.common.exception;

import java.util.Arrays;
import java.util.List;

/**
 * 实现描述：输入参数错误
 */
public class InputParamsException extends RuntimeException {

    private static final long serialVersionUID = -2367893781492397451L;
    private final List<String> fields;

    public InputParamsException(String message, String... fields) {
        super(message);
        this.fields = Arrays.asList(fields);
    }

    public List<String> getFields() {
        return fields;
    }

}
