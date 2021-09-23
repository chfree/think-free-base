package com.cditer.free.user.logical.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.cditer.free.user.logical.dao.IUserDao;
import com.cditer.free.user.logical.entity.model.User;
import com.cditer.free.user.logical.entity.viewmodel.UserSearch;
import com.cditer.free.data.dao.base.impl.SuperService;
import com.cditer.free.user.logical.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-11 22:59
 * @comment
 */

@Component
public class UserServiceImpl extends SuperService<User> implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public int queryCountBySearch(UserSearch search) {
        return userDao.queryCountBySearch(search);
    }

    @Override
    public User queryModelBySearch(UserSearch search) {
        return userDao.queryModelBySearch(search);
    }

    @Override
    public User queryModelBySearch(String userId) {
        UserSearch userSearch = new UserSearch();
        userSearch.setId(userId);

        return queryModelBySearch(userSearch);
    }

    @Override
    public User queryModelByLogin(String account, String password) {
        return userDao.queryModelByLogin(account,passwordFormat(password));
    }

    @Override
    public String passwordFormat(String password){
        return SecureUtil.md5(password);
    }
}
