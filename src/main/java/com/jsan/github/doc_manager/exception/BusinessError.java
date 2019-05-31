package com.jsan.github.doc_manager.exception;

/**
 * description
 * create on 2019-04-25
 *
 * @author yang_yue
 * @since version
 */
public enum BusinessError {
    AuthFail("100", "认证失败"),
    RuntimeFail("101", "认证失败");

    public final String code;
    public final String desc;

    BusinessError(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public BusinessException exception(Object ...msgs) {
        StringBuilder sb = new StringBuilder(desc);
        sb.append(";[");
        for (Object msg : msgs) {sb.append(sb);}
        sb.append("]");
        return new BusinessException(code, sb.toString());
    }

    public BusinessException exception(String msg) {
        StringBuilder sb = new StringBuilder(desc);
        sb.append(";[");
        sb.append(msg);
        sb.append("]");
        return new BusinessException(code, sb.toString());
    }
}
