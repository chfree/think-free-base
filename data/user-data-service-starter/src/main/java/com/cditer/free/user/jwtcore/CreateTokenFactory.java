package com.cditer.free.user.jwtcore;

import com.cditer.free.core.util.SpringContextUtils;
import com.cditer.free.user.configuration.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-12-24 09:07
 * @comment
 */

@Component
public class CreateTokenFactory {

    public final static String ID="id";

    public final static String ACCOUNT="account";

    public final static String NAME="name";

    public final static String DEFAULT_TOKEN_CREATE="defaultTokenCreate";

    @Autowired
    JwtConfig jwtConfig;

    public ITokenCreate newTokenCreate(){
        String tokenCreateBean = DEFAULT_TOKEN_CREATE;

        if(!"default".equals(jwtConfig.getCreateTokenBean())&&!StringUtils.hasLength(jwtConfig.getCreateTokenBean())){
            tokenCreateBean = jwtConfig.getCreateTokenBean();
        }
        return (ITokenCreate) SpringContextUtils.getCurrentContext().getBean(tokenCreateBean);
    }
}
