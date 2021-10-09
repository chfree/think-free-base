package com.cditer.free.data.dao.base.impl;

import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.util.ReflectUtils;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.test.model.TestDataUser;
import com.cditer.free.data.utils.SqlExpressionFactory;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.Map;

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
        sqlExpression.select("name,age").from("user").andEqNoEmpty("name", "cheng").andEqNoEmpty("age", nullInteger());

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name=#{name})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("age"));
    }

    private Integer nullInteger(){
        return null;
    }

    @Test
    public void andEqNoEmptyFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andEqNoEmpty(TestDataUser::getName, "cheng").andEqNoEmpty(TestDataUser::getAccount, nullInteger());

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name=#{name})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("account"));
    }

    @Test
    public void andNotEq() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotEq("name", "cheng").andNotEq("age", nullInteger());

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name!=#{name}) and (age!=#{age})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertTrue(sqlExpression.getParams().containsKey("age"));
    }

    @Test
    public void andNotEqFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotEq(TestDataUser::getName, "cheng").andNotEq(TestDataUser::getAccount, nullInteger());

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
        sqlExpression.select("name,age").from("user").andNotEqNoEmpty("name", "cheng").andNotEqNoEmpty("age", nullInteger());

        Assert.assertEquals(sqlExpression.toSql(),"select name,age from user where (name!=#{name})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertFalse(sqlExpression.getParams().containsKey("age"));

    }

    @Test
    public void andNotEqNoEmptyFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").andNotEqNoEmpty(TestDataUser::getName, "cheng").andNotEqNoEmpty(TestDataUser::getAccount, nullInteger());

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
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").limit(1,20);

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user limit 0,20");

        sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").limit(2,15);

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user limit 15,15");
    }

    @Test
    public void testLimit() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").limit(new PagerModel(20,1));

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user limit 0,20");

        sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").limit(new PagerModel(15,2));

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user limit 15,15");
    }

    @Test
    public void selectDistinct() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.selectDistinct("name","age").from("user");

        Assert.assertEquals(sqlExpression.toSql(), "select distinct name,age from user");
    }

    @Test
    public void selectDistinctFcn() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.selectDistinct(TestDataUser::getName, TestDataUser::getAccount).from("user");

        Assert.assertEquals(sqlExpression.toSql(), "select distinct name,account from user");
    }

    @Test
    public void testSelectDistinct() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.selectDistinct("name,age").from("user");

        Assert.assertEquals(sqlExpression.toSql(), "select distinct name,age from user");
    }

    @Test
    public void setColumn() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.update("user").setColumn("name", "cheng").andEq("account", "cheng");

        Assert.assertEquals(sqlExpression.toSql(), "update user set name=#{name} where (account=#{account})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertEquals(sqlExpression.getParams().get("account"), "cheng");
    }

    @Test
    public void setColumnFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.update("user").setColumn(TestDataUser::getName, "cheng").andEq(TestDataUser::getAccount, "cheng");

        Assert.assertEquals(sqlExpression.toSql(), "update user set name=#{name} where (account=#{account})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertEquals(sqlExpression.getParams().get("account"), "cheng");
    }

    @Test
    public void setColumnNoEmpty() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.update("user").setColumnNoEmpty("name", "cheng").setColumnNoEmpty("age", null).andEq("account", "cheng");

        Assert.assertEquals(sqlExpression.toSql(), "update user set name=#{name} where (account=#{account})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertEquals(sqlExpression.getParams().get("account"), "cheng");
        Assert.assertNull(sqlExpression.getParams().get("age"));
    }

    @Test
    public void setColumnNoEmptyFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.update("user").setColumnNoEmpty(TestDataUser::getName, "cheng").setColumnNoEmpty(TestDataUser::getBuId, null).andEq(TestDataUser::getAccount, "cheng");

        Assert.assertEquals(sqlExpression.toSql(), "update user set name=#{name} where (account=#{account})");
        Assert.assertEquals(sqlExpression.getParams().get("name"), "cheng");
        Assert.assertEquals(sqlExpression.getParams().get("account"), "cheng");
        Assert.assertNull(sqlExpression.getParams().get("bu_id"));
    }

    @Test
    public void callFunction() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.callFunction("testFun").setFunParam("name", "cheng").setFunParam("age", "18");

        Assert.assertEquals(sqlExpression.toSql(), "select testFun(#{name},#{age})");
    }

    @Test
    public void setFunParam() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.callFunction("testFun").setFunParam("name", "cheng").setFunParam("age", "18");

        Assert.assertEquals(sqlExpression.toSql(), "select testFun(#{name},#{age})");
        Assert.assertEquals(sqlExpression.getParams().get("name"),"cheng");
        Assert.assertEquals(sqlExpression.getParams().get("age"),"18");
    }

    @Test
    public void setParamAll() {
        ISqlExpression sqlExpression = getEmptySql();

        Map<String,Object> params = new HashMap<>();
        params.put("name", "cheng");

        sqlExpression.select("name,age").from("user").andWhere("name=#{name}").setParamAll(params);

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user where (name=#{name})");
        Assert.assertEquals(params.get("name"), sqlExpression.getParams().get("name"));
    }

    @Test
    public void groupBy() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").groupBy("name,age").groupBy("account");

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user group by name,age,account");
    }

    @Test
    public void groupBys() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").groupBys("name","age").groupBy("account");

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user group by name,age,account");
    }

    @Test
    public void groupByFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").groupBy(TestDataUser::getName).groupBy(TestDataUser::getAccount);

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user group by name,account");
    }

    @Test
    public void groupBysFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("name,age").from("user").groupBys(TestDataUser::getName,TestDataUser::getAccount);

        Assert.assertEquals(sqlExpression.toSql(), "select name,age from user group by name,account");
    }

    @Test
    public void leftJoin() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").leftJoin("dept d").on("u.id","d.id");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u left join dept d on (u.id=d.id)");
    }

    @Test
    public void testLeftJoin() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").leftJoin(TestDataUser.class, "d").on("u.id","d.id");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u left join basic_authority_user d on (u.id=d.id)");
    }

    @Test
    public void innerJoin() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").innerJoin("dept d").on("u.id","d.id");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u inner join dept d on (u.id=d.id)");
    }

    @Test
    public void testInnerJoin() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").innerJoin(TestDataUser.class, "d").on("u.id","d.id");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u inner join basic_authority_user d on (u.id=d.id)");
    }

    @Test
    public void rightJoin() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").rightJoin("dept d").on("u.id","d.id");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u right join dept d on (u.id=d.id)");
    }

    @Test
    public void testRightJoin() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").rightJoin(TestDataUser.class, "d").on("u.id","d.id");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u right join basic_authority_user d on (u.id=d.id)");
    }

    @Test
    public void on() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").rightJoin("dept d").on("u.id","d.id");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u right join dept d on (u.id=d.id)");
    }

    @Test
    public void testOn() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").rightJoin("dept d").on("u.id=d.id");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u right join dept d on (u.id=d.id)");
    }

    @Test
    public void testOnFun() {
        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.select("user,age").from("user u").rightJoin("dept d").on(TestDataUser::getName, "u",TestDataUser::getAccount,"d");

        Assert.assertEquals(sqlExpression.toSql(), "select user,age from user u right join dept d on (u.name=d.account)");
    }

    @Test
    public void selectAllFrom() {
        // 依赖于@Mapper注解启动，所以暂时先初始化EntityNameMap
        EntityHelper.initEntityNameMap(TestDataUser.class, new Config());

        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.selectAllFrom(TestDataUser.class);



        Assert.assertEquals(sqlExpression.toSql(), "select id,name,account,password,delete_mark,create_date,bu_id from basic_authority_user");
    }

    @Test
    public void testSelectAllFrom() {
        // 依赖于@Mapper注解启动，所以暂时先初始化EntityNameMap
        EntityHelper.initEntityNameMap(TestDataUser.class, new Config());

        ISqlExpression sqlExpression = getEmptySql();
        sqlExpression.selectAllFrom(TestDataUser.class, "u");



        Assert.assertEquals(sqlExpression.toSql(), "select u.id,u.name,u.account,u.password,u.delete_mark,u.create_date,u.bu_id from basic_authority_user u");
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