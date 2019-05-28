
package com.jsan.github.doc_manager.exception;



/**
 * 业务异常
 *
 * @author xuhua
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    private String code = "100001";

    private String message;

    private int level;

    public BusinessException(ExceptionType exceptionType) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.level = exceptionType.getLevel();
    }

    public BusinessException(String code, String message, int level) {
        this.code = code;
        this.message = message;
        this.level = level;
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getLevel() {
        return level;
    }

}
