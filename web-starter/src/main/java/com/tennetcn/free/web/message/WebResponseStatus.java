package com.tennetcn.free.web.message;

public class WebResponseStatus {
    public static final String STATUS_KEY="status";

    //请求成功
    public static final int SUCCESS=200;

    //授权失败
    public static final int AUTHORIZE_ERROR=999;

    //服务器异常
    public static final int SERVER_ERROR=1000;

    //数据异常
    public static final int DATA_ERROR=1999;

    //数据异常
    public static final int DATA_NULL_ERROR=1998;
}
