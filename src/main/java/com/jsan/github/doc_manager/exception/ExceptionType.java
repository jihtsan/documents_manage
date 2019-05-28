package com.jsan.github.doc_manager.exception;

/**
 * 具体业务异常枚举类
 *
 * @author oem
 */
public enum ExceptionType {

    PARAM_EMPTY("110001", "参数不能为空"),
    PARAM_ERROR("110002", "参数格式错误");

    private String code;
    private String message;
    private int level;

    ExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ExceptionType(String code, String message, int level) {
        this.code = code;
        this.message = message;
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getLevel() {
        return level;
    }
}


