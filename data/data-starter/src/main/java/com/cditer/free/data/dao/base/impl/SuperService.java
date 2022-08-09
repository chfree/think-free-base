package com.cditer.free.data.dao.base.impl;

import cn.hutool.core.util.ReflectUtil;
import com.cditer.free.core.enums.ModelStatus;
import com.cditer.free.core.message.data.ModelBase;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.core.util.CommonUtils;
import com.cditer.free.data.dao.base.IBatchInsertProcessor;
import com.cditer.free.data.dao.base.IMapper;
import com.cditer.free.data.dao.base.ISqlExecutor;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.dao.base.ISuperDao;
import com.cditer.free.data.dao.base.ISuperService;
import com.cditer.free.data.message.DaoBaseRuntimeException;
import com.cditer.free.data.utils.ClassAnnotationUtils;
import com.cditer.free.data.utils.DbModelSaveInceptorHelper;
import com.cditer.free.data.utils.Pager2RowBounds;
import com.cditer.free.data.utils.SqlExpressionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-31 22:43
 * @comment
 */

@Slf4j
public abstract class SuperService<E extends ModelBase> extends DbContext implements ISuperService<E> {

    @Autowired
    private IMapper<E> mapper;

    protected IMapper<E> getMapper() {
        return mapper;
    }

    @Autowired
    private ISqlExecutor sqlExecutor;

    @Autowired
    private DbModelSaveInceptorHelper dbModelSaveInceptorHelper;

    @Autowired
    private IBatchInsertProcessor batchInsertProcessor;

    protected ISqlExecutor getSqlExecutor() {
        return this.sqlExecutor;
    }

    protected DbModelSaveInceptorHelper getDbModelSaveInceptorHelper() {
        return this.dbModelSaveInceptorHelper;
    }

    protected IBatchInsertProcessor getBatchInsertProcessor() {
        return this.batchInsertProcessor;
    }

    protected String getDbFirstColumnKey() {
        return ClassAnnotationUtils.getFirstColumnKey(entityClass);
    }

    @Override
    public String getModelName() {
        return ClassAnnotationUtils.getModelName(entityClass);
    }

    @Override
    public String getTableName() {
        return ClassAnnotationUtils.getTableName(entityClass);
    }

    protected List<E> queryList(ISqlExpression sqlExpression) {
        try {
            return sqlExecutor.selectList(sqlExpression, entityClass);
        } catch (DaoBaseRuntimeException e) {
            e.printStackTrace();
            throw new DaoBaseRuntimeException(e);
        }
    }

    @Override
    public List<E> queryList() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(entityClass);

        return queryList(sqlExpression);
    }


    @Override
    public List<E> queryListByIds(String ids) {
        if (!StringUtils.hasText(ids)) {
            return null;
        }
        return queryListByIds(Arrays.asList(ids.split(",")));
    }

    @Override
    public List<E> queryListByIds(List<String> ids) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(entityClass).andWhereInString(getDbFirstColumnKey(), ids);

        return queryList(sqlExpression);
    }

    @Override
    public List<E> queryListByIds(String... ids) {
        if (ids == null || ids.length <= 0) {
            return null;
        }
        return queryListByIds(Arrays.asList(ids));
    }


    @Override
    public List<E> queryList(PagerModel pagerModel) {
        return getMapper().selectByRowBounds(null, Pager2RowBounds.getRowBounds(pagerModel));
    }

    @Override
    public List<E> queryList(E e) {
        return getMapper().select(e);
    }

    @Override
    public int queryCount(E e) {
        return getMapper().selectCount(e);
    }

    @Override
    public int queryCount() {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.addBody("select count(1) from " + getTableName());

        return queryCount(sqlExpression);
    }

    protected int queryCount(ISqlExpression sqlExpression) {
        return sqlExecutor.selectCount(sqlExpression);
    }

    protected int delete(ISqlExpression sqlExpression) {
        return sqlExecutor.delete(sqlExpression);
    }

    @Override
    public E queryModel(String key) {
        return getMapper().selectByPrimaryKey(key);
    }

    @Override
    public E queryModel(E e) {
        return getMapper().selectOne(e);
    }

    @Override
    public boolean addModel(E e) {
        dbModelSaveInceptorHelper.dbModelSaveBefore(Arrays.asList(e));
        boolean result = getMapper().insert(e) == 1;
        dbModelSaveInceptorHelper.dbModelSaveAfter(Arrays.asList(e));

        return result;
    }

    @Override
    public boolean addModelSelective(E e) {
        dbModelSaveInceptorHelper.dbModelSaveBefore(Arrays.asList(e));
        boolean result = getMapper().insertSelective(e) == 1;
        dbModelSaveInceptorHelper.dbModelSaveAfter(Arrays.asList(e));

        return result;
    }

    @Override
    public boolean updateModel(E e) {
        dbModelSaveInceptorHelper.dbModelSaveBefore(Arrays.asList(e));
        boolean result = getMapper().updateByPrimaryKey(e) == 1;
        dbModelSaveInceptorHelper.dbModelSaveAfter(Arrays.asList(e));

        return result;
    }

    @Override
    public boolean updateModelSelective(E e) {
        dbModelSaveInceptorHelper.dbModelSaveBefore(Arrays.asList(e));
        boolean result = getMapper().updateByPrimaryKeySelective(e) == 1;
        dbModelSaveInceptorHelper.dbModelSaveAfter(Arrays.asList(e));

        return result;
    }

    @Override
    public boolean deleteModel(String key) {
        return getMapper().deleteByPrimaryKey(key) == 1;
    }

    @Override
    public boolean deleteModel(E e) {
        dbModelSaveInceptorHelper.dbModelSaveBefore(Arrays.asList(e));
        boolean result = getMapper().delete(e) == 1;
        dbModelSaveInceptorHelper.dbModelSaveAfter(Arrays.asList(e));

        return result;
    }

    @Override
    public int deleteByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
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

    public boolean applyChange(E e) {
        boolean result = false;
        if (ModelStatus.add.equals(e.getModelStatus())) {
            result = addModelSelective(e);
        } else if (ModelStatus.update.equals(e.getModelStatus())) {
            result = updateModelSelective(e);
        } else if (ModelStatus.delete.equals(e.getModelStatus())) {
            result = deleteModel(e);
        }
        return result;
    }

    public boolean applyChanges(List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        boolean result = false;
        List<E> insertList = new ArrayList<>();
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
            result = insertListEx(insertList) == insertList.size();
        }
        return result;
    }


    public int insertListEx(List<? extends E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return insertListEx(list, 32);
    }

    @Override
    public int insertListEx(List<? extends E> list, int batchSize) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        int insertCount = 0;

        list.forEach(item -> item.setModelStatus(ModelStatus.add));

        dbModelSaveInceptorHelper.dbModelSaveBefore(list);
        List<? extends List<? extends E>> lists = CommonUtils.listSlice(list, batchSize);
        for (List<? extends E> currentList : lists) {
            insertCount += mapper.insertListEx(currentList);
        }
        dbModelSaveInceptorHelper.dbModelSaveAfter(list);

        return insertCount;
    }

    private String getSqlId(String methodName) {
        Class clazz = (Class) ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(this.mapper, "h"), "mapperInterface");

        return clazz.getName() + "." + methodName;
    }

    @Override
    public int batchInsertList(List<? extends E> list) {
        return batchInsertList(list, 64);
    }

    @Override
    public int batchInsertList(List<? extends E> list, int batchSize) {
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        list.forEach(item -> item.setModelStatus(ModelStatus.add));

        String sqlId = getSqlId("insert");

        dbModelSaveInceptorHelper.dbModelSaveBefore(list);
        int count = batchInsertProcessor.insertListBatch(sqlId, list, batchSize);
        dbModelSaveInceptorHelper.dbModelSaveAfter(list);

        return count;
    }

    @Override
    public int batchUpdateList(List<? extends E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        list.forEach(item -> item.setModelStatus(ModelStatus.update));

        String sqlId = getSqlId("update");
        dbModelSaveInceptorHelper.dbModelSaveBefore(list);
        int count = batchInsertProcessor.updateListBatch(sqlId, list);
        dbModelSaveInceptorHelper.dbModelSaveAfter(list);

        return count;
    }

    @Override
    public int batchInsertSelectiveList(List<? extends E> list) {
        return batchInsertSelectiveList(list, 64);
    }

    @Override
    public int batchInsertSelectiveList(List<? extends E> list, int batchSize) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        list.forEach(item -> item.setModelStatus(ModelStatus.add));

        String sqlId = getSqlId("insertSelective");

        dbModelSaveInceptorHelper.dbModelSaveBefore(list);
        int count = batchInsertProcessor.insertListBatch(sqlId, list, batchSize);
        dbModelSaveInceptorHelper.dbModelSaveAfter(list);

        return count;
    }

    @Override
    public int batchUpdateSelectiveList(List<? extends E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        list.forEach(item -> item.setModelStatus(ModelStatus.add));

        String sqlId = getSqlId("updateSelective");
        dbModelSaveInceptorHelper.dbModelSaveBefore(list);
        int count = batchInsertProcessor.updateListBatch(sqlId, list);
        dbModelSaveInceptorHelper.dbModelSaveAfter(list);

        return count;
    }
}

