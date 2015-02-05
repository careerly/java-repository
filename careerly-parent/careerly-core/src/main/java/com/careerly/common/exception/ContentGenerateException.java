package com.careerly.common.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class ContentGenerateException extends NestableRuntimeException {

    private static final long serialVersionUID = 1L;

    public ContentGenerateException() {
        super();
    }

    public ContentGenerateException(String message) {
        super(message);
    }

    public ContentGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentGenerateException(Throwable cause) {
        super(cause);
    }

}
