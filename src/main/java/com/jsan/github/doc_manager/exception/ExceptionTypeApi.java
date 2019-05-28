package com.jsan.github.doc_manager.exception;

/**
 * Enum Api的响应类型
 *
 * @author hua xu
 * @since 1.0.0
 */
public enum ExceptionTypeApi {
    /**
     * 成功
     */
    SUCCESS("00", "success"),

    /**
     * 异常
     */
    HTTP_ERROR("320001", "HTTP请求错误");

    private String code;
    private String message;

    ExceptionTypeApi(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
