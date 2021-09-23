package com.cditer.free.user.logical.dao.impl;

import com.cditer.free.user.logical.dao.IUserDao;
import com.cditer.free.user.logical.entity.model.User;
import com.cditer.free.user.logical.entity.viewmodel.UserSearch;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.dao.base.impl.SuperDao;
import com.cditer.free.data.utils.SqlExpressionFactory;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-11 22:59
 * @comment
 */

@Component
public class UserDaoImpl extends SuperDao<User> implements IUserDao {

    @Override
    public int queryCountBySearch(UserSearch search) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectCount().from(User.class);

        appendExpression(sqlExpression,search);

        return queryCount(sqlExpression);
    }

    @Override
    public User queryModelBySearch(UserSearch search) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(User.class);

        appendExpression(sqlExpression,search);

        return queryModel(sqlExpression);
    }

    @Override
    public User queryModelByLogin(String account, String password) {
        ISqlExpression sqlExpression=SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(User.class)
                .andEq("account",account)
                .andEq("password",password);

        return queryModel(sqlExpression);
    }

    private void appendExpression(ISqlExpression sqlExpression, UserSearch search){
        sqlExpression.andEqNoEmpty("id",search.getId());

        sqlExpression.andEqNoEmpty("name",search.getName());

        sqlExpression.andEqNoEmpty("account",search.getAccount());

        sqlExpression.andNotEqNoEmpty("id",search.getNotId());

        sqlExpression.andRightLikeNoEmpty("name",search.getLikeName());

        sqlExpression.andRightLikeNoEmpty("account",search.getLikeAccount());

        sqlExpression.andEqNoEmpty("status",search.getStatus());
    }
}
