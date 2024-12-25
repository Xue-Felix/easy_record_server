package com.misu.easy_record_server.common;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author x
 *         泛型类，T用于表示具体的数据类型，可以根据实际返回的数据灵活指定类型
 */
public class ResponseResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 状态码，例如200表示成功，400表示客户端请求错误等
    private final int code;
    // 新增枚举类型的字段，用于表示响应状态，使语义更清晰
    private final ResponseStatus status;
    // 提示信息，描述请求结果的情况
    private String message;
    // 具体的数据内容，使用泛型可以存放各种类型的数据（对象、列表等）
    private final T data;

    // 私有构造方法，用于在类内部创建实例
    private ResponseResult(int code, ResponseStatus status, String message, T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 成功的响应结果，无具体数据返回时（例如删除操作成功）
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(200, ResponseStatus.SUCCESS, "操作成功", null);
    }

    // 成功的响应结果，带有具体数据返回时
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, ResponseStatus.SUCCESS, "操作成功", data);
    }

    // 自定义状态码和消息的成功响应结果，带有具体数据返回时
    public static <T> ResponseResult<T> success(int code, String message, T data) {
        return new ResponseResult<>(code, ResponseStatus.SUCCESS, message, data);
    }

    // 失败的响应结果，无具体数据返回时，根据不同的失败原因传入对应的状态枚举值
    public static <T> ResponseResult<T> fail(ResponseStatus status, String message) {
        int code = getCodeByStatus(status);
        return new ResponseResult<>(code, status, message, null);
    }

    // 根据响应状态枚举获取对应的状态码，可根据实际情况扩展更多映射关系
    private static int getCodeByStatus(ResponseStatus status) {
        return switch (status) {
            case SUCCESS -> 200;
            case FAILURE, BAD_REQUEST -> 400;
            case PARTIAL_SUCCESS -> 207;
            case NOT_FOUND -> 404;
            case UNAUTHORIZED -> 401;
            case FORBIDDEN -> 403;
            default -> 500;
        };
    }

    // 生成必要的Getter方法
    public int getCode() {
        return code;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}