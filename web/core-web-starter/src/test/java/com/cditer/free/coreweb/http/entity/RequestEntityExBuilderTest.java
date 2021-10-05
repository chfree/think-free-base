package com.cditer.free.coreweb.http.entity;

import com.cditer.free.coreweb.test.model.UserReq;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RequestEntityExBuilderTest{

    @Test
    public void builder() {
        RequestEntityExBuilder builder = RequestEntityExBuilder.builder();

        Assert.assertNotNull(builder);
    }

    @Test
    public void testBuilder() {
        RequestEntityExBuilder<UserReq> builder = RequestEntityExBuilder.builder(UserReq.class);
        builder.setBody(new UserReq());

        RequestEntityEx<UserReq> build = builder.build();
        Assert.assertTrue(build.getBody() instanceof UserReq);
    }

    @Test
    public void setMethod() {
        RequestEntityEx build = RequestEntityExBuilder.builder().setMethod(HttpMethod.POST).build();

        Assert.assertEquals(build.getMethod(), HttpMethod.POST);
    }

    @Test
    public void setUrl() {
        String url = "http://localhost/index";
        RequestEntityEx build = RequestEntityExBuilder.builder().setUrl(url).build();

        Assert.assertEquals(build.getUrl().toString(), url);
    }

    @Test
    public void setUri() {
        String url = "http://localhost/index";
        RequestEntityEx build = RequestEntityExBuilder.builder().setUri(URI.create(url)).build();

        Assert.assertEquals(build.getUrl().toString(), url);
    }

    @Test
    public void setBody() {
        RequestEntityExBuilder<UserReq> builder = RequestEntityExBuilder.builder(UserReq.class);
        builder.setBody(new UserReq());

        RequestEntityEx<UserReq> build = builder.build();
        Assert.assertTrue(build.getBody() instanceof UserReq);
    }

    @Test
    public void formData() {
        RequestEntityExBuilder<UserReq> builder = RequestEntityExBuilder.builder(UserReq.class);

        UserReq userReq = new UserReq();
        userReq.setName("cheng");

        builder.setBody(userReq).formData();

        RequestEntityEx<UserReq> build = builder.build();
        MultiValueMap<String, Object> body = (MultiValueMap<String, Object>) build.getBody();

        Assert.assertEquals(body.get("name").get(0), userReq.getName());
    }

    @Test
    public void setHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", "tokenval");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        RequestEntityEx build = RequestEntityExBuilder.builder().setHeaders(httpHeaders).build();

        Assert.assertEquals(build.getHeaders().get("token"), httpHeaders.get("token"));

        Assert.assertEquals(build.getHeaders().getContentType(), httpHeaders.getContentType());
    }

    @Test
    public void addHeader() {
        RequestEntityEx build = RequestEntityExBuilder.builder().addHeader("token", "tokenval").build();

        Assert.assertEquals(build.getHeaders().get("token").get(0), "tokenval");
    }

    @Test
    public void addParam() {
        RequestEntityEx build = RequestEntityExBuilder.builder().addParam("token", "tokenval").build();

        MultiValueMap<String, Object> body = (MultiValueMap<String, Object>) build.getBody();

        Assert.assertEquals(body.get("token").get(0), "tokenval");
    }

    @Test
    public void addParamMap() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("token", "tokenval");

        RequestEntityEx build = RequestEntityExBuilder.builder().addParamMap(paramMap).build();
        MultiValueMap<String, Object> body = (MultiValueMap<String, Object>) build.getBody();

        Assert.assertEquals(body.get("token").get(0), "tokenval");
    }

    @Test
    public void build() {
        RequestEntityEx build = RequestEntityExBuilder.builder().build();

        Assert.assertNotNull(build);
    }
}