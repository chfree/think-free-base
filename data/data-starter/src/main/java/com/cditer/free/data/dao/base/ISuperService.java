package com.cditer.free.data.dao.base;

import com.cditer.free.core.message.data.ModelBase;
import com.cditer.free.core.message.data.PagerModel;

import java.util.List;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-08-31 22:38
 * @comment
 */

public interface ISuperService<E extends ModelBase> {
    /**
     * 获得当前泛型参数的类型名称
     *
     * @return
     */
    String getModelName();

    List<E> queryList();

    List<E> queryListByIds(String ids);

    List<E> queryListByIds(List<String> ids);

    List<E> queryListByIds(String... ids);

    List<E> queryList(E e);

    List<E> queryList(PagerModel pagerModel);

    int queryCount(E e);

    int queryCount();

    E queryModel(String key);

    E queryModel(E e);

    boolean addModel(E e);

    boolean addModelSelective(E e);

    boolean updateModel(E e);

    boolean updateModelSelective(E e);

    boolean deleteModel(String key);

    int deleteModel(E e);

    int deleteByIds(List<String> ids);

    int deleteByIds(String... ids);

    int deleteByIds(String ids);

    boolean applyChange(E e);

    boolean applyChanges(List<E> list);

    int insertListEx(List<E> list);

    int insertListEx(List<E> list, int batchSize);

    int batchInsertList(List<E> list);

    int batchInsertList(List<E> list, int batchSize);

    int batchUpdateList(List<E> list);

    int batchInsertSelectiveList(List<E> list);

    int batchInsertSelectiveList(List<E> list, int batchSize);

    int batchUpdateSelectiveList(List<E> list);
}
