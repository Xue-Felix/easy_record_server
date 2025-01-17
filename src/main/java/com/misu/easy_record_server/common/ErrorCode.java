package com.misu.easy_record_server.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ROOM_NOT_FOUND("ROOM001", "房间不存在"),
    ROOM_NOT_AVAILABLE("ROOM002", "房间不可入住"),
    ROOM_ALREADY_OCCUPIED("ROOM003", "房间已被占用"),
    ROOM_NOT_OCCUPIED("ROOM004", "房间未被入住"),
    INVALID_ROOM_STATUS("ROOM005", "无效的房间状态"),
    GUEST_INFO_INCOMPLETE("ROOM006", "客人信息不完整"),
    INVALID_ID_CARD("ROOM007", "无效的身份证号"),
    INVALID_PHONE("ROOM008", "无效的手机号"),
    CHECKOUT_TIME_INVALID("ROOM009", "退房时间无效"),
    CHANGE_ROOM_FAILED("ROOM010", "换房失败"),
    RECORD_NOT_FOUND("ROOM011", "入住记录不存在"),
    OPERATION_NOT_ALLOWED("ROOM012", "操作不允许");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}