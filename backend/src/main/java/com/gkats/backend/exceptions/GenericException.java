package com.gkats.backend.exceptions;

public class GenericException extends RuntimeException {
    private String errorCode;

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GenericException(String message, String errorCode, Throwable e) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
