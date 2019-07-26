package com.tennetcn.free.core.exception;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-26 12:17
 * @comment
 */

public class BizException extends RuntimeException {
    public BizException(String code, Object... params) {
        this.code = code;
        this.params = params;
    }


    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
    }


    public BizException(String code) {
        this(code, new Object[0]);
    }

    private String code;
    private Object[] params;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getParameters() {
        return this.params;
    }
}