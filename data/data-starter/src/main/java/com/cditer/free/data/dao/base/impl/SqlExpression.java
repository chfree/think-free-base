package com.cditer.free.data.dao.base.impl;

import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.util.ReflectUtils;
import com.cditer.free.core.util.SerializableFunction;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.message.OrderInfo;
import com.cditer.free.data.message.SqlOperateMode;
import com.cditer.free.data.utils.ClassAnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author chenghuan-home
 * @email 79763939@qq.com
 * @comment
 */

@Component
public class SqlExpression implements ISqlExpression {

    private String sqlOperateMode = SqlOperateMode.select;

    private Map<String, Object> params = null;

    private String mainTableAlias = "";

    private String fromMainTableAlias = "";

    private List<String> wheres = new ArrayList<>();

    private StringBuffer bodyBuffer = new StringBuffer();

    private List<String> orderList = new ArrayList<>();

    private StringBuffer groupBuffer = new StringBuffer();

    private StringBuffer setBuffer = new StringBuffer();

    private StringBuffer fromBuffer = new StringBuffer();

    private StringBuffer limitBuffer = new StringBuffer();

    private List<String> callFunParam = new ArrayList<>();

    private Map<String, String> insertMap = null;

    public SqlExpression() {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
    }


    /**
     * 设置参数。
     *
     * @param param
     * @param value
     * @return
     */
    @Override
    public SqlExpression setParam(String param, Object value) {
        params.put(param, value);
        return this;
    }


    public SqlExpression getSqlExpression() {
        return this;
    }

    @Override
    public ISqlExpression andWhere(String value) {
        wheres.add("and (" + value + ")");
        return this;
    }

    @Override
    public ISqlExpression andEq(String column, String value) {
        column = resolveColumnMainTable(column);
        String paramName = resolveColumn(column);

        this.andWhere(column + "=#{" + paramName + "}")
                .setParam(paramName, value);
        return this;
    }

    @Override
    public <T, R> ISqlExpression andEq(SerializableFunction<T, R> column, String value) {
        return andEq(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andLike(String column, String value) {
        column = resolveColumnMainTable(column);
        String paramName = resolveColumn(column);

        this.andWhere(column + " like concat('%',#{" + paramName + "},'%')")
                .setParam(paramName, value);
        return this;
    }

    @Override
    public <T, R> ISqlExpression andLike(SerializableFunction<T, R> column, String value) {
        return andLike(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andRightLike(String column, String value) {
        column = resolveColumnMainTable(column);
        String paramName = resolveColumn(column);

        this.andWhere(column + " like concat(#{" + paramName + "},'%')")
                .setParam(paramName, value);
        return this;
    }

    @Override
    public <T, R> ISqlExpression andRightLike(SerializableFunction<T, R> column, String value) {
        return andRightLike(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andLikeNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andLike(column, value);
        }
        return this;
    }

    @Override
    public <T, R> ISqlExpression andLikeNoEmpty(SerializableFunction<T, R> column, String value) {
        return andLikeNoEmpty(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andRightLikeNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andRightLike(column, value);
        }
        return this;
    }

    @Override
    public <T, R> ISqlExpression andRightLikeNoEmpty(SerializableFunction<T, R> column, String value) {
        return andRightLikeNoEmpty(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andEqNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andEq(column, value);
        }
        return this;
    }

    @Override
    public <T, R> ISqlExpression andEqNoEmpty(SerializableFunction<T, R> column, String value) {
        return andEqNoEmpty(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andNotEq(String column, String value) {
        column = resolveColumnMainTable(column);
        String paramName = resolveColumn(column);

        this.andWhere(column + "!=#{" + paramName + "}")
                .setParam(paramName, value);
        return this;
    }

    @Override
    public <T, R> ISqlExpression andNotEq(SerializableFunction<T, R> column, String value) {
        return andNotEq(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andNotLike(String column, String value) {
        column = resolveColumnMainTable(column);
        String paramName = resolveColumn(column);

        this.andWhere(column + " not like concat('%',#{" + paramName + "},'%')")
                .setParam(paramName, value);
        return this;
    }

    @Override
    public <T, R> ISqlExpression andNotLike(SerializableFunction<T, R> column, String value) {
        return andNotLike(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andNotLikeNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andNotLike(column, value);
        }
        return this;
    }

    @Override
    public <T, R> ISqlExpression andNotLikeNoEmpty(SerializableFunction<T, R> column, String value) {
        return andNotLikeNoEmpty(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andNotEqNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andNotEq(column, value);
        }
        return this;
    }

    @Override
    public <T, R> ISqlExpression andNotEqNoEmpty(SerializableFunction<T, R> column, String value) {
        return andNotEqNoEmpty(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andEq(String column, Integer value) {
        if (value == null) {
            return andEq(column, nullStr());
        }
        return andEq(column, String.valueOf(value.intValue()));
    }

    @Override
    public <T, R> ISqlExpression andEq(SerializableFunction<T, R> column, Integer value) {
        return andEq(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andEqNoEmpty(String column, Integer value) {
        if (value == null) {
            return this;
        }
        return andEq(column, value);
    }

    @Override
    public <T, R> ISqlExpression andEqNoEmpty(SerializableFunction<T, R> column, Integer value) {
        return andEqNoEmpty(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andNotEq(String column, Integer value) {
        if (value == null) {
            return andNotEq(column, nullStr());
        }
        return andNotEq(column, String.valueOf(value));
    }

    private String nullStr() {
        return null;
    }

    @Override
    public <T, R> ISqlExpression andNotEq(SerializableFunction<T, R> column, Integer value) {
        return andNotEq(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andNotEqNoEmpty(String column, Integer value) {
        if (value == null) {
            return this;
        }
        return andNotEq(column, value);
    }

    @Override
    public <T, R> ISqlExpression andNotEqNoEmpty(SerializableFunction<T, R> column, Integer value) {
        return andNotEqNoEmpty(function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression andMainTableWhere(String value) {
        wheres.add("and (" + this.getMainTableAlias() + value + ")");
        return this;
    }

    @Override
    public ISqlExpression orWhere(String value) {
        wheres.add("or (" + value + ")");
        return this;
    }

    @Override
    public ISqlExpression addOrders(OrderEnum order, String... columns) {
        if (columns == null || columns.length <= 0) {
            return this;
        }

        List<String> orders = Arrays.stream(columns).map(item -> item + " " + order.name()).collect(Collectors.toList());
        orderList.addAll(orders);

        return this;
    }

    @Override
    public ISqlExpression addOrder(String column, OrderEnum order) {
        if (!StringUtils.hasText(column)) {
            return this;
        }

        orderList.add(column + " " + order.name());

        return this;
    }

    @Override
    public ISqlExpression addBody(String body) {
        bodyBuffer.append(body);
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public ISqlExpression union(ISqlExpression sqlExpression1, ISqlExpression sqlExpression2) {
        this.addBody("(" + sqlExpression1.toSql() + ") union (" + sqlExpression2.toSql() + ")")
                .setParamAll(sqlExpression1.getParams()).setParamAll(sqlExpression2.getParams());

        return this;
    }

    @Override
    public ISqlExpression addUnion(ISqlExpression sqlExpression) {
        this.addBody(" union (" + sqlExpression.toSql() + ")").setParamAll(sqlExpression.getParams());

        return this;
    }

    @Override
    public ISqlExpression unionAll(ISqlExpression sqlExpression1, ISqlExpression sqlExpression2) {
        this.addBody("(" + sqlExpression1.toSql() + ") union all (" + sqlExpression2.toSql() + ")")
                .setParamAll(sqlExpression1.getParams()).setParamAll(sqlExpression2.getParams());

        return this;
    }

    @Override
    public ISqlExpression addUnionAll(ISqlExpression sqlExpression) {
        this.addBody(" union all (" + sqlExpression.toSql() + ")").setParamAll(sqlExpression.getParams());

        return this;
    }

    @Override
    public ISqlExpression limit(PagerModel pagerModel) {
        limitBuffer.append("limit " + pagerModel.getCurrentSize() + "," + pagerModel.getPageSize());
        return this;
    }

    @Override
    public ISqlExpression limit(int pageIndex, int pageSize) {
        return limit(new PagerModel(pageSize, pageIndex));
    }

    @Override
    public ISqlExpression selectDistinct(String body) {
        select("distinct " + body);
        return this;
    }

    @Override
    public <T, R> ISqlExpression selectDistinct(SerializableFunction<T, R>... bodys) {
        return selectDistinct(null, bodys);
    }

    @Override
    public <T, R> ISqlExpression selectDistinct(String tblAlias, SerializableFunction<T, R>... bodys) {
        if (bodys == null || bodys.length <= 0) {
            return this;
        }
        final String finalTblAlias = tblAlaisFormat(tblAlias);

        List<String> list = Arrays.stream(bodys).map(item -> finalTblAlias + function2ColumnName(item)).collect(Collectors.toList());
        return selectDistinct(list.toArray(new String[0]));
    }

    @Override
    public ISqlExpression selectDistinct(String... bodys) {
        sqlOperateMode = SqlOperateMode.select;

        if (bodys == null && bodys.length <= 0) {
            return this;
        }
        String body = String.join(",", bodys);
        select("distinct " + body);

        return this;
    }

    @Override
    public ISqlExpression setColumn(String column, ISqlExpression sqlExpression) {
        return setValue(column, "(" + sqlExpression.toSql() + ")").setParamAll(sqlExpression.getParams());
    }

    @Override
    public ISqlExpression callFunction(String funName) {
        sqlOperateMode = SqlOperateMode.callFun;
        this.addBody("select " + funName);

        return this;
    }

    @Override
    public ISqlExpression setFunParam(String funName, String funValue) {
        callFunParam.add("#{" + funName + "}");
        setParam(funName, funValue);
        return this;
    }

    @Override
    public String toSql() {
        List<String> result = new ArrayList<>();
        result.add(bodyBuffer.toString());
        if (SqlOperateMode.select.equals(sqlOperateMode)) {
            if (StringUtils.hasText(fromBuffer.toString())) {
                result.add(fromBuffer.toString());
            }

            String whereSql = getWhereString();
            if (StringUtils.hasText(whereSql)) {
                result.add(whereSql);
            }
            if (StringUtils.hasText(groupBuffer.toString())) {
                result.add(groupBuffer.toString());
            }
            if (!CollectionUtils.isEmpty(orderList)) {
                String orderStr = "order by " + String.join(",", orderList);
                result.add(orderStr);
            }
            if (StringUtils.hasText(limitBuffer.toString())) {
                result.add(limitBuffer.toString());
            }
        } else if (SqlOperateMode.update.equals(sqlOperateMode)) {
            if (StringUtils.hasText(fromBuffer.toString())) {
                result.add(fromBuffer.toString());
            }
            if (StringUtils.hasText(setBuffer.toString())) {
                result.add(setBuffer.toString());
            }
            String whereSql = getWhereString();
            if (StringUtils.hasText(whereSql)) {
                result.add(whereSql);
            }
        } else if (SqlOperateMode.delete.equals(sqlOperateMode)) {
            if (StringUtils.hasText(fromBuffer.toString())) {
                result.add(fromBuffer.toString());
            }

            String whereSql = getWhereString();
            if (StringUtils.hasText(whereSql)) {
                result.add(whereSql);
            }
        } else if (SqlOperateMode.insert.equals(sqlOperateMode)) {
            String keys = String.join(",", insertMap.keySet());
            String vals = String.join(",", insertMap.values());

            return String.format("%s(%s) values(%s)", String.join(",", result), keys, vals);
        } else if (SqlOperateMode.callFun.equals(sqlOperateMode)) {
            result.add(String.format("(%s)", String.join(",", callFunParam)));

            return String.join("", result);
        }

        return String.join(" ", result);
    }

    private String getWhereString() {
        if (wheres.size() == 0) {
            return "";
        }

        if (wheres.get(0).indexOf("and ") == 0 || wheres.get(0).indexOf("or ") == 0) {
            String firstWhere = wheres.get(0).replaceFirst("and ", "").replaceFirst("or ", "");
            wheres.set(0, firstWhere);
        }

        String defaultWhere = "where ";
        return defaultWhere + String.join(" ", wheres);
    }


    @Override
    public ISqlExpression setParamAll(Map<String, Object> maps) {
        if (maps != null && maps.size() > 0) {
            params.putAll(maps);
        }
        return this;
    }


    @Override
    public ISqlExpression groupBy(String group) {
        if (groupBuffer.length() == 0) {
            groupBuffer.append(String.format("group by %s", group));
        } else {
            groupBuffer.append("," + group);
        }
        return this;
    }

    @Override
    public ISqlExpression groupBys(String... groups) {
        if (groups == null || groups.length <= 0) {
            return this;
        }
        return groupBy(String.join(",", groups));
    }

    @Override
    public <T, R> ISqlExpression groupBy(SerializableFunction<T, R>... groups) {
        return groupBy(null, groups);
    }

    @Override
    public <T, R> ISqlExpression groupBy(String tblAlias, SerializableFunction<T, R>... groups) {
        if (groups == null || groups.length <= 0) {
            return this;
        }
        final String fianlTblAlias = tblAlaisFormat(tblAlias);

        List<String> list = Arrays.stream(groups).map(item -> fianlTblAlias + function2ColumnName(item)).collect(Collectors.toList());
        return groupBys(list.toArray(new String[0]));
    }

    @Override
    public ISqlExpression leftJoin(String body) {
        fromBuffer.append(" left join " + body + " ");
        return this;
    }

    @Override
    public ISqlExpression leftJoin(Class<?> tClass, String alias) {
        fromBuffer.append(" left join " + ClassAnnotationUtils.getTableName(tClass) + " " + alias + " ");
        return this;
    }

    @Override
    public ISqlExpression innerJoin(String body) {
        fromBuffer.append(" inner join " + body + " ");
        return this;
    }

    @Override
    public ISqlExpression innerJoin(Class<?> tClass, String alias) {
        fromBuffer.append(" inner join " + ClassAnnotationUtils.getTableName(tClass) + " " + alias + " ");
        return this;
    }

    @Override
    public ISqlExpression rightJoin(String body) {
        fromBuffer.append(" right join " + body + " ");
        return this;
    }

    @Override
    public ISqlExpression rightJoin(Class<?> tClass, String alias) {
        fromBuffer.append(" right join " + ClassAnnotationUtils.getTableName(tClass) + " " + alias + " ");
        return this;
    }

    @Override
    public ISqlExpression on(String body) {
        fromBuffer.append("on (" + body + ")");
        return this;
    }

    @Override
    public ISqlExpression on(String left, String right) {
        fromBuffer.append("on (" + left + "=" + right + ")");
        return this;
    }

    @Override
    public <T, R> ISqlExpression on(SerializableFunction<T, R> left, String leftAlias, SerializableFunction<T, R> right, String rightAlias) {
        return on(String.format("%s.%s", leftAlias, function2ColumnName(left)), String.format("%s.%s", rightAlias, function2ColumnName(right)));
    }

    @Override
    public ISqlExpression selectAllFrom(Class<?> tClass) {
        return select(SqlHelper.getAllColumns(tClass)).from(tClass);
    }

    @Override
    public ISqlExpression selectAllFrom(Class<?> tClass, String alias) {
        // "aa,bb,cc"  replace ,->,xx.  ="aa,xx.bb,xx.cc"
        // and add start xx.
        String allColumns = alias + "." + SqlHelper.getAllColumns(tClass).replace(",", "," + alias + ".");

        return select(allColumns).from(tClass, alias);
    }

    @Override
    public ISqlExpression select(String body) {
        if (bodyBuffer.indexOf("select ") >= 0) {
            addBody("," + body);
        } else {
            addBody("select " + body);
        }
        sqlOperateMode = SqlOperateMode.select;
        return this;
    }

    @Override
    public ISqlExpression appendSelect(String body) {
        return addBody("," + body);
    }

    @Override
    public ISqlExpression select(String... bodys) {
        if (bodys == null && bodys.length <= 0) {
            return this;
        }
        sqlOperateMode = SqlOperateMode.select;

        String body = String.join(",", bodys);
        if (bodyBuffer.indexOf("select ") >= 0) {
            addBody("," + body);
        } else {
            addBody("select " + body);
        }
        return this;
    }

    @Override
    public <T, R> ISqlExpression select(SerializableFunction<T, R>... bodys) {
        return select(null, bodys);
    }

    @Override
    public <T, R> ISqlExpression select(String tblAlias, SerializableFunction<T, R>... bodys) {
        if (bodys == null || bodys.length <= 0) {
            return this;
        }
        final String finalTblAlias = tblAlaisFormat(tblAlias);

        List<String> list = Arrays.stream(bodys).map(item -> finalTblAlias + function2ColumnName(item)).collect(Collectors.toList());
        return select(list.toArray(new String[0]));
    }

    @Override
    public <T, R> String function2ColumnName(SerializableFunction<T, R> function) {
        Field field = ReflectUtils.getField(function);
        Column columnAnno = field.getAnnotation(Column.class);
        if (columnAnno != null) {
            return columnAnno.name();
        }
        ColumnType columnTypeAnno = field.getAnnotation(ColumnType.class);
        if (columnTypeAnno != null) {
            return columnTypeAnno.column();
        }
        return field.getName();
    }

    @Override
    public ISqlExpression insert(String tableName) {
        sqlOperateMode = SqlOperateMode.insert;
        bodyBuffer.append(String.format("insert into %s", tableName));

        return this;
    }

    @Override
    public ISqlExpression insert(Class<?> tClass) {
        return insert(ClassAnnotationUtils.getTableName(tClass));
    }

    @Override
    public ISqlExpression insertColumn(String column, Object value) {
        if (insertMap == null) {
            insertMap = new HashMap<>();
        }
        String columnParam = String.format("#{%s}", column);
        insertMap.put(column, columnParam);

        setParam(column, value);

        return this;
    }

    @Override
    public <T, R> ISqlExpression insertColumn(SerializableFunction<T, R> column, Object value) {
        return insertColumn(null, column, value);
    }

    @Override
    public <T, R> ISqlExpression insertColumn(String tblAlias, SerializableFunction<T, R> column, Object value) {
        tblAlias = tblAlaisFormat(tblAlias);

        return insertColumn(tblAlias + function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression appendSelect(String... bodys) {
        if (bodys != null && bodys.length > 0) {
            String body = String.join(",", bodys);
            addBody("," + body);
        }
        return this;
    }

    @Override
    public <T, R> ISqlExpression appendSelect(SerializableFunction<T, R>... bodys) {
        return appendSelect(null, bodys);
    }

    @Override
    public <T, R> ISqlExpression appendSelect(String tblAlias, SerializableFunction<T, R>... bodys) {
        if (bodys == null || bodys.length <= 0) {
            return this;
        }
        final String finalTblAlias = tblAlaisFormat(tblAlias);

        List<String> list = Arrays.stream(bodys).map(item -> finalTblAlias + function2ColumnName(item)).collect(Collectors.toList());
        return appendSelect(list.toArray(new String[0]));
    }

    @Override
    public ISqlExpression selectCount() {
        select("count(1) as count");
        return this;
    }

    @Override
    public ISqlExpression selectCount(String column) {
        selectCount(column, "count");
        return this;
    }

    @Override
    public <T, R> ISqlExpression selectCount(SerializableFunction<T, R> column) {
        return selectCount(null, column);
    }

    @Override
    public <T, R> ISqlExpression selectCount(String tblAlias, SerializableFunction<T, R> column) {
        tblAlias = tblAlaisFormat(tblAlias);

        return selectCount(tblAlias + function2ColumnName(column));
    }

    @Override
    public ISqlExpression selectCount(String column, String alias) {
        select(String.format("count(%s) as %s", column, alias));
        return this;
    }

    @Override
    public <T, R> ISqlExpression selectCount(SerializableFunction<T, R> column, String columnAlias) {
        return selectCount(null, column, columnAlias);
    }

    @Override
    public <T, R> ISqlExpression selectCount(String tblAlias, SerializableFunction<T, R> column, String columnAlias) {
        tblAlias = tblAlaisFormat(tblAlias);

        return selectCount(tblAlias + function2ColumnName(column), columnAlias);
    }

    @Override
    public ISqlExpression update(String body) {
        addBody("update " + body);
        sqlOperateMode = SqlOperateMode.update;
        return this;
    }

    @Override
    public ISqlExpression update(Class<?> tClass) {
        addBody("update " + ClassAnnotationUtils.getTableName(tClass));
        sqlOperateMode = SqlOperateMode.update;
        return this;
    }

    @Override
    public ISqlExpression update(Class<?> tClass, String alias) {
        addBody("update " + ClassAnnotationUtils.getTableName(tClass) + " " + alias);
        sqlOperateMode = SqlOperateMode.update;
        return this;
    }

    @Override
    public ISqlExpression setColumnKey(String column, String columnKey) {
        if (setBuffer.length() == 0) {
            setBuffer.append(String.format("set %s=#{%s}", column, columnKey));
        } else {
            setBuffer.append(String.format(",%s=#{%s}", column, columnKey));
        }
        return this;
    }

    @Override
    public ISqlExpression setValue(String column, String value) {
        if (setBuffer.length() == 0) {
            setBuffer.append("set ");
            setBuffer.append(column + "=" + value);
        } else {
            setBuffer.append("," + column + "=" + value);
        }
        return this;
    }

    @Override
    public ISqlExpression setColumn(String column, String value) {
        this.setColumnKey(column, column)
                .setParam(column, value);
        return this;
    }

    @Override
    public <T, R> ISqlExpression setColumn(SerializableFunction<T, R> column, String value) {
        return setColumn(null, column, value);
    }

    @Override
    public <T, R> ISqlExpression setColumn(String tblAlias, SerializableFunction<T, R> column, String value) {
        tblAlias = tblAlaisFormat(tblAlias);

        return setColumn(tblAlias + function2ColumnName(column), value);
    }

    @Override
    public ISqlExpression setColumnNoEmpty(String column, String value) {
        if (!StringUtils.hasText(value)) {
            return this;
        }
        return setColumn(column, value);
    }

    @Override
    public <T, R> ISqlExpression setColumnNoEmpty(SerializableFunction<T, R> column, String value) {
        if (!StringUtils.hasText(value)) {
            return this;
        }
        return setColumn(column, value);
    }

    @Override
    public <T, R> ISqlExpression setColumnNoEmpty(String tblAlias, SerializableFunction<T, R> column, String value) {
        if (!StringUtils.hasText(value)) {
            return this;
        }
        return setColumn(tblAlias, column, value);
    }

    public ISqlExpression set(String... columnKeys) {
        if (columnKeys != null && columnKeys.length > 0) {
            String sets = String.join(",", columnKeys);
            if (setBuffer.length() == 0) {
                setBuffer.append("set ");
                setBuffer.append(sets);
            } else {
                setBuffer.append("," + sets);
            }
        }
        return this;
    }

    @Override
    public ISqlExpression delete() {
        sqlOperateMode = SqlOperateMode.delete;
        addBody("delete");
        return this;
    }

    @Override
    public ISqlExpression from(String body) {
        fromBuffer.append("from " + body);
        return this;
    }

    @Override
    public ISqlExpression from(Class<?> tClass) {
        fromBuffer.append("from " + ClassAnnotationUtils.getTableName(tClass));
        return this;
    }

    @Override
    public ISqlExpression from(Class<?> tClass, String alias) {
        this.fromMainTableAlias = alias;
        fromBuffer.append("from " + ClassAnnotationUtils.getTableName(tClass) + " " + alias);
        return this;
    }


    public ISqlExpression setMainTableAlias(String mainTableAlias) {
        this.mainTableAlias = mainTableAlias;
        return this;
    }

    public String getMainTableAlias() {
        // 如果设置的mainTableAlias为空，则取一次from的时候设置的mainTableAlias
        if (StringUtils.hasText(mainTableAlias)) {
            return mainTableAlias + ".";
        }
        if (StringUtils.hasText(fromMainTableAlias)) {
            return fromMainTableAlias + ".";
        }
        return "";
    }

    @Override
    public ISqlExpression addOrderInfoList(List<OrderInfo> orderInfos) {
        if (orderInfos != null) {
            for (OrderInfo orderInfo : orderInfos) {
                this.addOrder(orderInfo.getProperty(), orderInfo.getOrderBy());
            }
        }
        return this;
    }

    @Override
    public ISqlExpression andWhereIn(String column, ISqlExpression sqlExpression) {
        return andWhereIn("in", column, sqlExpression);
    }

    private ISqlExpression andWhereIn(String inOrNotIn, String column, ISqlExpression sqlExpression) {
        StringBuilder builder = new StringBuilder();
        builder.append(column + " " + inOrNotIn + " (");
        builder.append(sqlExpression.toSql());
        builder.append(")");

        this.setParamAll(sqlExpression.getParams());

        andWhere(builder.toString());
        return this;
    }

    @Override
    public ISqlExpression andWhereIn(String column, List<Object> values) {
        return andWhereIn("in", column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereIn(SerializableFunction<T, R> column, ISqlExpression sqlExpression) {
        return andWhereIn(null, column, sqlExpression);
    }

    @Override
    public <T, R> ISqlExpression andWhereIn(String tblAlias, SerializableFunction<T, R> column, ISqlExpression sqlExpression) {
        tblAlias = tblAlaisFormat(tblAlias);
        return andWhereIn(tblAlias + function2ColumnName(column), sqlExpression);
    }

    @Override
    public <T, R> ISqlExpression andWhereIn(SerializableFunction<T, R> column, List<Object> values) {
        return andWhereIn(null, column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereIn(String tblAlias, SerializableFunction<T, R> column, List<Object> values) {
        tblAlias = tblAlaisFormat(tblAlias);
        return andWhereIn(tblAlias + function2ColumnName(column), values);
    }

    private ISqlExpression andWhereIn(String inOrNotIn, String column, List<Object> values) {
        if (CollectionUtils.isEmpty(values)) {
            return this;
        }
        column = resolveColumnMainTable(column);

        StringBuffer builder = new StringBuffer();
        builder.append(column + " " + inOrNotIn + " (");
        String columnName = "";
        for (int i = 0; i < values.size(); i++) {
            columnName = resolveColumn(column) + i;
            if (i == values.size() - 1) {
                builder.append("#{" + columnName + "}");
            } else {
                builder.append("#{" + columnName + "},");
            }
            this.setParam(columnName, values.get(i));
        }
        builder.append(")");

        andWhere(builder.toString());

        return this;
    }

    @Override
    public ISqlExpression andWhereInString(String column, List<String> values) {
        return andWhereInString("in", column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereInString(SerializableFunction<T, R> column, List<String> values) {
        return andWhereInString(null, column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereInString(String tblAlias, SerializableFunction<T, R> column, List<String> values) {
        tblAlias = tblAlaisFormat(tblAlias);

        return andWhereInString(tblAlias + function2ColumnName(column), values);
    }

    private ISqlExpression andWhereInString(String inOrNotIn, String column, List<String> values) {
        if (values == null || values.size() <= 0) {
            return this;
        }
        column = resolveColumnMainTable(column);
        StringBuffer builder = new StringBuffer();
        builder.append(column + " " + inOrNotIn + " (");
        String columnName = "";
        for (int i = 0; i < values.size(); i++) {

            columnName = resolveColumn(column) + i;
            if (i == values.size() - 1) {
                builder.append("#{" + columnName + "}");
            } else {
                builder.append("#{" + columnName + "},");
            }
            this.setParam(columnName, values.get(i));
        }
        builder.append(")");

        andWhere(builder.toString());

        return this;
    }

    @Override
    public ISqlExpression andWhereInString(String column, String... values) {
        this.andWhereInString(column, Arrays.asList(values));
        return this;
    }

    @Override
    public <T, R> ISqlExpression andWhereInString(SerializableFunction<T, R> column, String... values) {
        return andWhereInString(null, column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereInString(String tblAlias, SerializableFunction<T, R> column, String... values) {
        tblAlias = tblAlaisFormat(tblAlias);
        return andWhereInString(tblAlias + function2ColumnName(column), values);
    }

    @Override
    public ISqlExpression andWhereInString(List<String> values, String join, String... columns) {
        return andWhereInString("in", values, join, columns);
    }

    @Override
    public <T, R> ISqlExpression andWhereInString(List<String> values, String join, SerializableFunction<T, R>... columns) {
        return andWhereInString(values, join, null, columns);
    }

    @Override
    public <T, R> ISqlExpression andWhereInString(List<String> values, String join, String tblAlias, SerializableFunction<T, R>... columns) {
        final String finalTblAlias = tblAlaisFormat(tblAlias);

        List<String> list = Arrays.stream(columns).map(item -> finalTblAlias + function2ColumnName(item)).collect(Collectors.toList());

        return andWhereInString(values, join, list.toArray(new String[0]));
    }

    private ISqlExpression andWhereInString(String inOrNotIn, List<String> values, String join, String... columns) {
        if (values == null || values.size() <= 0) {
            return this;
        }
        if (columns == null || columns.length <= 0) {
            return this;
        }
        StringBuffer sbWheres = new StringBuffer();
        String inWhereColumn = "";
        Map<String, Object> inWhereMap = new HashMap<String, Object>();
        for (String column : columns) {
            column = resolveColumnMainTable(column);
            if (sbWheres.length() > 0) {
                sbWheres.append(" " + join + " ");
            }
            if (StringUtils.isEmpty(inWhereColumn)) {
                inWhereColumn = resolveColumn(column);
            }
            sbWheres.append(column + " " + inOrNotIn + " (");
            for (int i = 0; i < values.size(); i++) {
                if (i == values.size() - 1) {
                    sbWheres.append("#{" + inWhereColumn + i + "}");
                } else {
                    sbWheres.append("#{" + inWhereColumn + i + "},");
                }

                if (inWhereMap.isEmpty()) {
                    this.setParam(resolveColumn(column) + i, values.get(i));
                }
            }

            sbWheres.append(")");
        }
        this.setParamAll(inWhereMap);

        andWhere(sbWheres.toString());

        return this;
    }

    @Override
    public ISqlExpression andWhereNotIn(String column, ISqlExpression sqlExpression) {
        return andWhereIn("not in", column, sqlExpression);
    }

    @Override
    public ISqlExpression andWhereNotIn(String column, List<Object> values) {
        return andWhereIn("not in", column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotIn(SerializableFunction<T, R> column, ISqlExpression sqlExpression) {
        return andWhereNotIn(null, column, sqlExpression);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotIn(String tblAlias, SerializableFunction<T, R> column, ISqlExpression sqlExpression) {
        tblAlias = tblAlaisFormat(tblAlias);

        return andWhereNotIn(tblAlias + function2ColumnName(column), sqlExpression);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotIn(SerializableFunction<T, R> column, List<Object> values) {
        return andWhereNotIn(null, column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotIn(String tblAlias, SerializableFunction<T, R> column, List<Object> values) {
        tblAlias = tblAlaisFormat(tblAlias);

        return andWhereNotIn(tblAlias + function2ColumnName(column), values);
    }

    @Override
    public ISqlExpression andWhereNotInString(String column, List<String> values) {
        return andWhereInString("not in", column, values);
    }

    @Override
    public ISqlExpression andWhereNotInString(String column, String... values) {
        return andWhereInString("not in", column, Arrays.asList(values));
    }

    @Override
    public ISqlExpression andWhereNotInString(List<String> values, String join, String... columns) {
        return andWhereInString("not in", values, join, columns);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotInString(SerializableFunction<T, R> column, List<String> values) {
        return andWhereNotInString(null, column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotInString(String tblAlias, SerializableFunction<T, R> column, List<String> values) {
        tblAlias = tblAlaisFormat(tblAlias);

        return andWhereNotInString(tblAlias + function2ColumnName(column), values);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotInString(SerializableFunction<T, R> column, String... values) {
        return andWhereNotInString(null, column, values);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotInString(String tblAlias, SerializableFunction<T, R> column, String... values) {
        tblAlias = tblAlaisFormat(tblAlias);

        return andWhereNotInString(tblAlias + function2ColumnName(column), values);
    }

    @Override
    public <T, R> ISqlExpression andWhereNotInString(List<String> values, String join, SerializableFunction<T, R>... columns) {
        String mainTableAlias = getMainTableAlias();
        List<String> list = Arrays.stream(columns).map(item -> mainTableAlias + function2ColumnName(item)).collect(Collectors.toList());

        return andWhereNotInString(values, join, list.toArray(new String[0]));
    }

    @Override
    public <T, R> ISqlExpression andWhereNotInString(List<String> values, String join, String tblAlias, SerializableFunction<T, R>... columns) {
        final String finalTblAlias = tblAlaisFormat(tblAlias);

        List<String> list = Arrays.stream(columns).map(item -> finalTblAlias + function2ColumnName(item)).collect(Collectors.toList());

        return andWhereNotInString(values, join, list.toArray(new String[0]));
    }

    private String tblAlaisFormat(String tblAlias) {
        if (!StringUtils.hasText(tblAlias)) {
            tblAlias = getMainTableAlias();
        } else {
            if (tblAlias.indexOf(".") < 0) {
                tblAlias += ".";
            }
        }
        return tblAlias;
    }

    private String resolveColumn(String column) {

        return column.replaceAll("\\.", "_");
    }

    // 如果有maintable的别名设置，并且列没有指定表别名，则默认为主表别名
    private String resolveColumnMainTable(String column) {
        String mainTableAlias = getMainTableAlias();
        if (StringUtils.hasText(mainTableAlias) && column.indexOf(".") < 0) {
            column = mainTableAlias + column;
        }
        return column;
    }
}
