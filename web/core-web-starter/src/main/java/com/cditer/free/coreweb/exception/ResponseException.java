package com.cditer.free.coreweb.exception;

import com.cditer.free.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-15 12:34
 * @comment
 */

@Slf4j
public class ResponseException extends BizException {
    public ResponseException(int status, String message){
        super(status, message);
        log.error("解析返回时，返回业务信息中的状态码不为200");
    }
}
