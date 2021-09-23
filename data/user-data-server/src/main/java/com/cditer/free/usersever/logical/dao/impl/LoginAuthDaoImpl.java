package com.cditer.free.usersever.logical.dao.impl;

import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.dao.base.impl.SuperDao;
import com.cditer.free.data.utils.SqlExpressionFactory;
import com.cditer.free.usersever.logical.dao.ILoginAuthDao;
import com.cditer.free.usersever.logical.entity.model.LoginAuth;
import com.cditer.free.usersever.logical.entity.model.User;
import com.cditer.free.usersever.logical.entity.viewmodel.LoginAuthSearch;
import com.cditer.free.usersever.logical.entity.viewmodel.LoginAuthView;
import com.cditer.free.usersever.logical.enums.LoginAuthStatus;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-15 14:32:34
 * @comment     登陆授权表
 */

@Component
public class LoginAuthDaoImpl extends SuperDao<LoginAuth> implements ILoginAuthDao {

    private String mainTableAlias = "loginAuth";

    @Override
    public int queryCountBySearch(LoginAuthSearch search) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();

        sqlExpression.selectCount().from(LoginAuth.class,mainTableAlias)
                .leftJoin(User.class,"user").on(mainTableAlias+".user_id","user.id");


        appendExpression(sqlExpression,search);
        appendUserWhere(sqlExpression,search);

        return queryCount(sqlExpression);
    }

    @Override
    public List<LoginAuthView> queryListBySearch(LoginAuthSearch search, PagerModel pagerModel) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();

        sqlExpression.selectAllFrom(LoginAuth.class, mainTableAlias)
                .appendSelect("user.account as account", "user.name as name")
                .leftJoin(User.class, "user").on(mainTableAlias + ".user_id", "user.id")
                .addOrder("auth_tm", OrderEnum.desc);


        appendExpression(sqlExpression,search);
        appendUserWhere(sqlExpression,search);

        return queryList(sqlExpression,pagerModel,LoginAuthView.class);
    }

    @Override
    public boolean updateStatusByUserId(String userId,String status) {
        String notStatus = LoginAuthStatus.VALID.getValue().equals(status)? LoginAuthStatus.INVALID.getValue():LoginAuthStatus.VALID.getValue();

        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.update(LoginAuth.class)
                     .set("status","setStatus")
                     .setParam("setStatus",status)
                     .andEq("user_id",userId)
                     .andEq("status",notStatus);
        
        return update(sqlExpression)>=0;
    }

    @Override
    public boolean checkTokenIsValid(String token) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();

        sqlExpression.selectCount()
                .from(LoginAuth.class)
                .andEq("token", token)
                .andEq("status", LoginAuthStatus.VALID.getValue())
                .andWhere("exp_tm>now()");

        return queryCount(sqlExpression) > 0;
    }

    @Override
    public boolean updateStatusByToken(String token, String status) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.update(LoginAuth.class)
                .setColumn("status",status)
                .andEq("token",token);

        return update(sqlExpression)>=0;
    }

    private void appendUserWhere(ISqlExpression sqlExpression,LoginAuthSearch search){
        sqlExpression.andLikeNoEmpty("user.account", search.getAccount());

        sqlExpression.andLikeNoEmpty("user.name", search.getName());
    }

    private void appendExpression(ISqlExpression sqlExpression, LoginAuthSearch search){
        sqlExpression.andEqNoEmpty("id", search.getId());

        sqlExpression.andNotEqNoEmpty("id", search.getNotId());

        sqlExpression.andEqNoEmpty("user_id", search.getUserId());

        sqlExpression.andEqNoEmpty("token", search.getToken());

        sqlExpression.andEqNoEmpty("type", search.getType());

        sqlExpression.andEqNoEmpty("status", search.getStatus());

    }
}