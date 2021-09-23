package com.cditer.free.user.jwtcore;

import com.cditer.free.user.configuration.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
    JwtHelper jwtHelper;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public String createToken(String userId,String account,String name) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("account",account);
        claims.put("name",name);

        long expiresSecond = jwtConfig.getExpiresSecond()*1000L;

        return jwtHelper.createJwt(userId,claims,expiresSecond);
    }
}
