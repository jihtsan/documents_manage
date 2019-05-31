
package com.jsan.github.doc_manager.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author xuhua
 * @since 1.0.0
 */
@Data
public class BusinessException extends RuntimeException {


    public final String defCode = "999";


    public final String code;
    public final String msg;

    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = defCode;
        this.msg = msg;
    }

}
