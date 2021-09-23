package com.cditer.free.login.handle.helper;

import com.cditer.free.core.util.SpringContextUtils;
import com.cditer.free.login.handle.ILoginedIntceptor;
import com.cditer.free.security.message.LoginModel;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-11-02 17:15
 * @comment
 */

public class LoginedIntceptorHelper {
    public static void loginedCallback(LoginModel loginModel){
        Map<String, ILoginedIntceptor> loginedIntceptorMap = SpringContextUtils.getCurrentContext().getBeansOfType(ILoginedIntceptor.class);
        if(loginedIntceptorMap==null||loginedIntceptorMap.isEmpty()){
            return;
        }
        List<ILoginedIntceptor> loginedList = loginedIntceptorMap.values().stream().sorted(Comparator.comparing(ILoginedIntceptor::getOrder)).collect(Collectors.toList());
        loginedList.forEach(loginedItem -> {
            loginedItem.logined(loginModel);
        });
    }
}
