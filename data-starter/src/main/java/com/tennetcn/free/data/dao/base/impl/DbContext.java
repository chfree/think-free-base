package com.tennetcn.free.data.dao.base.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.tennetcn.free.data.message.ModelBase;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @comment 
 */

public abstract class DbContext<E extends ModelBase> {
	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public DbContext() {
		Type genType = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		this.entityClass = (Class<E>) params[0];	
	}
	
	private SqlSessionFactory sqlSessionFactory;

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	@Resource(name="sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	private DruidDataSource dataSource;

	public DruidDataSource getDataSource() {
		return dataSource;
	}
	
	@Resource(name="dataSource")
	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
}
