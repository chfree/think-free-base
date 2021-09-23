package com.cditer.free.user.utils;

import com.cditer.free.security.message.LoginModel;
import com.cditer.free.user.logical.entity.model.User;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2021-09-23 19:27
 * @comment
 */

public class LoginUtil {

    public static LoginModel user2LoginModel(User user){
        LoginModel loginModel = new LoginModel();
        loginModel.setId(user.getId());
        loginModel.setAccount(user.getAccount());
        loginModel.setName(user.getName());

        return loginModel;
    }
}
