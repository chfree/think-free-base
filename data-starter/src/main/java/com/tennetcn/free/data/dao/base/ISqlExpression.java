package com.tennetcn.free.data.dao.base;

import com.tennetcn.free.data.enums.OrderEnum;
import com.tennetcn.free.data.message.OrderInfo;

import java.util.List;
import java.util.Map;


/**
 * @author  chenghuan-home
 * @email   79763939@qq.com
 * @comment 
 */
public interface ISqlExpression {
	
	ISqlExpression setParam(String param, Object value);
	
	ISqlExpression setParamAll(Map<String,Object> maps);
	
	ISqlExpression getSqlExpression();
	
	ISqlExpression andWhere(String value);
	
	ISqlExpression andEq(String column,String value);

	ISqlExpression andLike(String column,String value);

	ISqlExpression andRightLike(String column,String value);

	ISqlExpression andLikeNoEmpty(String column,String value);

	ISqlExpression andRightLikeNoEmpty(String column,String value);
	
	ISqlExpression andEqNoEmpty(String column,String value);
	
	ISqlExpression andNotEq(String column,String value);

	ISqlExpression andNotLike(String column,String value);

	ISqlExpression andNotLikeNoEmpty(String column,String value);
	
	ISqlExpression andNotEqNoEmpty(String column,String value);
	
	ISqlExpression andEq(String column,int value);
	
	ISqlExpression andEqNoEmpty(String column,int value);
	
	ISqlExpression andNotEq(String column,int value);
	
	ISqlExpression andNotEqNoEmpty(String column,int value);
	
	ISqlExpression andMainTableWhere(String value);
	
	ISqlExpression andWhereIn(String column,List<Object> values);
	
	ISqlExpression andWhereInString(String column,List<String> values);
	
	ISqlExpression andWhereInString(List<String> values,String join,String... columns);
	
	ISqlExpression orWhere(String value);
	
	ISqlExpression addOrder(String column,OrderEnum order);
	
	ISqlExpression addOrderInfoList(List<OrderInfo> orderInfos);
	
	ISqlExpression addOrders(OrderEnum order,String... columns);
	
	ISqlExpression addBody(String body);
	
	ISqlExpression addBody(String body,Class<?> tClass);
	
	ISqlExpression leftJoin(String body);
	
	ISqlExpression leftJoin(Class<?> tClass,String alias);
	
	ISqlExpression rightJoin(String body);
	
	ISqlExpression rightJoin(Class<?> tClass,String alias);
	
	ISqlExpression on(String body);
	
	ISqlExpression on(String left,String right);

	ISqlExpression selectAllFrom(Class<?> tClass);

	ISqlExpression selectAllFrom(Class<?> tClass,String alias);

	ISqlExpression select(String body);

	ISqlExpression appendSelect(String body);
	
	ISqlExpression selectCount();
	
	ISqlExpression selectCount(String column);
	
	ISqlExpression select(String... bodys);

	ISqlExpression appendSelect(String... bodys);
	
	ISqlExpression update(String body);
	
	ISqlExpression update(Class<?> tClass);
	
	ISqlExpression update(Class<?> tClass,String alias);
	
	ISqlExpression set(String column,String columnKey);
	
	ISqlExpression setColumn(String column,String value);
	
	ISqlExpression set(String... columnKeys);
	
	ISqlExpression delete();
	
	ISqlExpression from(String body);
	
	ISqlExpression from(Class<?> tClass);
	
	ISqlExpression from(Class<?> tClass,String alias);
	
	ISqlExpression setMainTableAlias(String mainTableAlias);
	
	String getMainTableAlias();
	
	ISqlExpression addGroup(String group);
	
	ISqlExpression addGroups(String... groups);
	
	String toSql();
	
	Map<String, Object> getParams();
}
