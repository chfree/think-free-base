package com.cditer.free.coreweb.http.entity;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-09 13:03
 * @comment
 */

public class RequestEntityEx<T> extends RequestEntity<T> {

    public RequestEntityEx(HttpMethod method, URI url) {
        super(method, url);
    }

    public RequestEntityEx(HttpMethod method, String url) {
        this(method, URI.create(url));
    }

    public RequestEntityEx(T body, HttpMethod method, URI url) {
        super(body, method, url);
    }

    public RequestEntityEx(T body, HttpMethod method, URI url, Type type) {
        super(body, method, url, type);
    }

    public RequestEntityEx(MultiValueMap<String, String> headers, HttpMethod method, URI url) {
        super(headers, method, url);
    }

    public RequestEntityEx(T body, MultiValueMap<String, String> headers, HttpMethod method, URI url) {
        super(body, headers, method, url);
    }

    public RequestEntityEx(T body, MultiValueMap<String, String> headers, HttpMethod method, URI url, Type type) {
        super(body, headers, method, url, type);
    }

    public Map<String,Object> getParam(){
        if(this.getBody() instanceof MultiValueMap){
            return ((MultiValueMap<String, Object>)this.getBody()).toSingleValueMap();
        }
        return null;
    }

    public String getUrlStr(){
        return this.getUrl().toString();
    }
}
