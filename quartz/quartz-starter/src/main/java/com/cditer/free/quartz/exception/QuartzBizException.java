package com.cditer.free.quartz.exception;

import com.cditer.free.core.exception.BizException;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-22 16:00
 * @comment
 */

public class QuartzBizException extends BizException {
    protected static int defaultErrorCode = 1997;
    public QuartzBizException(Object... params) {
        super(defaultErrorCode,params);
    }
}
