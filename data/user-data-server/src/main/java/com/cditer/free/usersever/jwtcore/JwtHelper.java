package com.cditer.free.usersever.jwtcore;

import com.cditer.free.usersever.configuration.JwtConfig;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-23 12:09
 * @comment
 */

@Slf4j
@Component
public class JwtHelper {
    @Autowired
    private JwtConfig jwtConfig;


    public String createJwt(Claims claims){
        return createJwt(claims,jwtConfig.getExpiresSecond()*1000L);
    }

    public String createJwt(Claims claims,long ttlMillis){
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims);

        return createJwt(builder,ttlMillis);
    }

    public String createJwt(String id, Map<String,Object> claims){
        return createJwt(id,claims,jwtConfig.getExpiresSecond()*1000L);
    }

    public String createJwt(String id, Map<String,Object> claims,long ttlMillis){
        // 此处有一个设置上的隐藏bug
        // 如果先setId在setClaims,在获取id的时候是null
        // 所以先设置了claims后在设置id，就可以正常获取了
        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(id);

        return createJwt(builder,ttlMillis);
    }

    private String createJwt(JwtBuilder builder,long ttlMillis){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        Key signingKey = generalKey();

        builder.setIssuedAt(now)
                .signWith(signatureAlgorithm, signingKey);

        //添加Token过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        //生成JWT
        return builder.compact();
    }


    public Claims parseJWT(String jwt){
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        try {
            Claims claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)         //设置签名的秘钥
                    .setAllowedClockSkewSeconds(120) // 设置机器时间容差
                    .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
            return claims;
        }catch(ExpiredJwtException ex) {
            return ex.getClaims();
        }catch (Exception ex){
            log.error("解析token异常", ex);
            return null;
        }
    }

    private SecretKey generalKey(){
        String stringKey = jwtConfig.getSecretKey();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] encodedKey = DatatypeConverter.parseBase64Binary(stringKey);//本地的密码解码[B@152f6e2
        // 根据给定的字节数组使用HS256加密算法构造一个密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, signatureAlgorithm.getJcaName());
    }

    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public boolean isTokenExpired(String token) {
        Claims claims = parseJWT(token);
        if(claims==null){
            return true;
        }
        return claims.getExpiration().before(new Date());
    }

}
