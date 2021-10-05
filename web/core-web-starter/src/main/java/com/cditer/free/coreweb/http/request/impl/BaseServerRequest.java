package com.cditer.free.coreweb.http.request.impl;

import com.cditer.free.coreweb.http.request.IBaseServerRequest;
import com.cditer.free.coreweb.http.request.IServerRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-09 12:24
 * @comment
 */

@Component
public class BaseServerRequest implements IBaseServerRequest {

    @Autowired
    IServerRequestHelper serverRequestHelper;

    @Override
    public IServerRequestHelper getServerRequestHelper() {
        return serverRequestHelper;
    }
}
