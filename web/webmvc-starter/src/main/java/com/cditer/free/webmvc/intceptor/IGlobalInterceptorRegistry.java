package com.cditer.free.webmvc.intceptor;

import org.springframework.web.servlet.HandlerInterceptor;

public interface IGlobalInterceptorRegistry {
    HandlerInterceptor getHandlerInterceptor();

    int getOrder();

    String getPatterns();
}
