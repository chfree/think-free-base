package com.tennetcn.free.data.dao.base.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tennetcn.free.data.dao.base.ISuperDao;
import com.tennetcn.free.data.dao.base.ISuperService;
import com.tennetcn.free.data.message.DaoBaseException;
import com.tennetcn.free.data.message.DaoBaseRuntimeException;
import com.tennetcn.free.data.message.ModelBase;
import com.tennetcn.free.data.message.PagerModel;
import com.tennetcn.free.data.message.SearchModel;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @comment 
 */

public abstract class SuperService<E extends ModelBase> implements ISuperService<E> {
	private static  Logger logger = LoggerFactory.getLogger(SuperService.class);
	
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
		try {
			return getSuperDao().queryList();
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryListByIds(String ids){
		try {
			return getSuperDao().queryListByIds(ids);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryListByIds(List<String> ids){
		try {
			return getSuperDao().queryListByIds(ids);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryListByIds(String ...ids){
		try {
			return getSuperDao().queryListByIds(ids);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryList(SearchModel search){
		try {
			return getSuperDao().queryList(search);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public List<E> queryList(PagerModel pagerModel){
		try {
			return getSuperDao().queryList(pagerModel);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	public List<E> queryList(SearchModel search,PagerModel pagerModel){
		try {
			return getSuperDao().queryList(search,pagerModel);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryList(boolean deleteMark,PagerModel pagerModel){
		try {
			return getSuperDao().queryList(deleteMark,pagerModel);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryList(boolean deleteMark){
		try {
			return getSuperDao().queryList(deleteMark);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryList(int deleteMark,PagerModel pagerModel){
		try {
			return getSuperDao().queryList(deleteMark);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryList(int deleteMark){
		try {
			return getSuperDao().queryList(deleteMark);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	
	@Override
	public List<E> queryList(E e) {
		try {
			return getSuperDao().queryList(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryListByExample(Object example){
		try {
			return getSuperDao().queryListByExample(example);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryListByExampleAndRowBounds(Object example,RowBounds rowBounds){
		try {
			return getSuperDao().queryListByExampleAndRowBounds(example, rowBounds);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public List<E> queryList(Object example,PagerModel pagerModel){
		try {
			return getSuperDao().queryList(example,pagerModel);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public int queryCount(E e){
		try {
			return getSuperDao().queryCount(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public int queryCountByExample(Object example){
		try {
			return getSuperDao().queryCountByExample(example);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public int queryCount(){
		try {
			return getSuperDao().queryCount();
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public int queryCount(SearchModel search){
		try {
			return getSuperDao().queryCount(search);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public int queryCount(boolean deleteMark){
		try {
			return getSuperDao().queryCount(deleteMark);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public E queryModel(String key) {
		try {
			return getSuperDao().queryModel(key);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public E queryModel(E e) {
		try {
			return getSuperDao().queryModel(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public E queryModelByExample(Object example){
		try {
			return getSuperDao().queryModelByExample(example);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public boolean addModel(E e) {
		try {
			return getSuperDao().addModel(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public boolean addModelSelective(E e) {
		try {
			return getSuperDao().addModelSelective(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public boolean updateModel(E e) {
		try {
			return getSuperDao().updateModel(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public boolean updateModelSelective(E e) {
		try {
			return getSuperDao().updateModelSelective(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public int updateModelByExample(E e,Object example){
		try {
			return getSuperDao().updateModelByExample(e, example);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public int updateModelSelectiveByExample(E e,Object example){
		try {
			return getSuperDao().updateModelSelectiveByExample(e, example);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public boolean deleteModel(String key) {
		try {
			return getSuperDao().deleteModel(key);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}

	@Override
	public int deleteModel(E e) {
		try {
			return getSuperDao().deleteModel(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public int deleteModelByExample(Object example){
		try {
			return getSuperDao().deleteModelByExample(example);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	@Override
	public boolean deleteFakeByKey(String key){
		try {
			return getSuperDao().deleteFakeByKey(key);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	public boolean applyChange(E e){
		try {
			return getSuperDao().applyChange(e);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	public boolean applyChanges(List<E> list){
		try {
			return getSuperDao().applyChanges(list);
		} catch (DaoBaseException dbe) {
			logger.error(dbe.getMessage());
			throw new DaoBaseRuntimeException(dbe);
		}
	}
	
	public List<E> queryListMP(SearchModel search,PagerModel pagerModel){
		return getSuperDao().queryListMP(search, pagerModel);
	}
	
	public int queryCountMP(SearchModel search){
		return getSuperDao().queryCountMP(search);
	}
	
	public int insertListEx(List<E> list){
		return getSuperDao().insertListEx(list);
	}
}
