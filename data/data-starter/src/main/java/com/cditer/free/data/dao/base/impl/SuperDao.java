package com.cditer.free.data.dao.base.impl;

import cn.hutool.core.util.ReflectUtil;
import com.cditer.free.core.enums.ModelStatus;
import com.cditer.free.core.message.data.ModelBase;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.data.dao.base.*;
import com.cditer.free.data.dao.base.mapper.SqlMapper;
import com.cditer.free.data.message.DaoBaseRuntimeException;
import com.cditer.free.data.message.OrderInfo;
import com.cditer.free.data.utils.ClassAnnotationUtils;
import com.cditer.free.data.utils.Pager2RowBounds;
import com.cditer.free.data.utils.SqlExpressionFactory;
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
import java.util.stream.Collectors;

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
        if (!StringUtils.hasText(ids)) {
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
        return getMapper().insert(e) == 1;
    }

    @Override
    public boolean addModelSelective(E e) throws DaoBaseRuntimeException {
        return getMapper().insertSelective(e) == 1;
    }

    @Override
    public boolean updateModel(E e) throws DaoBaseRuntimeException {
        return getMapper().updateByPrimaryKey(e) == 1;
    }

    @Override
    public boolean updateModelSelective(E e) throws DaoBaseRuntimeException {
        return getMapper().updateByPrimaryKeySelective(e) == 1;
    }

    @Override
    public boolean deleteModel(String key) throws DaoBaseRuntimeException {
        return getMapper().deleteByPrimaryKey(key) == 1;
    }

    @Override
    public boolean deleteModel(E e) throws DaoBaseRuntimeException {
        return getMapper().delete(e) == 1;
    }

    @Override
    public boolean applyChange(E e) throws DaoBaseRuntimeException {
        if (ModelStatus.add.equals(e.getModelStatus())) {
            return addModelSelective(e);
        } else if (ModelStatus.update.equals(e.getModelStatus())) {
            return updateModelSelective(e);
        } else if (ModelStatus.delete.equals(e.getModelStatus())) {
            return deleteModel(e);
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
                result = deleteModel(e);
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
        return sqlExecutor.update(sql, params);
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
    public int queryCount(String sql, Object value) throws DaoBaseRuntimeException {
        return sqlExecutor.selectCount(sql, value);
    }

    // 自定义
    @Override
    public E queryModel(ISqlExpression sqlExpression) {
        try {
            return sqlExecutor.selectOne(sqlExpression.toSql(), sqlExpression.getParams(), entityClass);
        } catch (DaoBaseRuntimeException e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> queryModelEx(ISqlExpression sqlExpression) {
        return sqlExecutor.selectOneEx(sqlExpression);
    }

    @Override
    public <T> T queryModel(ISqlExpression sqlExpression, Class<T> resultType) {
        return sqlExecutor.selectOne(sqlExpression, resultType);
    }

    @Override
    public List<E> queryList(ISqlExpression sqlExpression) {
        try {
            return sqlExecutor.selectList(sqlExpression, entityClass);
        } catch (DaoBaseRuntimeException e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        }
    }

    @Override
    public <T> List<T> queryList(ISqlExpression sqlExpression, Class<T> resultType) {
        return sqlExecutor.selectList(sqlExpression, resultType);
    }

    @Override
    public List<E> queryList(ISqlExpression sqlExpression, PagerModel pagerModel) {
        try {
            if (pagerModel != null) {
                return sqlExecutor.selectList(sqlExpression, Pager2RowBounds.getRowBounds(pagerModel), entityClass);
            } else {
                return queryList(sqlExpression);
            }

        } catch (DaoBaseRuntimeException e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression) {
        return sqlExecutor.selectListEx(sqlExpression);
    }

    @Override
    public List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression, PagerModel pagerModel) {
        return sqlExecutor.selectListEx(sqlExpression, pagerModel);
    }

    @Override
    public <T> List<T> queryList(ISqlExpression sqlExpression, PagerModel pagerModel, Class<T> resultType) {
        return sqlExecutor.selectList(sqlExpression, pagerModel, resultType);
    }

    @Override
    public int queryCount(ISqlExpression sqlExpression) {
        return sqlExecutor.selectCount(sqlExpression);
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
        if (ids == null || ids.isEmpty()) {
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
        return deleteByIds(Arrays.asList(split));
    }

    @Override
    public int insert(ISqlExpression sqlExpression) {
        return sqlExecutor.insert(sqlExpression);
    }

    @Override
    public double queryScalarDouble(ISqlExpression sqlExpression) {
        return sqlExecutor.selectScalarDouble(sqlExpression);
    }

    @Override
    public int queryScalarInt(ISqlExpression sqlExpression) {
        return sqlExecutor.selectScalarInt(sqlExpression);
    }

    @Override
    public String queryScalar(ISqlExpression sqlExpression) {
        return sqlExecutor.selectScalar(sqlExpression);
    }

    @Override
    public int insertListEx(List<E> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return insertListEx(list, 32);
    }

    @Override
    public int insertListEx(List<E> list, int batchSize) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int insertCount = 0;

        List<List<E>> lists = CommonUtils.listSlice(list, batchSize);
        for (List<E> currentList : lists) {
            insertCount += mapper.insertListEx(currentList);
        }

        return insertCount;
    }

    private String getSqlId(String methodName) {
        Class clazz = (Class) ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(this.mapper, "h"), "mapperInterface");

        return clazz.getName() + "." + methodName;
    }

    @Override
    public int batchInsertList(List<E> list) {
        return batchInsertList(list, 64);
    }

    @Override
    public int batchInsertList(List<E> list, int batchSize) {
        String sqlId = getSqlId("insert");

        return batchInsertProcessor.insertListBatch(sqlId, list, batchSize);
    }

    @Override
    public int batchUpdateList(List<E> list) {
        String sqlId = getSqlId("update");

        return batchInsertProcessor.updateListBatch(sqlId, list);
    }

    @Override
    public int batchInsertSelectiveList(List<E> list) {
        return batchInsertSelectiveList(list, 64);
    }

    @Override
    public int batchInsertSelectiveList(List<E> list, int batchSize) {
        String sqlId = getSqlId("insertSelective");

        return batchInsertProcessor.insertListBatch(sqlId, list, batchSize);
    }

    @Override
    public int batchUpdateSelectiveList(List<E> list) {
        String sqlId = getSqlId("updateSelective");

        return batchInsertProcessor.updateListBatch(sqlId, list);
    }
}
