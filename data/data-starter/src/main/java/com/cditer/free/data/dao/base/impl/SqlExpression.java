package com.cditer.free.data.dao.base.impl;

import com.cditer.free.core.exception.BizException;
import com.cditer.free.core.message.data.OrderByEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.util.ReflectUtils;
import com.cditer.free.core.util.SerializableFunction;
import com.cditer.free.core.util.StringHelper;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.message.OrderInfo;
import com.cditer.free.data.message.SqlOperateMode;
import com.cditer.free.data.utils.ClassAnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import javax.persistence.Column;
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

    private StringBuffer orderBuffer = new StringBuffer();

    private StringBuffer groupBuffer = new StringBuffer();

    private StringBuffer setBuffer = new StringBuffer();

    private StringBuffer fromBuffer = new StringBuffer();

    private StringBuffer limitBuffer = new StringBuffer();

    private StringBuffer callFunParam = new StringBuffer();

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

        this.andWhere(column + " like #{" + paramName + "}")
                .setParam(paramName, "%" + value + "%");
        return this;
    }

    @Override
    public ISqlExpression andRightLike(String column, String value) {
        column = resolveColumnMainTable(column);
        String paramName = resolveColumn(column);

        this.andWhere(column + " like #{" + paramName + "}")
                .setParam(paramName, value + "%");
        return this;
    }

    @Override
    public ISqlExpression andLikeNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andLike(column, value);
        }
        return this;
    }

    @Override
    public ISqlExpression andRightLikeNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andRightLike(column, value);
        }
        return this;
    }

    @Override
    public ISqlExpression andEqNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andEq(column, value);
        }
        return this;
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
    public ISqlExpression andNotLike(String column, String value) {
        column = resolveColumnMainTable(column);
        String paramName = resolveColumn(column);

        this.andWhere(column + " not like #{" + paramName + "}")
                .setParam(paramName, "%" + value + "%");
        return this;
    }

    @Override
    public ISqlExpression andNotLikeNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andNotLike(column, value);
        }
        return this;
    }

    @Override
    public ISqlExpression andNotEqNoEmpty(String column, String value) {
        if (!StringUtils.isEmpty(value)) {
            return andNotEq(column, value);
        }
        return this;
    }

    @Override
    public ISqlExpression andEq(String column, int value) {
        return andEq(column, String.valueOf(value));
    }

    @Override
    public ISqlExpression andEqNoEmpty(String column, int value) {
        if (value > -1) {
            return andEq(column, value);
        }
        return this;
    }

    @Override
    public ISqlExpression andNotEq(String column, int value) {
        return andNotEq(column, String.valueOf(value));
    }

    @Override
    public ISqlExpression andNotEqNoEmpty(String column, int value) {
        if (value > -1) {
            return andNotEq(column, value);
        }
        return this;
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
        for (String column : columns) {
            if (orderBuffer.length() == 0) {
                orderBuffer.append(" order by ");
                orderBuffer.append(" " + column + " ");
            } else {
                orderBuffer.append(" ," + column + " ");
            }
            orderBuffer.append(" " + order.name() + " ");
        }
        return this;
    }

    @Override
    public ISqlExpression addOrder(String column, OrderEnum order) {
        if (orderBuffer.length() == 0) {
            orderBuffer.append(" order by ");
            orderBuffer.append(" " + column + " ");
        } else {
            orderBuffer.append(" ," + column + " ");
        }
        orderBuffer.append(" " + order.name() + " ");

        return this;
    }

    @Override
    public ISqlExpression addBody(String body) {
        bodyBuffer.append(body);
        return this;
    }

    @Override
    public ISqlExpression addBody(String body, Class<?> tClass) {
        bodyBuffer.append(" " + body + " " + ClassAnnotationUtils.getTableName(tClass));
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
        limitBuffer.append(" limit " + pagerModel.getCurrentSize() + " , " + pagerModel.getPageSize());
        return this;
    }

    @Override
    public ISqlExpression limit(int pageIndex, int pageSize) {
        return limit(new PagerModel(pageSize, pageIndex));
    }

    @Override
    public ISqlExpression selectDistinct(String body) {
        select(" distinct " + body);
        return this;
    }

    @Override
    public ISqlExpression selectDistinct(String... bodys) {
        if (bodys != null && bodys.length > 0) {
            String body = String.join(",", bodys);
            addBody("select distinct " + body);
        }
        sqlOperateMode = SqlOperateMode.select;
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
        callFunParam.append("#{" + funName + "}");
        setParam(funName, funValue);
        return this;
    }

    @Override
    public String toSql() {
        List<String> result = new ArrayList<>();
        result.add(bodyBuffer.toString());
        if (SqlOperateMode.select.equals(sqlOperateMode)) {
            if(StringUtils.hasText(fromBuffer.toString())) {
                result.add(fromBuffer.toString());
            }

            String whereSql = getWhereString();
            if(StringUtils.hasText(whereSql)) {
                result.add(whereSql);
            }
            if(StringUtils.hasText(groupBuffer.toString())) {
                result.add(groupBuffer.toString());
            }
            if(StringUtils.hasText(orderBuffer.toString())) {
                result.add(orderBuffer.toString());
            }
            if(StringUtils.hasText(limitBuffer.toString())) {
                result.add(limitBuffer.toString());
            }
        } else if (SqlOperateMode.update.equals(sqlOperateMode)) {
            if(StringUtils.hasText(fromBuffer.toString())) {
                result.add(fromBuffer.toString());
            }
            if(StringUtils.hasText(setBuffer.toString())) {
                result.add(setBuffer.toString());
            }
            String whereSql = getWhereString();
            if(StringUtils.hasText(whereSql)) {
                result.add(whereSql);
            }
        } else if (SqlOperateMode.delete.equals(sqlOperateMode)) {
            if(StringUtils.hasText(fromBuffer.toString())) {
                result.add(fromBuffer.toString());
            }

            String whereSql = getWhereString();
            if(StringUtils.hasText(whereSql)) {
                result.add(whereSql);
            }
        } else if (SqlOperateMode.callFun.equals(sqlOperateMode)) {
            result.add(String.format("(%s)", String.join(",", callFunParam)));
        }

        return String.join(" ", result);
    }

    private String getWhereString() {
        if (wheres.size() == 0) {
            return "";
        }
        String defaultWhere = "where ";
        String firstWhere = wheres.get(0).replace("and ", "").replace("or ", "");
        wheres.set(0, firstWhere);

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
    public ISqlExpression addGroup(String group) {
        if (groupBuffer.length() == 0) {
            groupBuffer.append(" group by ");
            groupBuffer.append(" " + group + " ");
        } else {
            groupBuffer.append(" ," + group + " ");
        }
        return this;
    }

    @Override
    public ISqlExpression addGroups(String... groups) {
        for (String group : groups) {
            if (groupBuffer.length() == 0) {
                groupBuffer.append(" group by ");
                groupBuffer.append(" " + group + " ");
            } else {
                groupBuffer.append(" ," + group + " ");
            }
        }
        return this;
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
        fromBuffer.append(" on (" + body + ") ");
        return this;
    }

    @Override
    public ISqlExpression on(String left, String right) {
        fromBuffer.append(" on (" + left + "=" + right + ") ");
        return this;
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
        addBody("select " + body);
        sqlOperateMode = SqlOperateMode.select;
        return this;
    }

    @Override
    public ISqlExpression appendSelect(String body) {
        return addBody("," + body);
    }

    @Override
    public ISqlExpression select(String... bodys) {
        if (bodys != null && bodys.length > 0) {
            String body = String.join(",", bodys);
            addBody("select " + body);
        }
        sqlOperateMode = SqlOperateMode.select;
        return this;
    }

    @Override
    public <T, R> ISqlExpression select(SerializableFunction<T, R>... bodys) {
        if(bodys==null||bodys.length<=0){
            return this;
        }
        List<String> list = Arrays.stream(bodys).map(item -> function2ColumnName(item)).collect(Collectors.toList());
        return select(list.toArray(new String[0]));
    }

    private <T, R> String function2ColumnName(SerializableFunction<T, R> function){
        Column columnAnno = ReflectUtils.getFieldByAnno(function, Column.class);

        if(columnAnno!=null){
            return columnAnno.name();
        }
        String fieldName = ReflectUtils.getFieldName(function);
        throw new BizException(String.format("%s @Cloumn is not find", fieldName));
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
    public ISqlExpression selectCount() {
        select("count(1) as c ");
        return this;
    }

    @Override
    public ISqlExpression selectCount(String column) {
        select("count(" + column + ") as c ");
        return this;
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
    public ISqlExpression set(String column, String columnKey) {
        if (setBuffer.length() == 0) {
            setBuffer.append(" set ");
            setBuffer.append(column + "=#{" + columnKey + "}");
        } else {
            setBuffer.append("," + column + "=#{" + columnKey + "}");
        }
        return this;
    }

    @Override
    public ISqlExpression setValue(String column, String value) {
        if (setBuffer.length() == 0) {
            setBuffer.append(" set ");
            setBuffer.append(column + "=" + value);
        } else {
            setBuffer.append("," + column + "=" + value);
        }
        return this;
    }

    @Override
    public ISqlExpression setColumn(String column, String value) {
        this.set(column, column)
                .setParam(column, value);
        return this;
    }

    public ISqlExpression set(String... columnKeys) {
        if (columnKeys != null && columnKeys.length > 0) {
            String sets = String.join(",", columnKeys);
            if (setBuffer.length() == 0) {
                setBuffer.append(" set ");
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
        if (StringUtils.isEmpty(mainTableAlias)) {
            mainTableAlias = this.fromMainTableAlias;
        }
        if (!StringUtils.isEmpty(mainTableAlias)) {
            return mainTableAlias + ".";
        }
        return mainTableAlias;
    }

    @Override
    public ISqlExpression addOrderInfoList(List<OrderInfo> orderInfos) {
        if (orderInfos != null) {
            for (OrderInfo orderInfo : orderInfos) {
                OrderEnum or = OrderEnum.asc;
                if (OrderByEnum.DESC.equals(orderInfo.getOrderBy().toUpperCase())) {
                    or = OrderEnum.desc;
                }
                this.addOrder(orderInfo.getProperty(), or);
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

    private ISqlExpression andWhereIn(String inOrNotIn, String column, List<Object> values) {
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
    public ISqlExpression andWhereInString(String column, List<String> values) {
        return andWhereInString("in", column, values);
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
    public ISqlExpression andWhereInString(List<String> values, String join, String... columns) {
        return andWhereInString("in", values, join, columns);
    }

    private ISqlExpression andWhereInString(String inOrNotIn, List<String> values, String join, String... columns) {
        if (values == null || values.size() <= 0) {
            return this;
        }
        if (columns == null || columns.length <= 0) {
            return this;
        }
        StringBuffer sbWheres = new StringBuffer();
        String inWhereId = "";
        Map<String, Object> inWhereMap = new HashMap<String, Object>();
        for (String column : columns) {
            column = resolveColumnMainTable(column);
            if (sbWheres.length() > 0) {
                sbWheres.append(" " + join + " ");
            }
            if (StringUtils.isEmpty(inWhereId)) {

                inWhereId = resolveColumn(column);
            }
            sbWheres.append(column + " " + inOrNotIn + " (");
            for (int i = 0; i < values.size(); i++) {
                if (i == values.size() - 1) {
                    sbWheres.append("#{" + inWhereId + i + "}");
                } else {
                    sbWheres.append("#{" + inWhereId + i + "},");
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
        return andWhereIn(column, values);
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

    private String resolveColumn(String column) {

        return column.replaceAll("\\.", "_");
    }

    // 如果有maintable的别名设置，并且列没有指定表别名，则默认为主表别名
    private String resolveColumnMainTable(String column) {
        if (!StringUtils.isEmpty(mainTableAlias) && column.indexOf(".") < 0) {
            column = mainTableAlias + "." + column;
        }
        return column;
    }
}
