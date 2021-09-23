package com.cditer.free.usersever.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-26 10:16
 * @comment
 */
@Data
@Configuration
public class JwtConfig {
    @Value("${think.jwt.secret-key:chfree001@gmail.com}")
    private String secretKey;

    @Value("${think.jwt.expires-second:7200}")
    private long expiresSecond = 7200;

    @Value("${think.jwt.create-token-bean:default}")
    private String createTokenBean;
}
