package com.misu.easy_record_server.handler;

import com.misu.easy_record_server.common.ResponseResult;
import com.misu.easy_record_server.common.ResponseStatus;
import com.misu.easy_record_server.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseResult<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常：code={}, message={}", e.getCode(), e.getMessage());
        return ResponseResult.fail(ResponseStatus.FAILURE, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleException(Exception e) {
        log.error("系统异常：", e);
        return ResponseResult.fail(ResponseStatus.FAILURE, "系统异常，请联系管理员");
    }
}