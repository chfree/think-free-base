package com.cditer.free.login.apis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.cditer.free.core.message.web.BaseResponse;
import com.cditer.free.core.util.SpringContextUtils;
import com.cditer.free.login.handle.ILoginAllowIntceptor;
import com.cditer.free.login.handle.helper.LoginedIntceptorHelper;
import com.cditer.free.security.message.LoginModel;
import com.cditer.free.user.configuration.LoginConfig;
import com.cditer.free.user.jwtcore.CreateTokenFactory;
import com.cditer.free.user.jwtcore.JwtHelper;
import com.cditer.free.user.logical.entity.apimodel.LoginReq;
import com.cditer.free.user.logical.entity.model.LoginAuth;
import com.cditer.free.user.logical.entity.model.User;
import com.cditer.free.user.logical.enums.LoginAuthStatus;
import com.cditer.free.user.logical.enums.LoginAuthType;
import com.cditer.free.user.logical.enums.LoginStatus;
import com.cditer.free.user.logical.service.ILoginAuthService;
import com.cditer.free.user.logical.service.IUserService;
import com.cditer.free.user.utils.LoginUtil;
import com.cditer.free.web.message.WebResponseStatus;
import com.cditer.free.web.security.AuthorityApi;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-12 00:05
 * @comment
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/authority/login/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginApi extends AuthorityApi {

    @Autowired
    private IUserService userService;

    @Autowired
    private ILoginAuthService loginAuthService;

    @Autowired
    CreateTokenFactory createTokenFactory;

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    LoginConfig loginConfig;

    @PostMapping("login")
    @Transactional
    public BaseResponse login(@Valid LoginReq loginReq){
        BaseResponse response = new BaseResponse();

        if(loginConfig.isEncrypt()){
            loginReq.resolveUserName(loginConfig.getLoginRsaPriKey());
        }

        User user = userService.queryModelByLogin(loginReq.getUsername(),loginReq.getPassword());
        if(user==null){
            response.setMessage("用户名或密码不正确");
            response.setStatus(WebResponseStatus.DATA_ERROR);
            return response;
        }

        // 用户名密码正确了，在检查状态是否正常,不是正常则不允许登陆
        if(!LoginStatus.NORMAL.getValue().equals(user.getStatus())){
            response.setMessage("您的用户状态处于【"+LoginStatus.convert2Text(user.getStatus())+"】，暂时无法登陆，请联系管理员");
            response.setStatus(WebResponseStatus.DATA_ERROR);
            return response;
        }

        loginSuccess(response, user);

        return response;
    }

    private void loginSuccess(BaseResponse response, User user) {
        LoginModel loginModel = LoginUtil.user2LoginModel(user);

        String token = createTokenFactory.newTokenCreate().createToken(loginModel.getId(),loginModel.getAccount(),loginModel.getName());
        loginModel.setToken(token);

        // 执行Logined切入点
        if(!isAllowLogin(loginModel, user)){
            response.setMessage("该用户暂时无法登陆，请联系管理员");
            response.setStatus(WebResponseStatus.DATA_ERROR);
            return;
        }

        LoginedIntceptorHelper.loginedCallback(loginModel);

        addLoginAuth(loginModel,token);


        cached.put(token,loginModel);
        response.put("result",true);
        response.put("token",token);
    }

    /**
     * 处理登陆授权信息
     * @param loginModel loginModel实体
     * @param token token信息
     */
    private void addLoginAuth(LoginModel loginModel,String token){
        LoginAuth loginAuth = new LoginAuth();

        Claims claims = jwtHelper.parseJWT(token);

        loginAuth.setId(IdUtil.randomUUID());
        loginAuth.setExpTm(claims.getExpiration());
        loginAuth.setToken(token);
        loginAuth.setUserId(loginModel.getId());
        loginAuth.setType(LoginAuthType.LOGIN.getValue());
        loginAuth.setStatus(LoginAuthStatus.VALID.getValue());
        loginAuth.setAuthTm(DateUtil.date());

        loginAuthService.saveLoginAuth(loginAuth);
    }

    private boolean isAllowLogin(LoginModel loginModel,User loginUser){
        ILoginAllowIntceptor loginAllowIntceptor = null;
        try{
            loginAllowIntceptor = SpringContextUtils.getCurrentContext().getBean(ILoginAllowIntceptor.class);
        }catch (NoSuchBeanDefinitionException ex){
            log.info("No qualifying bean of type '{}' available", ILoginAllowIntceptor.class.getName());
            return true;
        }
        if(loginAllowIntceptor == null){
            return true;
        }
        return loginAllowIntceptor.isAllowLogin(loginModel, loginUser);
    }

    @PostMapping("loginfo")
    public BaseResponse loginfo(){
        BaseResponse resp = new BaseResponse();

        LoginModel loginModel = getCurrentLogin();

        resp.put("loginfo", loginModel);
        return resp;
    }

    @PostMapping("logout")
    public BaseResponse logout(){
        BaseResponse resp = new BaseResponse();

        String token = getCurrentLogin().getToken();
        cached.remove(token);

        loginAuthService.updateStatusByToken(token,LoginAuthStatus.INVALID.getValue());

        resp.put("result", true);
        return resp;
    }

    @PostMapping("getPubKey")
    public BaseResponse getPubKey(){
        BaseResponse resp = new BaseResponse();
        String pubKey = loginConfig.getLoginRsaPubKey();

        resp.put("pubKey", pubKey);
        return resp;
    }
}
