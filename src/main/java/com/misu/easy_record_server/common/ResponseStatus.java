package com.misu.easy_record_server.common;

/**
 * @author x
 */

public enum ResponseStatus {
    SUCCESS("Success"),
    FAILURE("Failure"),
    PARTIAL_SUCCESS("部分操作成功"),
    NOT_FOUND("资源未找到"),
    BAD_REQUEST("请求参数错误"),
    UNAUTHORIZED("未授权"),
    FORBIDDEN("禁止访问"),
    INTERNAL_SERVER_ERROR("服务器内部错误");

    private final String description;

    ResponseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
