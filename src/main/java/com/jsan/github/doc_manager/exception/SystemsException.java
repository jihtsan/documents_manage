package com.jsan.github.doc_manager.exception;



/**
 * 业务异常-不方便对外
 *
 * @author xuhua
 * @since 1.0.0
 */
public class SystemsException extends RuntimeException {
    private String code;

    private String message;

    public SystemsException(ExceptionTypeApi exceptionTypeApi) {
        this.code = exceptionTypeApi.getCode();
        this.message = exceptionTypeApi.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
