package com.cditer.free.coreweb.http.request;

import com.cditer.free.coreweb.BaseTest;
import com.cditer.free.coreweb.TestContant;
import com.cditer.free.coreweb.http.entity.RequestEntityEx;
import com.cditer.free.coreweb.http.entity.RequestEntityExBuilder;
import com.cditer.free.coreweb.http.entity.ResponseEntityEx;
import com.cditer.free.coreweb.test.model.IndexListResp;
import com.cditer.free.coreweb.test.model.IndexUserListResp;
import com.cditer.free.coreweb.test.model.User;
import com.cditer.free.coreweb.test.model.UserModelResp;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.List;

public class IServerRequestHelperMapTest extends BaseTest {
    @Autowired
    IServerRequestHelper serverRequestHelper;

    private final String mapRespHost = TestContant.hostPrefix + "/mapResp/";

    @Test
    public void testMapRespIndex() {
        final String userIndex = mapRespHost + "index";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .addParam("name", "cheng")
                .addParam("deptName", "develop")
                .build();

        ResponseEntityEx<UserModelResp> userResp = serverRequestHelper.request(paramIndexRequest, UserModelResp.class);

        User user = userResp.getBody().getUser();

        Assert.assertEquals(user.getName(), "cheng");

        Assert.assertEquals(user.getDept().getName(), "develop");
    }

    @Test
    public void testMapRespIndexList() {
        final String userIndex = mapRespHost + "indexList";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .addParam("name", "cheng")
                .addParam("deptName", "develop")
                .build();

        ResponseEntityEx<IndexListResp> userResp = serverRequestHelper.request(paramIndexRequest, IndexListResp.class);

        List<String> names = userResp.getBody().getNames();

        Assert.assertEquals(names.get(0), "cheng");

        Assert.assertEquals(names.get(1), "develop");
    }

    @Test
    public void testMapRespIndexUserList() {
        final String userIndex = mapRespHost + "indexUserList";
        RequestEntityEx paramIndexRequest = RequestEntityExBuilder.builder()
                .setMethod(HttpMethod.POST)
                .setUrl(userIndex)
                .addParam("name", "cheng")
                .addParam("deptName", "develop")
                .build();

        ResponseEntityEx<IndexUserListResp> userResp = serverRequestHelper.request(paramIndexRequest, IndexUserListResp.class);

        List<User> users = userResp.getBody().getUsers();

        Assert.assertEquals(users.get(0).getName(), "cheng");

        Assert.assertEquals(users.get(0).getDept().getName(), "develop");
    }
}