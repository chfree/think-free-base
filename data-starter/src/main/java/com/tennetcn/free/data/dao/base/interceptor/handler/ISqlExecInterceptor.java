package com.tennetcn.free.data.dao.base.interceptor.handler;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-02-19 15:14
 * @comment
 */

public interface ISqlExecInterceptor {
    void execCall(long timing,String statementId,String sql);

    void execTimeoutCall(long timing,String statementId,String sql);
}
