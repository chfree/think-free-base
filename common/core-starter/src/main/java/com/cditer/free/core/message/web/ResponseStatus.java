package com.cditer.free.core.message.web;

public class ResponseStatus {
    public static final String STATUS_KEY="status";

    //请求成功
    public static final int SUCCESS=200;

    //签名异常
    public static final int SIGNATURE_ERROR=401;

    //服务器异常
    public static final int SERVER_ERROR=1000;
}
