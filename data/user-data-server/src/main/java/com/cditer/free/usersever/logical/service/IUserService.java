package com.cditer.free.usersever.logical.service;

import com.cditer.free.data.dao.base.ISuperService;
import com.cditer.free.usersever.logical.entity.model.User;
import com.cditer.free.usersever.logical.entity.viewmodel.UserSearch;

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
}
