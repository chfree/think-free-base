package com.cditer.free.jwt.core;

import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-12-24 09:02
 * @comment
 */

public interface ITokenCreate {
    /**
     * 创建token
     */
    String createToken(String userId, Map<String,Object> claims);


}
