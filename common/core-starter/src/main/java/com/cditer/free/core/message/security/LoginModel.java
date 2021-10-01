package com.cditer.free.core.message.security;

import lombok.Data;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-25 00:09
 * @comment
 */

@Data
public class LoginModel {
    private String id;

    private String name;

    private String account;

    private String token;
}
