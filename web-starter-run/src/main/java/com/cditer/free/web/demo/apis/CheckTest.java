package com.cditer.free.web.demo.apis;

import com.cditer.free.web.demo.viewmodel.User;
import com.cditer.free.core.validator.back.MethodCheckResult;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-01-15 22:53
 * @comment
 */
@Component
public class CheckTest {
    public MethodCheckResult checkTest(User user){
        MethodCheckResult checkResult = new MethodCheckResult();
        checkResult.setValid(false);
        checkResult.setMessage("密码不是6个0");
        if("000000".equals(user.getPassword())){
            checkResult.setValid(true);
        }
        return checkResult;
    }
}
