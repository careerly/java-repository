/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.careerly.common.exception;

/**
 * 实现描述：针对数据库中获得的实体对象，虽然数据库级别没有明显的1对1，1对多设计，但是业务上存在1对1,1对多的ER设计，那么违反这种约束拿出来的实体，则报这个错。
 */
public class EntityRelationMistakeException extends RuntimeException {

    private static final long serialVersionUID = 6277430338587059982L;

    public EntityRelationMistakeException() {
        super();
    }

    public EntityRelationMistakeException(String message) {
        super(message);
    }

    public EntityRelationMistakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityRelationMistakeException(Throwable cause) {
        super(cause);
    }

}
