package com.tennetcn.free.data.dao.base;

import java.util.List;
import java.util.Map;

import com.tennetcn.free.data.enums.OrderEnum;
import com.tennetcn.free.data.message.OrderInfo;
import com.tennetcn.free.data.message.SearchModel;


/**
 * @author  chenghuan-home
 * @email   79763939@qq.com
 * @comment 
 */
public interface ISqlExpression {
	
	public ISqlExpression setParam(String param, Object value);
	
	public ISqlExpression setParamAll(Map<String,Object> maps);
	
	public ISqlExpression getSqlExpression();
	
	public ISqlExpression andWhere(String value);
	
	public ISqlExpression andEq(String column,String value);
	
	public ISqlExpression andEqNoEmpty(String column,String value);
	
	public ISqlExpression andNotEq(String column,String value);
	
	public ISqlExpression andNotEqNoEmpty(String column,String value);
	
	public ISqlExpression andEq(String column,int value);
	
	public ISqlExpression andEqNoEmpty(String column,int value);
	
	public ISqlExpression andNotEq(String column,int value);
	
	public ISqlExpression andNotEqNoEmpty(String column,int value);
	
	public ISqlExpression andMainTableWhere(String value);
	
	public ISqlExpression andWhereIn(String column,List<Object> values);
	
	public ISqlExpression andWhereInString(String column,List<String> values);
	
	public ISqlExpression andWhereInString(List<String> values,String join,String... columns);
	
	public ISqlExpression orWhere(String value);
	
	public ISqlExpression addOrder(String column,OrderEnum order);
	
	public ISqlExpression addOrderInfoList(List<OrderInfo> orderInfos);
	
	public ISqlExpression addOrders(OrderEnum order,String... columns);
	
	public ISqlExpression addBody(String body);
	
	public ISqlExpression addBody(String body,Class<?> tClass);
	
	public ISqlExpression leftJoin(String body);
	
	public ISqlExpression leftJoin(Class<?> tClass,String alias);
	
	public ISqlExpression rightJoin(String body);
	
	public ISqlExpression rightJoin(Class<?> tClass,String alias);
	
	public ISqlExpression on(String body);
	
	public ISqlExpression on(String left,String right);
	
	public ISqlExpression select(String body);
	
	public ISqlExpression selectCount();
	
	public ISqlExpression selectCount(String column);
	
	public ISqlExpression select(String... bodys);
	
	public ISqlExpression update(String body);
	
	public ISqlExpression update(Class<?> tClass);
	
	public ISqlExpression update(Class<?> tClass,String alias);
	
	public ISqlExpression set(String column,String columnKey);
	
	public ISqlExpression setColumn(String column,String value);
	
	public ISqlExpression set(String... columnKeys);
	
	public ISqlExpression delete();
	
	public ISqlExpression from(String body);
	
	public ISqlExpression from(Class<?> tClass);
	
	public ISqlExpression from(Class<?> tClass,String alias);
	
	public ISqlExpression setMainTableAlias(String mainTableAlias);
	
	public String getMainTableAlias();
	
	public ISqlExpression addGroup(String group);
	
	public ISqlExpression addGroups(String... groups);
	
	public String toSql();
	
	public Map<String, Object> getParams();
	
	public ISqlExpression resolveSearchModel(SearchModel search);
	
	public ISqlExpression limit(int start,int length);
}
