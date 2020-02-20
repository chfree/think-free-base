package com.tennetcn.free.ratelimit.exception;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-20 21:45
 * @comment
 */

@Data
public class RateLimitException extends RuntimeException {

    /**
     * 限流异常
     */
    protected static int defaultErrorCode = 998;

    protected static String defaultMsg = "系统繁忙，请稍后再试";

    private int code;

    private Object[] params;

    public RateLimitException(Object... params) {
        this(defaultErrorCode, params);
    }

    public RateLimitException(String msg) {
        this(defaultErrorCode, msg);
    }

    public RateLimitException(int code, Object... params) {
        this.code = code;
        this.params = params;
    }

    public RateLimitException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public RateLimitException(int code) {
        this(code,defaultMsg);
    }

    public RateLimitException() {
        this(defaultErrorCode,defaultMsg);
    }
}
