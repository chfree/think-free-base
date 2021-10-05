package com.cditer.free.coreweb.http.inceptor;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cditer.free.core.message.web.BaseResponse;
import com.cditer.free.coreweb.BaseTest;
import com.cditer.free.coreweb.TestContant;
import com.cditer.free.coreweb.configuration.ThinkWebConfig;
import com.cditer.free.coreweb.exception.ResponseException;
import com.cditer.free.coreweb.http.entity.RequestEntityEx;
import com.cditer.free.coreweb.http.entity.RequestEntityExBuilder;
import com.cditer.free.coreweb.http.entity.ResponseEntityEx;
import com.cditer.free.coreweb.http.request.IServerRequestHelper;
import com.cditer.free.coreweb.test.model.UserResp;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class ResponseEntityDefaultResolveInceptorTest extends BaseTest {

    @Autowired
    ResponseEntityDefaultResolveInceptor responseEntityDefaultResolveInceptor;

    @Autowired
    ThinkWebConfig thinkWebConfig;

    @Autowired
    IServerRequestHelper serverRequestHelper;

    private final String userHost = TestContant.hostPrefix + "/user/";

    @Test
    public void getOrder() {
        int order = responseEntityDefaultResolveInceptor.getOrder();
        Assert.assertTrue(NumberUtil.isInteger(String.valueOf(order)));
    }

    @Test
    public void testUserIndexResolve(){
        final String userIndex = userHost + "index";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .addParam("name", "cheng")
                .build();

        thinkWebConfig.setOpenDefaultResolveEntity(false);
        ResponseEntityEx<String> indexResp = serverRequestHelper.request(paramIndexRequest, String.class);

        thinkWebConfig.setOpenDefaultResolveEntity(true);
        responseEntityDefaultResolveInceptor.resovle(indexResp);

        JSONObject jsonObject = JSONUtil.parseObj(indexResp.getBody());
        String name = jsonObject.getStr("name");

        System.out.println(indexResp.getBody());

        Assert.assertEquals(name, "cheng");
    }

    @Test
    public void testUserRespIndexResolve() {
        final String userIndex = userHost + "indexResp";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .addParam("name", "cheng")
                .build();

        thinkWebConfig.setOpenDefaultResolveEntity(false);
        ResponseEntityEx<UserResp> userResp = serverRequestHelper.request(paramIndexRequest, UserResp.class);

        thinkWebConfig.setOpenDefaultResolveEntity(true);
        responseEntityDefaultResolveInceptor.resovle(userResp);

        Assert.assertEquals(userResp.getBody().getName(), "cheng");
    }

    @Test
    public void testErrorIndexResolve(){
        final String userIndex = userHost + "errorIndex";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .build();

        thinkWebConfig.setOpenDefaultResolveEntity(false);
        ResponseEntityEx<String> indexResp = serverRequestHelper.request(paramIndexRequest, String.class);

        thinkWebConfig.setOpenDefaultResolveEntity(true);

        Assert.assertThrows("errorIndex", ResponseException.class, () -> responseEntityDefaultResolveInceptor.resovle(indexResp));
    }

    @Test
    public void testErrorIndexRespResolve(){
        final String userIndex = userHost + "errorIndexResp";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .build();

        thinkWebConfig.setOpenDefaultResolveEntity(false);
        ResponseEntityEx<BaseResponse> indexResp = serverRequestHelper.request(paramIndexRequest, BaseResponse.class);

        thinkWebConfig.setOpenDefaultResolveEntity(true);

        Assert.assertThrows("errorIndex", ResponseException.class, () -> responseEntityDefaultResolveInceptor.resovle(indexResp));
    }

    @Test
    public void testHttpErrorIndexResolve(){

        ResponseEntityEx indexResp = new ResponseEntityEx(null, null, HttpStatus.BAD_REQUEST);

        responseEntityDefaultResolveInceptor.resovle(indexResp);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, indexResp.getStatusCode());
    }

    @Test
    public void testNoJsonResolve(){

        ResponseEntityEx indexResp = new ResponseEntityEx("cheng", null, HttpStatus.OK);

        responseEntityDefaultResolveInceptor.resovle(indexResp);

        Assert.assertEquals("cheng", indexResp.getBody());
    }
}