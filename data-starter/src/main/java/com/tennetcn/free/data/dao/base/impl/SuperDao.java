package com.tennetcn.free.data.dao.base.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.tennetcn.free.data.enums.YesOrNo;
import com.tennetcn.free.data.enums.YesOrNoInteger;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tennetcn.free.data.dao.base.ISqlExpression;
import com.tennetcn.free.data.dao.base.IMapper;
import com.tennetcn.free.data.dao.base.ISuperDao;
import com.tennetcn.free.data.dao.base.mapper.SqlMapper;
import com.tennetcn.free.data.enums.ModelStatus;
import com.tennetcn.free.data.message.DaoBaseException;
import com.tennetcn.free.data.message.DaoBaseRuntimeException;
import com.tennetcn.free.data.message.ModelBase;
import com.tennetcn.free.data.message.OrderByEnum;
import com.tennetcn.free.data.message.OrderInfo;
import com.tennetcn.free.data.message.PagerModel;
import com.tennetcn.free.data.message.SearchModel;
import com.tennetcn.free.data.utils.ClassAnnotationUtils;
import com.tennetcn.free.data.utils.SqlExpressionFactory;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.entity.Example.OrderBy;

/**
 * @author chenghuan
 * @email 79763939@qq.com
 * @comment
 */

public abstract class SuperDao<E extends ModelBase> extends DbContext<E> implements ISuperDao<E> {

	@Autowired
	private IMapper<E> mapper;

	public IMapper<E> getMapper() {
		return mapper;
	}

	@Override
	public String getModelName() {
		return ClassAnnotationUtils.getModelName(entityClass);
	}

	@Override
	public String getTableName() {
		return ClassAnnotationUtils.getTableName(entityClass);
	}

	@Override
	public <T extends ModelBase> String getTableName(Class<T> classType) {
		return ClassAnnotationUtils.getTableName(classType);
	}

	@Override
	public String getPrimaryKey() {
		return ClassAnnotationUtils.getPrimaryKey(entityClass);
	}

	@Override
	public List<OrderInfo> getOrderInfoList() {
		return ClassAnnotationUtils.getOrderInfoList(entityClass);
	}

	@Override
	public List<E> queryList() throws DaoBaseException {
		return getMapper().selectAll();
	}

	@Override
	public List<E> queryListByIds(String ids) throws DaoBaseException {
		if (StringUtils.isEmpty(ids)) {
			return null;
		}
		return queryListByIds(Arrays.asList(ids.split(",")));
	}

	@Override
	public List<E> queryListByIds(List<String> ids) throws DaoBaseException {
		ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
		sqlExpression.addBody("select * from " + getTableName());

		sqlExpression.andWhereInString(getPrimaryKey(), ids);

		return queryList(sqlExpression);
	}

	@Override
	public List<E> queryListByIds(String... ids) throws DaoBaseException {
		if (ids == null || ids.length <= 0) {
			return null;
		}
		return queryListByIds(Arrays.asList(ids));
	}

	@Override
	public List<E> queryList(SearchModel search) throws DaoBaseException {
		return queryList(search, null);
	}

	@Override
	public List<E> queryList(PagerModel pagerModel) throws DaoBaseException {
		return queryList(true, pagerModel);
	}

	@Override
	public List<E> queryList(boolean deleteMark, PagerModel pagerModel) throws DaoBaseException {
		Example example = new Example(entityClass);
		if (deleteMark) {
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("deleteMark", YesOrNo.NO);
		}

		setOrderBy(example, pagerModel);

		return getMapper().selectByExampleAndRowBounds(example, pagerModel.getRowBounds());
	}

	@Override
	public List<E> queryList(SearchModel search, PagerModel pagerModel) throws DaoBaseException {
		ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
		sqlExpression.addBody("select * from " + getTableName());

		sqlExpression.addOrderInfoList(getOrderInfoList());

		if (pagerModel == null) {
			return queryList(sqlExpression, search);
		}
		return queryList(sqlExpression, search, pagerModel);
	}

	@Override
	public List<E> queryList(boolean deleteMark) throws DaoBaseException {
		Example example = new Example(entityClass);
		if (deleteMark) {
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("deleteMark", YesOrNo.NO);
		}

		setOrderBy(example);

		return getMapper().selectByExample(example);
	}

	@Override
	public List<E> queryList(int deleteMark, PagerModel pagerModel) throws DaoBaseException {
		if (YesOrNoInteger.NO == deleteMark) {
			return this.queryList(true, pagerModel);
		} else {
			return this.queryList(false, pagerModel);
		}
	}

	@Override
	public List<E> queryList(int deleteMark) throws DaoBaseException {
		if (YesOrNoInteger.NO == deleteMark) {
			return this.queryList(true);
		} else {
			return this.queryList(false);
		}
	}

	private void setOrderBy(Object exampleObj) {
		List<OrderInfo> orderInfoList = getOrderInfoList();
		if (orderInfoList == null) {
			return;
		}

		Example example = (Example) exampleObj;
		OrderBy orderBy = null;

		for (OrderInfo orderInfo : orderInfoList) {
			if (orderBy == null) {
				orderBy = example.orderBy(orderInfo.getProperty());
			} else {
				orderBy.orderBy(orderInfo.getProperty());
			}

			if (OrderByEnum.ASC.equals(orderInfo.getOrderBy())) {
				orderBy.asc();
			} else {
				orderBy.desc();
			}
		}
	}

	private void setOrderBy(Object exampleObj, PagerModel pager) {
		List<OrderInfo> orderInfoList = pager.getOrderInfoList();
		if (orderInfoList == null) {
			// 有pager的orderInfo的时候就不取默认的注解orderby
			setOrderBy(exampleObj);
			return;
		}

		Example example = (Example) exampleObj;
		OrderBy orderBy = null;

		for (OrderInfo orderInfo : orderInfoList) {
			if (orderBy == null) {
				orderBy = example.orderBy(orderInfo.getProperty());
			} else {
				orderBy.orderBy(orderInfo.getProperty());
			}

			if (OrderByEnum.ASC.equals(orderInfo.getOrderBy().toUpperCase())) {
				orderBy.asc();
			} else {
				orderBy.desc();
			}
		}
	}

	@Override
	public List<E> queryList(E e) throws DaoBaseException {
		return getMapper().select(e);
	}

	@Override
	public List<E> queryListByExample(Object example) throws DaoBaseException {
		setOrderBy(example);
		return getMapper().selectByExample(example);
	}

	@Override
	public List<E> queryList(Object example, PagerModel pagerModel) throws DaoBaseException {
		setOrderBy(example, pagerModel);
		return getMapper().selectByExampleAndRowBounds(example, pagerModel.getRowBounds());
	}

	@Override
	public List<E> queryListByExampleAndRowBounds(Object example, RowBounds rowBounds) throws DaoBaseException {
		setOrderBy(example);
		return getMapper().selectByExampleAndRowBounds(example, rowBounds);
	}

	@Override
	public E queryModel(String key) throws DaoBaseException {
		return getMapper().selectByPrimaryKey(key);
	}

	@Override
	public E queryModel(E e) throws DaoBaseException {
		return getMapper().selectOne(e);
	}

	@Override
	public E queryModelByExample(Object example) throws DaoBaseException {
		List<E> list = getMapper().selectByExample(example);
		if (list == null) {
			return null;
		}
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public int queryCount(E e) throws DaoBaseException {
		return getMapper().selectCount(e);
	}

	@Override
	public int queryCountByExample(Object example) throws DaoBaseException {
		return getMapper().selectCountByExample(example);
	}

	@Override
	public int queryCount() throws DaoBaseException {
		return queryCount(false);
	}

	@Override
	public int queryCount(boolean deleteMark) throws DaoBaseException {
		ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
		sqlExpression.addBody("select count(1) from " + getTableName());
		if (deleteMark) {
			sqlExpression.andWhere("deleteMark=#{deleteMark}").setParam("deleteMark", YesOrNo.NO);
		}
		return queryCount(sqlExpression);
	}

	@Override
	public int queryCount(SearchModel search) throws DaoBaseException {
		ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
		sqlExpression.addBody("select count(1) from " + getTableName());

		return queryCount(sqlExpression, search);
	}

	@Override
	public boolean addModel(E e) throws DaoBaseException {
		return getMapper().insert(e) > 0;
	}

	@Override
	public boolean addModelSelective(E e) throws DaoBaseException {
		return getMapper().insertSelective(e) > 0;
	}

	@Override
	public boolean updateModel(E e) throws DaoBaseException {
		return getMapper().updateByPrimaryKey(e) > 0;
	}

	@Override
	public boolean updateModelSelective(E e) throws DaoBaseException {
		return getMapper().updateByPrimaryKeySelective(e) > 0;
	}

	@Override
	public int updateModelByExample(E e, Object example) throws DaoBaseException {
		return getMapper().updateByExample(e, example);
	}

	@Override
	public int updateModelSelectiveByExample(E e, Object example) throws DaoBaseException {
		return getMapper().updateByExampleSelective(e, example);
	}

	@Override
	public boolean deleteModel(String key) throws DaoBaseException {
		return getMapper().deleteByPrimaryKey(key) > 0;
	}

	@Override
	public int deleteModel(E e) throws DaoBaseException {
		return getMapper().delete(e);
	}

	@Override
	public int deleteModelByExample(Object example) throws DaoBaseException {
		return getMapper().deleteByExample(example);
	}

	@Override
	public boolean deleteFakeByKey(String key) throws DaoBaseException {
		ISqlExpression deleteSqlExpression = SqlExpressionFactory.createExpression();
		deleteSqlExpression.addBody("update " + getTableName() + " set deleteMark=" + YesOrNo.YES).andWhere(getPrimaryKey() + "=#{key}").setParam("key", key);
		return update(deleteSqlExpression) > 0;
	}

	@Override
	public boolean applyChange(E e) throws DaoBaseException {
		if (ModelStatus.add.equals(e.getModelStatus())) {
			return addModelSelective(e);
		} else if (ModelStatus.update.equals(e.getModelStatus())) {
			return updateModelSelective(e);
		} else if (ModelStatus.delete.equals(e.getModelStatus())) {
			return deleteModel(e) == 1;
		}
		return false;
	}

	@Override
	public boolean applyChanges(List<E> list) throws DaoBaseException {
		if (list == null || list.size() == 0) {
			return false;
		}
		boolean result = false;
		List<E> insertList = new ArrayList<E>();
		for (E e : list) {
			if (ModelStatus.add.equals(e.getModelStatus())) {
				insertList.add(e);
			} else if (ModelStatus.update.equals(e.getModelStatus())) {
				result = updateModelSelective(e);
			} else if (ModelStatus.delete.equals(e.getModelStatus())) {
				result = deleteModel(e) == 1;
			}
		}
		if (insertList.size() > 0) {
			return getMapper().insertListEx(insertList) == insertList.size();
		}
		return result;
	}

	@Override
	public int update(String sql) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.update(sql);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public int update(String sql, Object params) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.update(sql, params);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public int delete(String sql) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.delete(sql);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public int delete(String sql, Object params) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.delete(sql, params);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public int insert(String sql) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.insert(sql);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public int insert(String sql, Object params) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.insert(sql, params);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Map<String, Object>> selectList(String sql) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectList(sql);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public <T> List<T> selectList(String sql, Class<T> resultType) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectList(sql, resultType);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Map<String, Object>> selectList(String sql, Object value) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectList(sql, value);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Map<String, Object>> selectListEx(String sql, Object value, RowBounds rowBounds) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectListEx(sql, value, rowBounds);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public <T> List<T> selectList(String sql, Object value, Class<T> resultType) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectList(sql, value, resultType);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public Map<String, Object> selectOne(String sql) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectOne(sql);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public <T> T selectOne(String sql, Class<T> resultType) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectOne(sql, resultType);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public Map<String, Object> selectOne(String sql, Object value) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectOne(sql, value);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public <T> T selectOne(String sql, Object value, Class<T> resultType) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectOne(sql, value, resultType);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<E> selectList(String sql, Object value, RowBounds rowBounds) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(ExecutorType.SIMPLE, true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectList(sql, value, rowBounds);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public <T> List<T> selectList(String sql, RowBounds rowBounds, Class<T> resultType) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectList(sql, rowBounds, resultType);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public <T> List<T> selectList(String sql, Object value, RowBounds rowBounds, Class<T> resultType) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectList(sql, value, rowBounds, resultType);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public int queryCount(String sql, Object value) throws DaoBaseException {
		SqlSession session = getSqlSessionFactory().openSession(true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.queryCount(sql, value);
		} catch (Exception e) {
			throw new DaoBaseException(e.getMessage());
		} finally {
			session.close();
		}
	}

	// 自定义
	@Override
	public E queryModel(ISqlExpression sqlExpression) {
		try {
			return selectOne(sqlExpression.toSql(), sqlExpression.getParams(), entityClass);
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public <T> T queryModel(ISqlExpression sqlExpression, Class<T> resultType) {
		try {
			return selectOne(sqlExpression.toSql(), sqlExpression.getParams(), resultType);
		} catch (DaoBaseException e) {
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public List<E> queryList(ISqlExpression sqlExpression) {
		try {
			return selectList(sqlExpression.toSql(), sqlExpression.getParams(), entityClass);
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public List<E> queryList(ISqlExpression sqlExpression, SearchModel search) {
		sqlExpression = sqlExpression.resolveSearchModel(search);
		return queryList(sqlExpression);
	}

	@Override
	public <T> List<T> queryList(ISqlExpression sqlExpression, Class<T> resultType) {
		try {
			return selectList(sqlExpression.toSql(), sqlExpression.getParams(), resultType);
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public <T> List<T> queryList(ISqlExpression sqlExpression, SearchModel search, Class<T> resultType) {
		sqlExpression = sqlExpression.resolveSearchModel(search);
		return queryList(sqlExpression, resultType);
	}

	@Override
	public List<E> queryList(ISqlExpression sqlExpression, PagerModel pagerModel) {
		try {
			if (pagerModel != null) {
				sqlExpression.addOrderInfoList(pagerModel.getOrderInfoList());
				return selectList(sqlExpression.toSql(), sqlExpression.getParams(), pagerModel.getRowBounds(), entityClass);
			} else {
				return queryList(sqlExpression);
			}

		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public List<E> queryList(ISqlExpression sqlExpression, SearchModel search, PagerModel pagerModel) {
		sqlExpression = sqlExpression.resolveSearchModel(search);
		return queryList(sqlExpression, pagerModel);
	}

	@Override
	public List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression, PagerModel pagerModel) {
		try {
			sqlExpression.addOrderInfoList(pagerModel.getOrderInfoList());
			return selectListEx(sqlExpression.toSql(), sqlExpression.getParams(), pagerModel.getRowBounds());
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression, SearchModel search, PagerModel pagerModel) {
		sqlExpression = sqlExpression.resolveSearchModel(search);
		return queryListEx(sqlExpression, pagerModel);
	}

	@Override
	public <T> List<T> queryList(ISqlExpression sqlExpression, PagerModel pagerModel, Class<T> resultType) {
		try {
			sqlExpression.addOrderInfoList(pagerModel.getOrderInfoList());
			return selectList(sqlExpression.toSql(), sqlExpression.getParams(), pagerModel.getRowBounds(), resultType);
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public <T> List<T> queryList(ISqlExpression sqlExpression, SearchModel search, PagerModel pagerModel, Class<T> resultType) {
		sqlExpression = sqlExpression.resolveSearchModel(search);
		return queryList(sqlExpression, pagerModel, resultType);
	}

	@Override
	public int queryCount(ISqlExpression sqlExpression) {
		try {
			return queryCount(sqlExpression.toSql(), sqlExpression.getParams());
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public int queryCount(ISqlExpression sqlExpression, SearchModel search) {
		sqlExpression = sqlExpression.resolveSearchModel(search);
		return queryCount(sqlExpression);
	}

	@Override
	public int update(ISqlExpression sqlExpression) {
		try {
			return update(sqlExpression.toSql(), sqlExpression.getParams());
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public int delete(ISqlExpression sqlExpression) {
		try {
			return delete(sqlExpression.toSql(), sqlExpression.getParams());
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public int insert(ISqlExpression sqlExpression) {
		try {
			return insert(sqlExpression.toSql(), sqlExpression.getParams());
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public double querySum(ISqlExpression sqlExpression) {
		List<Map<String, Object>> list;
		try {
			list = selectList(sqlExpression.toSql(), sqlExpression.getParams());
			if (list == null || list.size() == 0) {
				return 0.0;
			}
			Map<String, Object> map = list.get(0);
			return Double.parseDouble(map.get(map.keySet().toArray()[0]).toString());
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public double queryAggreg(ISqlExpression sqlExpression) {
		List<Map<String, Object>> list;
		try {
			list = selectList(sqlExpression.toSql(), sqlExpression.getParams());
			if (list == null || list.size() == 0) {
				return 0.0;
			}
			Map<String, Object> map = list.get(0);
			return Double.parseDouble(map.get(map.keySet().toArray()[0]).toString());
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public String queryScalar(ISqlExpression sqlExpression) {
		List<Map<String, Object>> list;
		try {
			list = selectList(sqlExpression.toSql(), sqlExpression.getParams());
			if (list == null || list.size() == 0) {
				return null;
			}
			Map<String, Object> map = list.get(0);
			if (map == null || map.keySet() == null || map.keySet().toArray() == null || map.keySet().toArray().length <= 0 || map.get(map.keySet().toArray()[0]) == null) {
				return null;
			}
			return map.get(map.keySet().toArray()[0]).toString();
		} catch (DaoBaseException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	public List<E> queryListMP(SearchModel search, PagerModel pagerModel) {
		return mapper.queryListMP(search, pagerModel);
	}

	public int queryCountMP(SearchModel search) {
		return mapper.queryCountMP(search);
	}

	public int insertListEx(List<E> list) {
		return mapper.insertListEx(list);
	}
}
