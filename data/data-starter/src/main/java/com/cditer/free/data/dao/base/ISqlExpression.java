package com.cditer.free.data.dao.base;

import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.util.SerializableFunction;
import com.cditer.free.data.message.OrderInfo;

import java.util.List;
import java.util.Map;


/**
 * @author chenghuan-home
 * @email 79763939@qq.com
 * @comment
 */
public interface ISqlExpression {

    ISqlExpression setParam(String param, Object value);

    ISqlExpression setParamAll(Map<String, Object> maps);

    ISqlExpression getSqlExpression();

    ISqlExpression andWhere(String value);

    ISqlExpression andEq(String column, String value);

    <T, R> ISqlExpression andEq(SerializableFunction<T, R> column, String value);

    ISqlExpression andLike(String column, String value);

    <T, R> ISqlExpression andLike(SerializableFunction<T, R> column, String value);

    ISqlExpression andRightLike(String column, String value);

    <T, R> ISqlExpression andRightLike(SerializableFunction<T, R> column, String value);

    ISqlExpression andLikeNoEmpty(String column, String value);

    <T, R> ISqlExpression andLikeNoEmpty(SerializableFunction<T, R> column, String value);

    ISqlExpression andRightLikeNoEmpty(String column, String value);

    <T, R> ISqlExpression andRightLikeNoEmpty(SerializableFunction<T, R> column, String value);

    ISqlExpression andEqNoEmpty(String column, String value);

    <T, R> ISqlExpression andEqNoEmpty(SerializableFunction<T, R> column, String value);

    ISqlExpression andNotEq(String column, String value);

    <T, R> ISqlExpression andNotEq(SerializableFunction<T, R> column, String value);

    ISqlExpression andNotLike(String column, String value);

    <T, R> ISqlExpression andNotLike(SerializableFunction<T, R> column, String value);

    ISqlExpression andNotLikeNoEmpty(String column, String value);

    <T, R> ISqlExpression andNotLikeNoEmpty(SerializableFunction<T, R> column, String value);

    ISqlExpression andNotEqNoEmpty(String column, String value);

    <T, R> ISqlExpression andNotEqNoEmpty(SerializableFunction<T, R> column, String value);

    ISqlExpression andEq(String column, Integer value);

    <T, R> ISqlExpression andEq(SerializableFunction<T, R> column, Integer value);

    ISqlExpression andEqNoEmpty(String column, Integer value);

    <T, R> ISqlExpression andEqNoEmpty(SerializableFunction<T, R> column, Integer value);

    ISqlExpression andNotEq(String column, Integer value);

    <T, R> ISqlExpression andNotEq(SerializableFunction<T, R> column, Integer value);

    ISqlExpression andNotEqNoEmpty(String column, Integer value);

    <T, R> ISqlExpression andNotEqNoEmpty(SerializableFunction<T, R> column, Integer value);

    ISqlExpression andMainTableWhere(String value);

    ISqlExpression andWhereIn(String column, ISqlExpression sqlExpression);

    ISqlExpression andWhereIn(String column, List<Object> values);

    ISqlExpression andWhereInString(String column, List<String> values);

    ISqlExpression andWhereInString(String column, String... values);

    ISqlExpression andWhereInString(List<String> values, String join, String... columns);

    ISqlExpression andWhereNotIn(String column, ISqlExpression sqlExpression);

    ISqlExpression andWhereNotIn(String column, List<Object> values);

    ISqlExpression andWhereNotInString(String column, List<String> values);

    ISqlExpression andWhereNotInString(String column, String... values);

    ISqlExpression andWhereNotInString(List<String> values, String join, String... columns);

    ISqlExpression orWhere(String value);

    ISqlExpression addOrder(String column, OrderEnum order);

    ISqlExpression addOrderInfoList(List<OrderInfo> orderInfos);

    ISqlExpression addOrders(OrderEnum order, String... columns);

    ISqlExpression addBody(String body);

    ISqlExpression leftJoin(String body);

    ISqlExpression leftJoin(Class<?> tClass, String alias);

    ISqlExpression innerJoin(String body);

    ISqlExpression innerJoin(Class<?> tClass, String alias);

    ISqlExpression rightJoin(String body);

    ISqlExpression rightJoin(Class<?> tClass, String alias);

    ISqlExpression on(String body);

    ISqlExpression on(String left, String right);

    ISqlExpression selectAllFrom(Class<?> tClass);

    ISqlExpression selectAllFrom(Class<?> tClass, String alias);

    ISqlExpression select(String body);

    ISqlExpression appendSelect(String body);

    <T, R> ISqlExpression appendSelect(SerializableFunction<T, R> column);

    ISqlExpression selectCount();

    ISqlExpression selectCount(String column);

    ISqlExpression select(String... bodys);

    <T, R> ISqlExpression select(SerializableFunction<T, R>... bodys);

    ISqlExpression appendSelect(String... bodys);

    ISqlExpression update(String body);

    ISqlExpression update(Class<?> tClass);

    ISqlExpression update(Class<?> tClass, String alias);

    ISqlExpression set(String column, String columnKey);

    ISqlExpression setValue(String column, String value);

    ISqlExpression setColumn(String column, String value);

    <T, R> ISqlExpression setColumn(SerializableFunction<T, R> column, String value);

    ISqlExpression setColumnNoEmpty(String column, String value);

    <T, R> ISqlExpression setColumnNoEmpty(SerializableFunction<T, R> column, String value);

    ISqlExpression set(String... columnKeys);

    ISqlExpression delete();

    ISqlExpression from(String body);

    ISqlExpression from(Class<?> tClass);

    ISqlExpression from(Class<?> tClass, String alias);

    ISqlExpression setMainTableAlias(String mainTableAlias);

    String getMainTableAlias();

    ISqlExpression groupBy(String group);

    ISqlExpression groupBys(String... groups);

    <T, R> ISqlExpression groupBy(SerializableFunction<T, R> group);

    <T, R> ISqlExpression groupBys(SerializableFunction<T, R>... groups);

    String toSql();

    Map<String, Object> getParams();

    ISqlExpression union(ISqlExpression sqlExpression1, ISqlExpression sqlExpression2);

    ISqlExpression addUnion(ISqlExpression sqlExpression);

    ISqlExpression unionAll(ISqlExpression sqlExpression1, ISqlExpression sqlExpression2);

    ISqlExpression addUnionAll(ISqlExpression sqlExpression);

    ISqlExpression limit(PagerModel pagerModel);

    ISqlExpression limit(int pageIndex, int pageSize);

    ISqlExpression selectDistinct(String body);

    <T, R> ISqlExpression selectDistinct(SerializableFunction<T, R>... bodys);

    ISqlExpression selectDistinct(String... bodys);

    ISqlExpression setColumn(String column, ISqlExpression sqlExpression);

    ISqlExpression callFunction(String funName);

    ISqlExpression setFunParam(String funName, String funValue);

    <T, R> String function2ColumnName(SerializableFunction<T, R> function);
}
