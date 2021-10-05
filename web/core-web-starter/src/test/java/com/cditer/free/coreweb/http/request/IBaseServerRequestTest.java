package com.cditer.free.coreweb.http.request;

import com.cditer.free.coreweb.BaseTest;
import com.cditer.free.coreweb.TestContant;
import com.cditer.free.coreweb.http.entity.RequestEntityEx;
import com.cditer.free.coreweb.http.entity.RequestEntityExBuilder;
import com.cditer.free.coreweb.http.entity.ResponseEntityEx;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

public class IBaseServerRequestTest  extends BaseTest {

    private final String host = TestContant.hostPrefix + "/home/";

    @Autowired
    IBaseServerRequest baseServerRequest;

    @Test
    public void getServerRequestHelper() {
        IServerRequestHelper serverRequestHelper = baseServerRequest.getServerRequestHelper();
        final String indexUrl = host + "index";
        RequestEntityEx indexRquest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.GET)
                .setUrl(indexUrl).build();

        ResponseEntityEx<String> request = serverRequestHelper.request(indexRquest, String.class);
        Assert.assertEquals("index", request.getBody());
    }
}