package com.cditer.free.data.dao.base.impl;

import com.cditer.free.core.enums.OrderEnum;
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
        ISqlExpression sqlExpression = getEmptySql();

        Assert.assertNotNull(sqlExpression.getSqlExpression());
        Assert.assertEquals(sqlExpression, sqlExpression.getSqlExpression());
    }

    @Test
    public void andWhere() {
        ISqlExpression sqlExpression = getEmptySql();

        sqlExpression.select("name").from("user").andWhere("id=#{id}");

        Assert.assertEquals(sqlExpression.toSql(), "select name from user where (id=#{id})");
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
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andLike("name", "cheng");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name like concat('%',#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
    }

    @Test
    public void andLikeFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andLike(TestDataUser::getName, "cheng");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name like concat('%',#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
    }

    @Test
    public void andRightLike() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andRightLike("name", "cheng");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name like concat(#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
    }

    @Test
    public void andRightLikeFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andRightLike(TestDataUser::getName, "cheng");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name like concat(#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
    }

    @Test
    public void andLikeNoEmpty() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andLikeNoEmpty("name", "cheng").andLikeNoEmpty("age", null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name like concat('%',#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("age"));
    }

    @Test
    public void andLikeNoEmptyFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andLikeNoEmpty(TestDataUser::getName, "cheng").andLikeNoEmpty(TestDataUser::getAccount, null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name like concat('%',#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("account"));
    }

    @Test
    public void andRightLikeNoEmpty() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andRightLikeNoEmpty("name", "cheng").andRightLikeNoEmpty("age", null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name like concat(#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("age"));
    }

    @Test
    public void andRightLikeNoEmptyFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andRightLikeNoEmpty(TestDataUser::getName, "cheng").andRightLikeNoEmpty(TestDataUser::getAccount, null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name like concat(#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("account"));
    }

    @Test
    public void andEqNoEmpty() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andEqNoEmpty("name", "cheng").andEqNoEmpty("age", null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name=#{name})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("age"));
    }

    @Test
    public void andEqNoEmptyFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andEqNoEmpty(TestDataUser::getName, "cheng").andEqNoEmpty(TestDataUser::getAccount, null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name=#{name})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("account"));
    }

    @Test
    public void andNotEq() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotEq("name", "cheng").andNotEq("age", null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name!=#{name}) and (age!=#{age})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertTrue(sqlExpression.getParams().containsKey("age"));
    }

    @Test
    public void andNotEqFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotEq(TestDataUser::getName, "cheng").andNotEq(TestDataUser::getAccount, null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name!=#{name}) and (account!=#{account})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertTrue(sqlExpression.getParams().containsKey("account"));
    }

    @Test
    public void andNotLike() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotLike("name", "cheng");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name not like concat('%',#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
    }

    @Test
    public void andNotLikeFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotLike(TestDataUser::getName, "cheng");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name not like concat('%',#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
    }

    @Test
    public void andNotLikeNoEmpty() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotLikeNoEmpty("name", "cheng").andNotLikeNoEmpty("age", null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name not like concat('%',#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("age"));
    }

    @Test
    public void andNotLikeNoEmptyFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotLikeNoEmpty(TestDataUser::getName, "cheng").andNotLikeNoEmpty(TestDataUser::getAccount, null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name not like concat('%',#{name},'%'))");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("account"));
    }

    @Test
    public void andNotEqNoEmpty() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotEqNoEmpty("name", "cheng").andNotEqNoEmpty("age", null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name!=#{name})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("age"));

    }

    @Test
    public void andNotEqNoEmptyFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotEqNoEmpty(TestDataUser::getName, "cheng").andNotEqNoEmpty(TestDataUser::getAccount, null);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name!=#{name})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("account"));
    }

    @Test
    public void andMainTableWhere() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.setMainTableAlias("u");

        sqlExpression.select("name,age").from("user").andMainTableWhere("name=#{name}");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (u.name=#{name})");
    }

    @Test
    public void orWhere() {
        ISqlExpression sqlExpression = getEmptySql();

        sqlExpression.select("name,age").from("user").andEq("name", "cheng").orWhere("age=123");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name=#{name}) or (age=123)");
    }

    @Test
    public void addOrders() {
        ISqlExpression sqlExpression = getEmptySql();

        sqlExpression.select("name,age").from("user").andEq("name", "cheng").addOrders(OrderEnum.asc, "name", "age");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name=#{name}) order by name asc,age asc");
    }

    @Test
    public void addOrder() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andEq("name", "cheng").addOrder("name", OrderEnum.asc).addOrder("age", OrderEnum.desc);

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name=#{name}) order by name asc,age desc");
    }

    @Test
    public void addBody() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.addBody("select name,age").from(TestDataUser.class, "u").andEq(TestDataUser::getName, "cheng");

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from basic_authority_user u where (name=#{name})");
        Assert.assertEquals("cheng", sqlExpression.getParams().get("name"));
    }

    @Test
    public void getParams() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.setParam("name", "cheng");

        Assert.assertEquals("cheng", sqlExpression.getParams().get("name"));
    }

    @Test
    public void union() {
        ISqlExpression sqlExpression = getEmptySql();
        ISqlExpression sql1 = getEmptySql();
        ISqlExpression sql2 = getEmptySql();

        sql1.select("name,age").from("user1").andEq("name", "cheng");
        sql2.select("name,age").from("user2").andEq(TestDataUser::getAccount, "1234");

        sqlExpression.union(sql1, sql2);

        Assert.assertEquals(sqlExpression.toSql(),"(select name,age from user1 where (name=#{name})) union (select name,age from user2 where (account=#{account}))");
        Assert.assertEquals(sqlExpression.getParams().get("account"), sql2.getParams().get("account"));
        Assert.assertEquals(sqlExpression.getParams().get("name"), sql1.getParams().get("name"));
    }

    @Test
    public void addUnion() {
        ISqlExpression sqlExpression = getEmptySql();
        ISqlExpression sql1 = getEmptySql();
        ISqlExpression sql2 = getEmptySql();

        sql1.select("name,age").from("user1").andEq("name", "cheng");
        sql2.select("name,age").from("user2").andEq(TestDataUser::getAccount, "1234");

        sqlExpression.union(sql1, sql2);

        ISqlExpression sql3 = getEmptySql();
        sql3.select("name,age").from("user3").andEq(TestDataUser::getBuId, "buid");
        sqlExpression.addUnion(sql3);

        Assert.assertEquals(sqlExpression.toSql(),"(select name,age from user1 where (name=#{name})) union (select name,age from user2 where (account=#{account})) union (select name,age from user3 where (bu_id=#{bu_id}))");
        Assert.assertEquals(sqlExpression.getParams().get("account"), sql2.getParams().get("account"));
        Assert.assertEquals(sqlExpression.getParams().get("name"), sql1.getParams().get("name"));
        Assert.assertEquals(sqlExpression.getParams().get("bu_id"), sql3.getParams().get("bu_id"));
    }

    @Test
    public void unionAll() {
        ISqlExpression sqlExpression = getEmptySql();
        ISqlExpression sql1 = getEmptySql();
        ISqlExpression sql2 = getEmptySql();

        sql1.select("name,age").from("user1").andEq("name", "cheng");
        sql2.select("name,age").from("user2").andEq(TestDataUser::getAccount, "1234");

        sqlExpression.unionAll(sql1, sql2);

        Assert.assertEquals(sqlExpression.toSql(),"(select name,age from user1 where (name=#{name})) union all (select name,age from user2 where (account=#{account}))");
        Assert.assertEquals(sqlExpression.getParams().get("account"), sql2.getParams().get("account"));
        Assert.assertEquals(sqlExpression.getParams().get("name"), sql1.getParams().get("name"));
    }

    @Test
    public void addUnionAll() {
        ISqlExpression sqlExpression = getEmptySql();
        ISqlExpression sql1 = getEmptySql();
        ISqlExpression sql2 = getEmptySql();

        sql1.select("name,age").from("user1").andEq("name", "cheng");
        sql2.select("name,age").from("user2").andEq(TestDataUser::getAccount, "1234");

        sqlExpression.unionAll(sql1, sql2);

        ISqlExpression sql3 = getEmptySql();
        sql3.select("name,age").from("user3").andEq(TestDataUser::getBuId, "buid");
        sqlExpression.addUnionAll(sql3);

        Assert.assertEquals(sqlExpression.toSql(),"(select name,age from user1 where (name=#{name})) union all (select name,age from user2 where (account=#{account})) union all (select name,age from user3 where (bu_id=#{bu_id}))");
        Assert.assertEquals(sqlExpression.getParams().get("account"), sql2.getParams().get("account"));
        Assert.assertEquals(sqlExpression.getParams().get("name"), sql1.getParams().get("name"));
        Assert.assertEquals(sqlExpression.getParams().get("bu_id"), sql3.getParams().get("bu_id"));
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
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name").select("age").from(TestDataUser.class);

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from basic_authority_user");
    }

    @Test
    public void selectFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select(TestDataUser::getName).select(TestDataUser::getAccount).from(TestDataUser.class);

        Assert.assertEquals(sqlExpression.toSql(), "select name,account from basic_authority_user");
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
        sqlExpression.select(TestDataUser::getName,TestDataUser::getAccount).appendSelect(TestDataUser::getBuId).from(TestDataUser.class);

        Assert.assertEquals(sqlExpression.toSql(), String.format("select name,account,bu_id from basic_authority_user"));
    }
}