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

    String selectScalar(ISqlExpression sqlExpression);

    int selectScalarInt(ISqlExpression sqlExpression);

    List<Map<String, Object>> selectListEx(ISqlExpression sqlExpression);


    int update(String sql);

    int update(String sql,Object params);

    int delete(String sql);

    int delete(String sql,Object params);

    int insert(String sql);

    int insert(String sql,Object params);

    List<Map<String, Object>> selectListEx(String sql);

    <T> List<T> selectList(String sql, Class<T> resultType);

    <T> List<T> selectList(ISqlExpression sql, Class<T> resultType);

    <T> List<T> selectList(ISqlExpression sql,RowBounds rowBounds, Class<T> resultType);

    List<Map<String, Object>> selectListEx(String sql, Object value);

    List<Map<String, Object>> selectListEx(String sql, Object value, RowBounds rowBounds);

    <T> List<T> selectList(String sql, Object value, Class<T> resultType);

    Map<String, Object> selectOneEx(String sql);

    <T> T selectOne(String sql, Class<T> resultType);

    Map<String, Object> selectOneEx(String sql, Object value);

    Map<String, Object> selectOneEx(ISqlExpression sqlExpression);

    <T> T selectOne(ISqlExpression sqlExpression, Class<T> resultType);

    <T> T selectOne(String sql, Object value, Class<T> resultType);

    int selectCount(String sql, Object value);

    <T> List<T> selectList(String sql,RowBounds rowBounds, Class<T> resultType);

    <T> List<T> selectList(String sql, Object value,RowBounds rowBounds,Class<T> resultType);

    <T> T selectModel(ISqlExpression sqlExpression,Class<T> resultType);

    List<Map<String, Object>> selectListEx(ISqlExpression sqlExpression, PagerModel pagerModel);

    <T> List<T> selectList(ISqlExpression sqlExpression,PagerModel pagerModel,Class<T> resultType);

    int selectCount(ISqlExpression sqlExpression);

    double selectScalarDouble(ISqlExpression sqlExpression);
}
