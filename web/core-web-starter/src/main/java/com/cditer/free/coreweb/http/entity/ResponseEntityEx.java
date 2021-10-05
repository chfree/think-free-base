package com.cditer.free.coreweb.http.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-08 13:11
 * @comment
 */

public class ResponseEntityEx<T> extends ResponseEntity<T>{
    /**
     * 业务场景定义状态
     * 比如http是200只是请求成功，但业务场景也有一些状态
     */
    private String status;

    /**
     * 业务状态对应的消息
     */
    private String message;


    public ResponseEntityEx(HttpStatus status) {
        super(status);
    }

    public ResponseEntityEx(T body, HttpStatus status) {
        super(body, status);
    }

    public ResponseEntityEx(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseEntityEx(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public ResponseEntityEx(T body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }
}
