package com.cditer.free.data.dao.base;

import com.cditer.free.core.message.data.PagerModel;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-27 18:12
 * @comment
 */

public interface ISqlExecutor {
    int update(ISqlExpression sqlExpression);

    int delete(ISqlExpression sqlExpression);

    int insert(ISqlExpression sqlExpression);

    String queryScalar(ISqlExpression sqlExpression);

    int queryScalarInt(ISqlExpression sqlExpression);

    List<Map<String, Object>> selectList(ISqlExpression sqlExpression);


    int update(String sql);

    int update(String sql,Object params);

    int delete(String sql);

    int delete(String sql,Object params);

    int insert(String sql);

    int insert(String sql,Object params);

    List<Map<String, Object>> selectList(String sql);

    <T> List<T> selectList(String sql, Class<T> resultType);

    List<Map<String, Object>> selectList(String sql, Object value);

    List<Map<String, Object>> selectListEx(String sql, Object value, RowBounds rowBounds);

    <T> List<T> selectList(String sql, Object value, Class<T> resultType);

    Map<String, Object> selectOne(String sql);

    <T> T selectOne(String sql, Class<T> resultType);

    Map<String, Object> selectOne(String sql, Object value);

    <T> T selectOne(String sql, Object value, Class<T> resultType);

    int queryCount(String sql, Object value);

    <T> List<T> selectList(String sql,RowBounds rowBounds, Class<T> resultType);

    <T> List<T> selectList(String sql, Object value,RowBounds rowBounds,Class<T> resultType);

    <T> T queryModel(ISqlExpression sqlExpression,Class<T> resultType);

    <T> List<T> queryList(ISqlExpression sqlExpression,Class<T> resultType);

    List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression, PagerModel pagerModel);

    <T> List<T> queryList(ISqlExpression sqlExpression,PagerModel pagerModel,Class<T> resultType);

    int queryCount(ISqlExpression sqlExpression);

    double queryScalarDouble(ISqlExpression sqlExpression);
}
