package com.tennetcn.free.web.message;

public class WebResponseStatus {
    public static final String STATUS_KEY="status";

    //请求成功
    public static final String SUCCESS="200";

    //授权失败
    public static final String AUTHORIZE_ERROR="999";

    //服务器异常
    public static final String SERVER_ERROR="1000";

    //数据异常
    public static final String DATA_ERROR="1999";
}
