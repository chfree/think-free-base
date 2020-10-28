package com.tennetcn.free.data.dao.base.impl;

import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.data.dao.base.ISqlExecutor;
import com.tennetcn.free.data.dao.base.ISqlExpression;
import com.tennetcn.free.data.dao.base.mapper.SqlMapper;
import com.tennetcn.free.data.message.DaoBaseRuntimeException;
import com.tennetcn.free.data.utils.Pager2RowBounds;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-27 18:41
 * @comment
 */

@Component
public class SqlExecutorImpl implements ISqlExecutor {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public int update(ISqlExpression sqlExpression) {
        return update(sqlExpression.toSql(), sqlExpression.getParams());
    }

    @Override
    public int delete(ISqlExpression sqlExpression) {
        return delete(sqlExpression.toSql(), sqlExpression.getParams());
    }

    @Override
    public int insert(ISqlExpression sqlExpression) {
        return insert(sqlExpression.toSql(), sqlExpression.getParams());
    }

    @Override
    public String queryScalar(ISqlExpression sqlExpression) {
        List<Map<String, Object>> list;
        try {
            list = selectList(sqlExpression);
            if (list == null || list.size() == 0) {
                return null;
            }
            Map<String, Object> map = list.get(0);
            if (map == null || map.keySet() == null || map.keySet().toArray() == null || map.keySet().toArray().length <= 0 || map.get(map.keySet().toArray()[0]) == null) {
                return null;
            }
            return map.get(map.keySet().toArray()[0]).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        }
    }

    @Override
    public int queryScalarInt(ISqlExpression sqlExpression) {
        String scalar = queryScalar(sqlExpression);
        if(StringUtils.isEmpty(scalar)){
            return 0;
        }
        return Integer.parseInt(scalar);
    }

    @Override
    public List<Map<String, Object>> selectList(ISqlExpression sqlExpression) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectList(sqlExpression.toSql(), sqlExpression.getParams());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public int update(String sql) {
        return update(sql, null);
    }

    @Override
    public int update(String sql, Object params) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            if(params==null){
                return mapper.update(sql);
            }
            return mapper.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public int delete(String sql) {
        return delete(sql, null);
    }

    @Override
    public int delete(String sql, Object params) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            if(params==null){
                return mapper.delete(sql);
            }
            return mapper.delete(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public int insert(String sql) {
        return insert(sql, null);
    }

    @Override
    public int insert(String sql, Object params) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            if(params==null){
                return mapper.insert(sql);
            }
            return mapper.insert(sql,params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Map<String, Object>> selectList(String sql) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectList(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public <T> List<T> selectList(String sql, Class<T> resultType) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectList(sql, resultType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Map<String, Object>> selectList(String sql, Object value) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectList(sql, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Map<String, Object>> selectListEx(String sql, Object value, RowBounds rowBounds) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectListEx(sql, value, rowBounds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public <T> List<T> selectList(String sql, Object value, Class<T> resultType) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectList(sql, value, resultType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Map<String, Object> selectOne(String sql) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectOne(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public <T> T selectOne(String sql, Class<T> resultType) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectOne(sql, resultType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Map<String, Object> selectOne(String sql, Object value) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectOne(sql, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public <T> T selectOne(String sql, Object value, Class<T> resultType) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectOne(sql, value, resultType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public int queryCount(String sql, Object value) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.queryCount(sql, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public <T> List<T> selectList(String sql, RowBounds rowBounds, Class<T> resultType) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectList(sql, rowBounds, resultType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public <T> List<T> selectList(String sql, Object value, RowBounds rowBounds, Class<T> resultType) {
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            SqlMapper mapper = new SqlMapper(session);
            return mapper.selectList(sql, value, rowBounds, resultType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public <T> T queryModel(ISqlExpression sqlExpression, Class<T> resultType) {
        return selectOne(sqlExpression.toSql(), sqlExpression.getParams(), resultType);
    }

    @Override
    public <T> List<T> queryList(ISqlExpression sqlExpression, Class<T> resultType) {
        return selectList(sqlExpression.toSql(), sqlExpression.getParams(), resultType);
    }

    @Override
    public List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression, PagerModel pagerModel) {
        return selectListEx(sqlExpression.toSql(), sqlExpression.getParams(), Pager2RowBounds.getRowBounds(pagerModel));
    }

    @Override
    public <T> List<T> queryList(ISqlExpression sqlExpression, PagerModel pagerModel, Class<T> resultType) {
        return selectList(sqlExpression.toSql(), sqlExpression.getParams(), Pager2RowBounds.getRowBounds(pagerModel), resultType);
    }

    @Override
    public int queryCount(ISqlExpression sqlExpression) {
        return queryCount(sqlExpression.toSql(), sqlExpression.getParams());
    }

    @Override
    public double queryScalarDouble(ISqlExpression sqlExpression) {
        String scalar = queryScalar(sqlExpression);
        if(StringUtils.isEmpty(scalar)){
            return Double.NaN;
        }
        return Double.parseDouble(scalar);
    }
}
