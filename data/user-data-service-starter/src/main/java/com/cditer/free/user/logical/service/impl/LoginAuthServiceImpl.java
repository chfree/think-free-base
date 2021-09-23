package com.cditer.free.user.logical.service.impl;

import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.impl.SuperService;
import com.cditer.free.user.configuration.LoginConfig;
import com.cditer.free.user.logical.dao.ILoginAuthDao;
import com.cditer.free.user.logical.entity.model.LoginAuth;
import com.cditer.free.user.logical.entity.viewmodel.LoginAuthSearch;
import com.cditer.free.user.logical.entity.viewmodel.LoginAuthView;
import com.cditer.free.user.logical.enums.LoginAuthStatus;
import com.cditer.free.user.logical.service.ILoginAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-15 14:33:37
 * @comment     登陆授权表
 */

@Component
public class LoginAuthServiceImpl extends SuperService<LoginAuth> implements ILoginAuthService {
    @Autowired
    ILoginAuthDao loginAuthDao;

    @Autowired
    LoginConfig loginConfig;

    @Override
    public int queryCountBySearch(LoginAuthSearch search) {
        return loginAuthDao.queryCountBySearch(search);
    }

    @Override
    public List<LoginAuthView> queryListBySearch(LoginAuthSearch search, PagerModel pagerModel) {
        return loginAuthDao.queryListBySearch(search,pagerModel);
    }

    @Override
    public boolean saveLoginAuth(LoginAuth loginAuth) {
        if(loginConfig.isOpenSSO()){
            // 先将当前用户的其他的token置为无效
            if(!loginAuthDao.updateStatusByUserId(loginAuth.getUserId(), LoginAuthStatus.INVALID.getValue())){
                return false;
            }
        }
        return addModel(loginAuth);
    }

    @Override
    public boolean checkTokenIsValid(String token) {
        return loginAuthDao.checkTokenIsValid(token);
    }

    @Override
    public boolean updateStatusByToken(String token, String status) {
        return loginAuthDao.updateStatusByToken(token,status);
    }

}