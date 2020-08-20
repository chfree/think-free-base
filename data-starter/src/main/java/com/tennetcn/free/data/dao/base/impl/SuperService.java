package com.tennetcn.free.data.dao.base.impl;

import com.tennetcn.free.core.message.data.ModelBase;
import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.data.dao.base.ISuperDao;
import com.tennetcn.free.data.dao.base.ISuperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-31 22:43
 * @comment
 */

@Slf4j
public abstract class SuperService<E extends ModelBase> implements ISuperService<E> {

    @Autowired
    private ISuperDao<E> superDao;

    public ISuperDao<E> getSuperDao() {
        return superDao;
    }

    @SuppressWarnings("unused")
    private Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public SuperService() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.entityClass = (Class<E>) params[0];
    }

    @Override
    public String getModelName() {
        return getSuperDao().getModelName();
    }

    @Override
    public List<E> queryList() {
        return getSuperDao().queryList();
    }

    @Override
    public List<E> queryListByIds(String ids) {
        return getSuperDao().queryListByIds(ids);
    }

    @Override
    public List<E> queryListByIds(List<String> ids) {
        return getSuperDao().queryListByIds(ids);
    }

    @Override
    public List<E> queryListByIds(String... ids) {
        return getSuperDao().queryListByIds(ids);
    }


    @Override
    public List<E> queryList(PagerModel pagerModel) {
        return getSuperDao().queryList(pagerModel);
    }

    @Override
    public List<E> queryList(E e) {
        return getSuperDao().queryList(e);
    }

    @Override
    public List<E> queryListByExample(Object example) {
        return getSuperDao().queryListByExample(example);
    }

    @Override
    public List<E> queryList(Object example, RowBounds rowBounds) {
        return getSuperDao().queryList(example, rowBounds);
    }

    @Override
    public List<E> queryList(Object example, PagerModel pagerModel) {
        return getSuperDao().queryList(example, pagerModel);
    }

    @Override
    public int queryCount(E e) {
        return getSuperDao().queryCount(e);
    }

    @Override
    public int queryCountByExample(Object example) {
        return getSuperDao().queryCountByExample(example);
    }

    @Override
    public int queryCount() {
        return getSuperDao().queryCount();
    }

    @Override
    public E queryModel(String key) {
        return getSuperDao().queryModel(key);
    }

    @Override
    public E queryModel(E e) {
        return getSuperDao().queryModel(e);
    }

    @Override
    public E queryModelByExample(Object example) {
        return getSuperDao().queryModelByExample(example);
    }

    @Override
    public boolean addModel(E e) {
        return getSuperDao().addModel(e);
    }

    @Override
    public boolean addModelSelective(E e) {
        return getSuperDao().addModelSelective(e);
    }

    @Override
    public boolean updateModel(E e) {
        return getSuperDao().updateModel(e);
    }

    @Override
    public boolean updateModelSelective(E e) {
        return getSuperDao().updateModelSelective(e);
    }

    @Override
    public int updateModelByExample(E e, Object example) {
        return getSuperDao().updateModelByExample(e, example);
    }

    @Override
    public int updateModelSelectiveByExample(E e, Object example) {
        return getSuperDao().updateModelSelectiveByExample(e, example);
    }

    @Override
    public boolean deleteModel(String key) {
        return getSuperDao().deleteModel(key);
    }

    @Override
    public int deleteModel(E e) {
        return getSuperDao().deleteModel(e);
    }

    @Override
    public int deleteModelByExample(Object example) {
        return getSuperDao().deleteModelByExample(example);
    }

    public boolean applyChange(E e) {
        return getSuperDao().applyChange(e);
    }

    public boolean applyChanges(List<E> list) {
        return getSuperDao().applyChanges(list);
    }


    public int insertListEx(List<E> list) {
        return getSuperDao().insertListEx(list);
    }

    @Override
    public int batchInsertList(List<E> list) {
        return getSuperDao().batchInsertList(list);
    }

    @Override
    public int batchUpdateList(List<E> list) {
        return getSuperDao().batchUpdateList(list);
    }

    @Override
    public int batchInsertSelectiveList(List<E> list) {
        return getSuperDao().batchInsertSelectiveList(list);
    }

    @Override
    public int batchUpdateSelectiveList(List<E> list) {
        return getSuperDao().batchUpdateSelectiveList(list);
    }
}

