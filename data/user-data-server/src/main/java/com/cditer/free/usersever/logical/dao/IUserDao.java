package com.cditer.free.usersever.logical.dao;

import com.cditer.free.usersever.logical.entity.model.User;
import com.cditer.free.usersever.logical.entity.viewmodel.UserSearch;
import com.cditer.free.data.dao.base.ISuperDao;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-11 22:59
 * @comment
 */

public interface IUserDao extends ISuperDao<User> {
    int queryCountBySearch(UserSearch search);

    User queryModelBySearch(UserSearch search);

    User queryModelByLogin(String account, String password);
}
