package com.cditer.free.user.logical.service;

import com.cditer.free.data.dao.base.ISuperService;
import com.cditer.free.user.logical.entity.model.User;
import com.cditer.free.user.logical.entity.viewmodel.UserSearch;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-11 22:59
 * @comment
 */

public interface IUserService extends ISuperService<User> {
    int queryCountBySearch(UserSearch search);

    User queryModelBySearch(UserSearch search);

    User queryModelBySearch(String userId);

    User queryModelByLogin(String account, String password);

    String passwordFormat(String password);
}
