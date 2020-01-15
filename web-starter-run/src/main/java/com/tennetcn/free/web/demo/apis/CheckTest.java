package com.tennetcn.free.web.demo.apis;

import com.tennetcn.free.core.validator.back.MethodCheckResult;
import com.tennetcn.free.web.demo.viewmodel.User;
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
