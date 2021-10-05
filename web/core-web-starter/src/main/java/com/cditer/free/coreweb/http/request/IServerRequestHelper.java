package com.cditer.free.coreweb.http.request;

import com.cditer.free.coreweb.http.entity.RequestEntityEx;
import com.cditer.free.coreweb.http.entity.ResponseEntityEx;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-08 13:04
 * @comment
 */

public interface IServerRequestHelper {
    RestTemplate getRestTemplate();

    <T> ResponseEntityEx<T> request(RequestEntityEx requestEntity, Class<T> responseType);

    <T> ResponseEntityEx<T> postForEntity(RequestEntityEx requestEntity, Class<T> responseType);

    <T> ResponseEntityEx<T> getForEntity(RequestEntityEx requestEntity, Class<T> responseType);

    <T> ResponseEntityEx<T> resolveResponseEntity(ResponseEntity<T> responseEntity);
}
