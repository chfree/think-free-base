package com.cditer.free.core.message.security;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
