package com.cditer.free.data.dao.base.impl;

import com.cditer.free.core.util.ReflectUtils;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.test.model.TestDataUser;
import com.cditer.free.data.utils.SqlExpressionFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Column;

public class SqlExpressionTest {
    private ISqlExpression getEmptySql() {
        return SqlExpressionFactory.createExpression();
    }

    @Test
    public void setParam() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.setParam("name", "cheng");

        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
    }

    @Test
    public void getSqlExpression() {
    }

    @Test
    public void andWhere() {
    }

    @Test
    public void andEq() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from(TestDataUser.class).andEq("name", "cheng");

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from basic_authority_user where (name=#{name})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
    }

    @Test
    public void testAndEq() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from(TestDataUser.class).andEq(TestDataUser::getName, "cheng");

        Column fieldByAnno = ReflectUtils.getFieldByAnno(TestDataUser::getName, Column.class);

        Assert.assertEquals(sqlExpression.toSql(), String.format("select name,age from basic_authority_user where (%s=#{name})", fieldByAnno.name()));
        Assert.assertEquals(sqlExpression.getParams().get(fieldByAnno.name()), "cheng");
    }

    @Test
    public void andLike() {
    }

    @Test
    public void andRightLike() {
    }

    @Test
    public void andLikeNoEmpty() {
    }

    @Test
    public void andRightLikeNoEmpty() {
    }

    @Test
    public void andEqNoEmpty() {
    }

    @Test
    public void andNotEq() {
    }

    @Test
    public void andNotLike() {
    }

    @Test
    public void andNotLikeNoEmpty() {
    }

    @Test
    public void andNotEqNoEmpty() {
    }

    @Test
    public void testAndEq1() {
    }

    @Test
    public void testAndEqNoEmpty() {
    }

    @Test
    public void testAndNotEq() {
    }

    @Test
    public void testAndNotEqNoEmpty() {
    }

    @Test
    public void andMainTableWhere() {
    }

    @Test
    public void orWhere() {
    }

    @Test
    public void addOrders() {
    }

    @Test
    public void addOrder() {
    }

    @Test
    public void addBody() {
    }

    @Test
    public void testAddBody() {
    }

    @Test
    public void getParams() {
    }

    @Test
    public void union() {
    }

    @Test
    public void addUnion() {
    }

    @Test
    public void unionAll() {
    }

    @Test
    public void addUnionAll() {
    }

    @Test
    public void limit() {
    }

    @Test
    public void testLimit() {
    }

    @Test
    public void selectDistinct() {
    }

    @Test
    public void testSelectDistinct() {
    }

    @Test
    public void setColumn() {
    }

    @Test
    public void callFunction() {
    }

    @Test
    public void setFunParam() {
    }

    @Test
    public void toSql() {
    }

    @Test
    public void setParamAll() {
    }

    @Test
    public void addGroup() {
    }

    @Test
    public void addGroups() {
    }

    @Test
    public void leftJoin() {
    }

    @Test
    public void testLeftJoin() {
    }

    @Test
    public void innerJoin() {
    }

    @Test
    public void testInnerJoin() {
    }

    @Test
    public void rightJoin() {
    }

    @Test
    public void testRightJoin() {
    }

    @Test
    public void on() {
    }

    @Test
    public void testOn() {
    }

    @Test
    public void selectAllFrom() {
    }

    @Test
    public void testSelectAllFrom() {
    }

    @Test
    public void select() {
    }

    @Test
    public void appendSelect() {
    }

    @Test
    public void testSelect() {
    }

    @Test
    public void testAppendSelect() {
    }

    @Test
    public void selectCount() {
    }

    @Test
    public void testSelectCount() {
    }

    @Test
    public void update() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testUpdate1() {
    }

    @Test
    public void set() {
    }

    @Test
    public void setValue() {
    }

    @Test
    public void testSetColumn() {
    }

    @Test
    public void testSet() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void from() {
    }

    @Test
    public void testFrom() {
    }

    @Test
    public void testFrom1() {
    }

    @Test
    public void setMainTableAlias() {
    }

    @Test
    public void getMainTableAlias() {
    }

    @Test
    public void addOrderInfoList() {
    }

    @Test
    public void andWhereIn() {
    }

    @Test
    public void testAndWhereIn() {
    }

    @Test
    public void andWhereInString() {
    }

    @Test
    public void testAndWhereInString() {
    }

    @Test
    public void testAndWhereInString1() {
    }

    @Test
    public void andWhereNotIn() {
    }

    @Test
    public void testAndWhereNotIn() {
    }

    @Test
    public void andWhereNotInString() {
    }

    @Test
    public void testAndWhereNotInString() {
    }

    @Test
    public void testAndWhereNotInString1() {
    }

    @Test
    public void testSelectFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select(TestDataUser::getName,TestDataUser::getAccount).from(TestDataUser.class);

        Assert.assertEquals(sqlExpression.toSql(), String.format("select name,account from basic_authority_user"));
    }
}