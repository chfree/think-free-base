package com.cditer.free.coreweb.http.request;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cditer.free.core.message.web.BaseRequest;
import com.cditer.free.coreweb.BaseTest;
import com.cditer.free.coreweb.TestContant;
import com.cditer.free.coreweb.http.entity.RequestEntityEx;
import com.cditer.free.coreweb.http.entity.RequestEntityExBuilder;
import com.cditer.free.coreweb.http.entity.ResponseEntityEx;
import com.cditer.free.coreweb.test.model.UserReq;
import com.cditer.free.coreweb.test.model.UserResp;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

public class IServerRequestHelperTest extends BaseTest {


    @Autowired
    IServerRequestHelper serverRequestHelper;

    private final String homeHost = TestContant.hostPrefix + "/home/";
    private final String userHost = TestContant.hostPrefix + "/user/";

    @Test
    public void testRequestIndex() {
        final String indexUrl = homeHost + "index";
        RequestEntityEx indexRquest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.GET)
                .setUrl(indexUrl).build();

        ResponseEntityEx<String> request = serverRequestHelper.request(indexRquest, String.class);
        Assert.assertEquals("index", request.getBody());
    }

    @Test
    public void testRequestParamIndex() {
        final String paramIndex = homeHost + "paramIndex";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(paramIndex)
                .addParam("name", "cheng")
                .build();

        ResponseEntityEx<String> paramIndexResp = serverRequestHelper.request(paramIndexRequest, String.class);
        Assert.assertEquals("chengindex",paramIndexResp.getBody());
    }

    @Test
    public void testRequestUserIndex(){
        final String userIndex = homeHost + "userIndex";

        UserReq userReq = new UserReq();
        userReq.setAge(18);
        userReq.setName("cheng");

        RequestEntityEx userIndexRequest = RequestEntityExBuilder.builder(BaseRequest.class)
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .setBody(userReq)
                .addParam("token","123")
                .formData()
                .build();

        ResponseEntityEx<String> userIndexResp = serverRequestHelper.request(userIndexRequest, String.class);
        Assert.assertEquals(userReq.getName()+":"+userReq.getAge()+":123",userIndexResp.getBody());
    }

    @Test
    public void testRequestUserReqBodyIndex(){
        final String userIndex = homeHost + "userReqBodyIndex";

        UserReq userReq = new UserReq();
        userReq.setAge(18);
        userReq.setName("cheng");

        RequestEntityEx userIndexRequest = RequestEntityExBuilder.builder(BaseRequest.class)
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .setBody(userReq)
                .build();

        ResponseEntityEx<String> userIndexResp = serverRequestHelper.request(userIndexRequest, String.class);
        Assert.assertEquals(userReq.getName()+":"+userReq.getAge(),userIndexResp.getBody());
    }


    @Test
    public void testUserIndex(){
        final String userIndex = userHost + "index";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .addParam("name", "cheng")
                .build();

        ResponseEntityEx<String> indexResp = serverRequestHelper.request(paramIndexRequest, String.class);

        JSONObject jsonObject = JSONUtil.parseObj(indexResp.getBody());
        String name = jsonObject.getStr("name");

        System.out.println(indexResp.getBody());

        Assert.assertEquals(name, "cheng");
    }

    @Test
    public void testUserRespIndex() {
        final String userIndex = userHost + "indexResp";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .addParam("name", "cheng")
                .build();

        ResponseEntityEx<UserResp> userResp = serverRequestHelper.request(paramIndexRequest, UserResp.class);

        Assert.assertEquals(userResp.getBody().getName(), "cheng");
    }

    @Test
    public void testUserRespComplexIndex() {
        final String userIndex = userHost + "indexComplexResp";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .addParam("name", "cheng")
                .addParam("deptName", "develop")
                .build();

        ResponseEntityEx<UserResp> userResp = serverRequestHelper.request(paramIndexRequest, UserResp.class);

        Assert.assertEquals(userResp.getBody().getName(), "cheng");

        Assert.assertEquals(userResp.getBody().getDept().getName(), "develop");
    }
}