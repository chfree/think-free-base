package com.cditer.free.core.message.web;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-09 23:33
 * @comment
 */

@Data
public class BaseRequest<T> {
    private T data;
}
