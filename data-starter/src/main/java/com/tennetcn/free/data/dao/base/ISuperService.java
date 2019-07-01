package com.tennetcn.free.data.dao.base;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.tennetcn.free.data.message.DaoBaseException;
import com.tennetcn.free.data.message.ModelBase;
import com.tennetcn.free.data.message.PagerModel;
import com.tennetcn.free.data.message.SearchModel;

/**
 * @author chenghuan
 * @email 79763939@qq.com
 * @comment
 */

public interface ISuperService<E extends ModelBase> {
	/**
	 * 获得当前泛型参数的类型名称
	 * 
	 * @return
	 */
	public String getModelName();

	public List<E> queryList();
	
	public List<E> queryListByIds(String ids);
	
	public List<E> queryListByIds(List<String> ids);
	
	public List<E> queryListByIds(String ...ids);
	
	public List<E> queryList(SearchModel search);
	
	public List<E> queryList(E e);
	
	public List<E> queryList(PagerModel pagerModel);
	
	public List<E> queryList(SearchModel search,PagerModel pagerModel);
	
	public List<E> queryList(boolean deleteMark,PagerModel pagerModel);
	
	public List<E> queryList(boolean deleteMark);
	
	public List<E> queryList(int deleteMark,PagerModel pagerModel);
	
	public List<E> queryList(int deleteMark);
	
	public List<E> queryListByExample(Object example);
	
	public List<E> queryListByExampleAndRowBounds(Object example,RowBounds rowBounds);
	
	public List<E> queryList(Object example,PagerModel pagerModel);
	
	public int queryCount(E e);
	
	public int queryCountByExample(Object example);
	
	public int queryCount();
	
	public int queryCount(SearchModel search);
	
	public int queryCount(boolean deleteMark);
	
	public E queryModel(String key);
	
	public E queryModel(E e);
	
	public E queryModelByExample(Object example);
	
	public boolean addModel(E e);
	
	public boolean addModelSelective(E e);
	
	public boolean updateModel(E e);
	
	public boolean updateModelSelective(E e);
	
	public int updateModelByExample(E e,Object example);
	
	public int updateModelSelectiveByExample(E e,Object example);
	
	public boolean deleteModel(String key);
	
	public int deleteModel(E e);
	
	public int deleteModelByExample(Object example);
	
	public boolean deleteFakeByKey(String key);
	
	public boolean applyChange(E e);
	
	public boolean applyChanges(List<E> list);
	
	public List<E> queryListMP(SearchModel search,PagerModel pagerModel);
	
	public int queryCountMP(SearchModel search);
	
	public int insertListEx(List<E> list);
}
