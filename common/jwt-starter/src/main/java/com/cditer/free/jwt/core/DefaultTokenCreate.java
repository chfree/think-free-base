package com.cditer.free.jwt.core;

import com.cditer.free.jwt.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-12-24 09:09
 * @comment
 */

@Component(value = CreateTokenFactory.DEFAULT_TOKEN_CREATE)
public class DefaultTokenCreate implements ITokenCreate {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public String createToken(String userId, Map<String, Object> claims) {
        long expiresSecond = jwtConfig.getExpiresSecond()*1000L;

        return jwtHelper.createJwt(userId,claims,expiresSecond);
    }
}
