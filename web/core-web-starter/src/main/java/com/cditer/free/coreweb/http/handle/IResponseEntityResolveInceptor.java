package com.cditer.free.coreweb.http.handle;

import com.cditer.free.coreweb.http.entity.ResponseEntityEx;

public interface IResponseEntityResolveInceptor {
    int getOrder();

    <T> void resovle(ResponseEntityEx<T> exchangeEx);
}
