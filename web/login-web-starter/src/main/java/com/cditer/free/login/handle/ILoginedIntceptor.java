package com.cditer.free.login.handle;

import com.cditer.free.security.message.LoginModel;

/**
 * 登陆成功后的回掉
 * 用在验证完用户名和密码后的回调
 */
public interface ILoginedIntceptor {
    int getOrder();

    void logined(LoginModel loginModel);
}
