package com.cditer.free.user.logical.entity.apimodel;

import com.cditer.free.core.exception.BizException;
import com.cditer.free.core.message.web.ResponseStatus;
import com.cditer.free.core.util.RSAUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-25 15:57
 * @comment
 */
@Slf4j
@Data
public class LoginReq {
    @NotBlank(message="用户名不能为空")
    private String username;

    @NotBlank(message="密码不能为空")
    private String password;

    public void resolveUserName(String rsaPriKey){
        if(StringUtils.hasText(rsaPriKey)){
            throw new BizException(ResponseStatus.SERVER_ERROR,"服务端未配置私钥信息，请与管理员联系");
        }
        try {
            String decUsername = RSAUtils.decrypt(username, rsaPriKey);
            setUsername(decUsername);

            String decPassword = RSAUtils.decrypt(password, rsaPriKey);
            setPassword(decPassword);
        }catch (Exception ex){
            log.error("登陆解析用户名有误",ex);
            throw new BizException(ResponseStatus.SERVER_ERROR,"登陆解析用户名有误");
        }
    }
}
