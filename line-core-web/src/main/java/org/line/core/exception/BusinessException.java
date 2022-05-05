package org.line.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: yangcs
 * @Date: 2020/4/14 15:43
 * @Description: 自定义业务异常;
 *  业务逻辑校验失败抛出,由BaseExceptionController  @ExceptionHandler(Exception.class) 方法捕捉 并组装成json 信息返回
 */
@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException {
    private String code;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }
}
