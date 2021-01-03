package com.tennetcn.free.data.dao.base.impl;

import cn.hutool.core.util.ReflectUtil;
import com.tennetcn.free.core.enums.ModelStatus;
import com.tennetcn.free.core.message.data.ModelBase;
import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.data.dao.base.*;
import com.tennetcn.free.data.dao.base.mapper.SqlMapper;
import com.tennetcn.free.data.message.DaoBaseRuntimeException;
import com.tennetcn.free.data.message.OrderInfo;
import com.tennetcn.free.data.utils.ClassAnnotationUtils;
import com.tennetcn.free.data.utils.Pager2RowBounds;
import com.tennetcn.free.data.utils.SqlExpressionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author chenghuan
 * @email 79763939@qq.com
 * @comment
 */
@Slf4j
public abstract class SuperDao<E extends ModelBase> extends DbContext<E> implements ISuperDao<E> {

	@Autowired
	private IMapper<E> mapper;

	public IMapper<E> getMapper() {
		return mapper;
	}

	@Autowired
	ISqlExecutor sqlExecutor;

	@Override
	public String getModelName() {
		return ClassAnnotationUtils.getModelName(entityClass);
	}

	@Override
	public String getTableName() {
		return ClassAnnotationUtils.getTableName(entityClass);
	}

	@Autowired
	IBatchInsertProcessor batchInsertProcessor;

	@Override
	public <T extends ModelBase> String getTableName(Class<T> classType) {
		return ClassAnnotationUtils.getTableName(classType);
	}

	@Override
	public String getDbFirstColumnKey() {
		return ClassAnnotationUtils.getFirstColumnKey(entityClass);
	}

	@Override
	public List<OrderInfo> getOrderInfoList() {
		return ClassAnnotationUtils.getOrderInfoList(entityClass);
	}

	@Override
	public List<E> queryList() throws DaoBaseRuntimeException {
		ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
		sqlExpression.selectAllFrom(entityClass);

		return queryList(sqlExpression);
	}

	@Override
	public List<E> queryListByIds(String ids) throws DaoBaseRuntimeException {
		if (StringUtils.isEmpty(ids)) {
			return null;
		}
		return queryListByIds(Arrays.asList(ids.split(",")));
	}

	@Override
	public List<E> queryListByIds(List<String> ids) throws DaoBaseRuntimeException {
		ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
		sqlExpression.selectAllFrom(entityClass).andWhereInString(getDbFirstColumnKey(), ids);

		return queryList(sqlExpression);
	}

	@Override
	public List<E> queryListByIds(String... ids) throws DaoBaseRuntimeException {
		if (ids == null || ids.length <= 0) {
			return null;
		}
		return queryListByIds(Arrays.asList(ids));
	}

	@Override
	public List<E> queryList(PagerModel pagerModel) throws DaoBaseRuntimeException {
		return getMapper().selectByRowBounds(null, Pager2RowBounds.getRowBounds(pagerModel));
	}
	
	@Override
	public List<E> queryList(E e) throws DaoBaseRuntimeException {
		return getMapper().select(e);
	}

	@Override
	public E queryModel(String key) throws DaoBaseRuntimeException {
		return getMapper().selectByPrimaryKey(key);
	}

	@Override
	public E queryModel(E e) throws DaoBaseRuntimeException {
		return getMapper().selectOne(e);
	}

	@Override
	public int queryCount(E e) throws DaoBaseRuntimeException {
		return getMapper().selectCount(e);
	}

	@Override
	public int queryCount() throws DaoBaseRuntimeException {
		ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
		sqlExpression.addBody("select count(1) from " + getTableName());

		return queryCount(sqlExpression);
	}

	@Override
	public boolean addModel(E e) throws DaoBaseRuntimeException {
		return getMapper().insert(e) > 0;
	}

	@Override
	public boolean addModelSelective(E e) throws DaoBaseRuntimeException {
		return getMapper().insertSelective(e) > 0;
	}

	@Override
	public boolean updateModel(E e) throws DaoBaseRuntimeException {
		return getMapper().updateByPrimaryKey(e) > 0;
	}

	@Override
	public boolean updateModelSelective(E e) throws DaoBaseRuntimeException {
		return getMapper().updateByPrimaryKeySelective(e) > 0;
	}

//	@Override
//	public int updateModelByExample(E e, Object example) throws DaoBaseRuntimeException {
//		return getMapper().updateByExample(e, example);
//	}
//
//	@Override
//	public int updateModelSelectiveByExample(E e, Object example) throws DaoBaseRuntimeException {
//		return getMapper().updateByExampleSelective(e, example);
//	}

	@Override
	public boolean deleteModel(String key) throws DaoBaseRuntimeException {
		return getMapper().deleteByPrimaryKey(key) > 0;
	}

	@Override
	public int deleteModel(E e) throws DaoBaseRuntimeException {
		return getMapper().delete(e);
	}

//	@Override
//	public int deleteModelByExample(Object example) throws DaoBaseRuntimeException {
//		return getMapper().deleteByExample(example);
//	}

	@Override
	public boolean applyChange(E e) throws DaoBaseRuntimeException {
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
	public boolean applyChanges(List<E> list) throws DaoBaseRuntimeException {
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
	public int update(String sql) throws DaoBaseRuntimeException {
		return sqlExecutor.update(sql);
	}

	@Override
	public int update(String sql, Object params) throws DaoBaseRuntimeException {
		return sqlExecutor.update(sql,params);
	}

	@Override
	public int delete(String sql) throws DaoBaseRuntimeException {
		return sqlExecutor.delete(sql);
	}

	@Override
	public int delete(String sql, Object params) throws DaoBaseRuntimeException {
		return sqlExecutor.delete(sql, params);
	}

	@Override
	public int insert(String sql) throws DaoBaseRuntimeException {
		return sqlExecutor.insert(sql);
	}

	@Override
	public int insert(String sql, Object params) throws DaoBaseRuntimeException {
		return sqlExecutor.insert(sql, params);
	}

	@Override
	public List<Map<String, Object>> selectList(String sql) throws DaoBaseRuntimeException {
		return sqlExecutor.selectList(sql);
	}

	@Override
	public <T> List<T> selectList(String sql, Class<T> resultType) throws DaoBaseRuntimeException {
		return sqlExecutor.selectList(sql,resultType);
	}

	@Override
	public List<Map<String, Object>> selectList(String sql, Object value) throws DaoBaseRuntimeException {
		return sqlExecutor.selectList(sql, value);
	}

	@Override
	public List<Map<String, Object>> selectListEx(String sql, Object value, RowBounds rowBounds) throws DaoBaseRuntimeException {
		return sqlExecutor.selectListEx(sql, value, rowBounds);
	}

	@Override
	public <T> List<T> selectList(String sql, Object value, Class<T> resultType) throws DaoBaseRuntimeException {
		return sqlExecutor.selectList(sql, value, resultType);
	}

	@Override
	public Map<String, Object> selectOne(String sql) throws DaoBaseRuntimeException {
		return sqlExecutor.selectOne(sql);
	}

	@Override
	public <T> T selectOne(String sql, Class<T> resultType) throws DaoBaseRuntimeException {
		return sqlExecutor.selectOne(sql, resultType);
	}

	@Override
	public Map<String, Object> selectOne(String sql, Object value) throws DaoBaseRuntimeException {
		return sqlExecutor.selectOne(sql, value);
	}

	@Override
	public <T> T selectOne(String sql, Object value, Class<T> resultType) throws DaoBaseRuntimeException {
		return sqlExecutor.selectOne(sql, value, resultType);
	}

	@Override
	public List<E> selectList(String sql, Object value, RowBounds rowBounds) throws DaoBaseRuntimeException {
		SqlSession session = getSqlSessionFactory().openSession(ExecutorType.SIMPLE, true);
		try {
			SqlMapper mapper = new SqlMapper(session);
			return mapper.selectList(sql, value, rowBounds);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public <T> List<T> selectList(String sql, RowBounds rowBounds, Class<T> resultType) throws DaoBaseRuntimeException {
		return sqlExecutor.selectList(sql, rowBounds, resultType);
	}

	@Override
	public <T> List<T> selectList(String sql, Object value, RowBounds rowBounds, Class<T> resultType) throws DaoBaseRuntimeException {
		return sqlExecutor.selectList(sql, value, rowBounds, resultType);
	}

	@Override
	public int queryCount(String sql, Object value) throws DaoBaseRuntimeException {
		return sqlExecutor.queryCount(sql, value);
	}

	// 自定义
	@Override
	public E queryModel(ISqlExpression sqlExpression) {
		try {
			return selectOne(sqlExpression.toSql(), sqlExpression.getParams(), entityClass);
		} catch (DaoBaseRuntimeException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public <T> T queryModel(ISqlExpression sqlExpression, Class<T> resultType) {
		return sqlExecutor.queryModel(sqlExpression, resultType);
	}

	@Override
	public List<E> queryList(ISqlExpression sqlExpression) {
		try {
			return selectList(sqlExpression.toSql(), sqlExpression.getParams(), entityClass);
		} catch (DaoBaseRuntimeException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public <T> List<T> queryList(ISqlExpression sqlExpression, Class<T> resultType) {
		return sqlExecutor.queryList(sqlExpression, resultType);
	}

	@Override
	public List<E> queryList(ISqlExpression sqlExpression, PagerModel pagerModel) {
		try {
			if (pagerModel != null) {
				return selectList(sqlExpression.toSql(), sqlExpression.getParams(), Pager2RowBounds.getRowBounds(pagerModel), entityClass);
			} else {
				return queryList(sqlExpression);
			}

		} catch (DaoBaseRuntimeException e) {
			e.printStackTrace();
			throw new DaoBaseRuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression, PagerModel pagerModel) {
		return sqlExecutor.queryListEx(sqlExpression, pagerModel);
	}

	@Override
	public <T> List<T> queryList(ISqlExpression sqlExpression, PagerModel pagerModel, Class<T> resultType) {
		return sqlExecutor.queryList(sqlExpression, pagerModel, resultType);
	}

	@Override
	public int queryCount(ISqlExpression sqlExpression) {
		return sqlExecutor.queryCount(sqlExpression);
	}

	@Override
	public int update(ISqlExpression sqlExpression) {
		return sqlExecutor.update(sqlExpression);
	}

	@Override
	public int delete(ISqlExpression sqlExpression) {
		return sqlExecutor.delete(sqlExpression);
	}

	@Override
	public int deleteByIds(List<String> ids) {
		if(ids==null||ids.isEmpty()){
			return 0;
		}
		ISqlExpression deleteSql = SqlExpressionFactory.createExpression();
		deleteSql.delete().from(entityClass).andWhereInString(getDbFirstColumnKey(), ids);

		return delete(deleteSql);
	}

	@Override
	public int deleteByIds(String... ids) {
		return deleteByIds(Arrays.asList(ids));
	}

	@Override
	public int deleteByIds(String ids) {
		String[] split = ids.split(",");
		return deleteByIds(Arrays.asList(ids));
	}

	@Override
	public int insert(ISqlExpression sqlExpression) {
		return sqlExecutor.insert(sqlExpression);
	}

	@Override
	public double queryScalarDouble(ISqlExpression sqlExpression) {
		return sqlExecutor.queryScalarDouble(sqlExpression);
	}

	@Override
	public int queryScalarInt(ISqlExpression sqlExpression) {
		return sqlExecutor.queryScalarInt(sqlExpression);
	}

	@Override
	public String queryScalar(ISqlExpression sqlExpression) {
		return sqlExecutor.queryScalar(sqlExpression);
	}

	@Override
	public int insertListEx(List<E> list) {
		if(list==null||list.isEmpty()){
			return 0;
		}
		return mapper.insertListEx(list);
	}

	private String getSqlId(String methodName){
		Class clazz = (Class)ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(this.mapper, "h"),"mapperInterface");

		return clazz.getName() + "." + methodName;
	}

	@Override
	public int batchInsertList(List<E> list){
		return batchInsertList(list,64);
	}

	@Override
	public int batchInsertList(List<E> list, int batchSize) {
		String sqlId = getSqlId("insert");

		return batchInsertProcessor.insertListBatch(sqlId,list,batchSize);
	}

	@Override
	public int batchUpdateList(List<E> list){
		String sqlId = getSqlId("update");

		return batchInsertProcessor.updateListBatch(sqlId,list);
	}

	@Override
	public int batchInsertSelectiveList(List<E> list) {
		return batchInsertSelectiveList(list,64);
	}

	@Override
	public int batchInsertSelectiveList(List<E> list, int batchSize) {
		String sqlId = getSqlId("insertSelective");

		return batchInsertProcessor.insertListBatch(sqlId,list,batchSize);
	}

	@Override
	public int batchUpdateSelectiveList(List<E> list) {
		String sqlId = getSqlId("updateSelective");

		return batchInsertProcessor.updateListBatch(sqlId,list);
	}
}
