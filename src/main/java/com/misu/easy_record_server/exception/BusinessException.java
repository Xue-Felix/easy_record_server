package com.misu.easy_record_server.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;
    private final String message;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public static BusinessException of(String code, String message) {
        return new BusinessException(code, message);
    }
}