package com.tennetcn.free.core.exception;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-26 12:17
 * @comment
 */

public class BizException extends RuntimeException {
    protected static int defaultErrorCode = 1998;
    public BizException(Object... params) {
        this(defaultErrorCode,params);
    }

    public BizException(String msg) {
        this(defaultErrorCode,msg);
    }

    public BizException(int code, Object... params) {
        this.code = code;
        this.params = params;
    }


    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }


    public BizException(int code) {
        this(code, new Object[0]);
    }

    private int code;
    private Object[] params;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object[] getParameters() {
        return this.params;
    }
}