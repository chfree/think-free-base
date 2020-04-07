package com.tennetcn.free.data.dao.base;

import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.core.message.data.ModelBase;
import org.apache.ibatis.session.RowBounds;

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

    List<E> queryListByExample(Object example);

    List<E> queryList(Object example, RowBounds rowBounds);

    List<E> queryList(Object example, PagerModel pagerModel);

    int queryCount(E e);

    int queryCountByExample(Object example);

    int queryCount();

    E queryModel(String key);

    E queryModel(E e);

    E queryModelByExample(Object example);

    boolean addModel(E e);

    boolean addModelSelective(E e);

    boolean updateModel(E e);

    boolean updateModelSelective(E e);

    int updateModelByExample(E e, Object example);

    int updateModelSelectiveByExample(E e, Object example);

    boolean deleteModel(String key);

    int deleteModel(E e);

    int deleteModelByExample(Object example);

    boolean deleteFakeByKey(String key);

    boolean applyChange(E e);

    boolean applyChanges(List<E> list);

    int insertListEx(List<E> list);

}
