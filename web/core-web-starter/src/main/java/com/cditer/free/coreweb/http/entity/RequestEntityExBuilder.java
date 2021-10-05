package com.cditer.free.coreweb.http.entity;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-11 12:20
 * @comment
 */

public class RequestEntityExBuilder<T> {

    private HttpMethod httpMethod;

    private URI uri;

    private T body;

    private Map<String, Object> arguments = null;

    private HttpHeaders httpHeaders;

    private boolean formData = false;

    private Map<String,Object> getArgumentMap(){
        if(arguments == null){
            arguments = new HashMap<>();
        }
        return this.arguments;
    }

    public static RequestEntityExBuilder builder(){
        return new RequestEntityExBuilder();
    }

    public static  <X> RequestEntityExBuilder<X> builder(Class<X> tClass){
        return new RequestEntityExBuilder<X>();
    }


    public RequestEntityExBuilder<T> setMethod(HttpMethod httpMethod){
        this.httpMethod = httpMethod;

        return this;
    }

    public RequestEntityExBuilder<T> setUrl(String url){
        return setUri(URI.create(url));
    }

    public RequestEntityExBuilder<T> setUri(URI uri){
        this.uri = uri;

        return this;
    }

    public RequestEntityExBuilder<T> setBody(T body){
        this.body = body;

        return this;
    }

    public  RequestEntityExBuilder<T> formData(){
        this.formData = true;
        return  this;
    }

    public RequestEntityExBuilder<T> setHeaders(HttpHeaders headers){
        this.httpHeaders = headers;

        return this;
    }

    public RequestEntityExBuilder<T> addHeader(String headerKey,String headerVal){
        if(this.httpHeaders == null){
            this.httpHeaders = new HttpHeaders();
        }
        httpHeaders.add(headerKey, headerVal);

        return this;
    }

    public RequestEntityExBuilder<T> addParam(String key,Object value){
        getArgumentMap().put(key, value);

        return this;
    }

//    public RequestEntityExBuilder<T> addParamMap(Map<String,String> paramMap){
//        getArgumentMap().putAll(paramMap);
//
//        return this;
//    }

    public RequestEntityExBuilder<T> addParamMap(Map<String,Object> paramMap){
        getArgumentMap().putAll(paramMap);

        return this;
    }



    public RequestEntityEx<T> build(){
        // 传了对象，但是又指定为formdata, 则将body取属性转换为key,value
        if(formData){
            Map<String,Object> beanMap= BeanUtil.beanToMap(body);
            getArgumentMap().putAll(beanMap);
        }

        if(arguments!=null){
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

            for (String key : arguments.keySet()) {
                params.add(key, arguments.get(key));
            }
            return (RequestEntityEx<T>)new RequestEntityEx<MultiValueMap<String, Object>>(params, this.httpHeaders, this.httpMethod, this.uri);
        }

        if(body!=null){
            return new RequestEntityEx<T>(body,this.httpHeaders, this.httpMethod, this.uri);
        }
        return new RequestEntityEx<T>(this.httpHeaders, this.httpMethod, this.uri);
    }
}
