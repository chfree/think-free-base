package com.tennetcn.free.data.dao.base;

import com.tennetcn.free.data.message.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 获得当前泛型参数的@Table的name名称
	 * @return
	 */
	public String getTableName();
	
	/**
	 * 根据类型获取TableName
	 * @param classType
	 * @return
	 */
	public <T extends ModelBase> String getTableName(Class<T> classType);
	
	/**
	 * 获得当前泛型参数的属性的@Id值
	 * @return
	 */
	public String getPrimaryKey();
	
	/**
	 * 取得实体类的的排序信息集合
	 * @return
	 */
	public List<OrderInfo> getOrderInfoList();
	
	public List<E> queryList() throws DaoBaseRuntimeException;
	
	public List<E> queryListByIds(String ids) throws DaoBaseRuntimeException;
	
	public List<E> queryListByIds(List<String> ids) throws DaoBaseRuntimeException;
	
	public List<E> queryListByIds(String ...ids) throws DaoBaseRuntimeException;
	
	public List<E> queryList(SearchModel search) throws DaoBaseRuntimeException;
	
	public List<E> queryList(PagerModel pagerModel) throws DaoBaseRuntimeException;
	
	public List<E> queryList(SearchModel search,PagerModel pagerModel) throws DaoBaseRuntimeException;
	
	public List<E> queryList(boolean deleteMark,PagerModel pagerModel) throws DaoBaseRuntimeException;
	
	public List<E> queryList(boolean deleteMark) throws DaoBaseRuntimeException;
	
	public List<E> queryList(int deleteMark,PagerModel pagerModel) throws DaoBaseRuntimeException;
	
	public List<E> queryList(int deleteMark) throws DaoBaseRuntimeException;
	
	public List<E> queryList(E e) throws DaoBaseRuntimeException;
	
	public List<E> queryListByExample(Object example) throws DaoBaseRuntimeException;
	
	public List<E> queryList(Object example,PagerModel pagerModel) throws DaoBaseRuntimeException;
	
	public List<E> queryListByExampleAndRowBounds(Object example,RowBounds rowBounds) throws DaoBaseRuntimeException;
	
	public int queryCount() throws DaoBaseRuntimeException;
	
	public int queryCount(SearchModel search) throws DaoBaseRuntimeException;
	
	public int queryCount(boolean deleteMark) throws DaoBaseRuntimeException;
	
	public int queryCount(E e) throws DaoBaseRuntimeException;
	
	public int queryCountByExample(Object example) throws DaoBaseRuntimeException;
	
	public E queryModel(String key) throws DaoBaseRuntimeException;
	
	public E queryModel(E e) throws DaoBaseRuntimeException;
	
	public E queryModelByExample(Object example) throws DaoBaseRuntimeException;
	
	public boolean addModel(E e) throws DaoBaseRuntimeException;
	
	public boolean addModelSelective(E e) throws DaoBaseRuntimeException;
	
	public boolean updateModel(E e)  throws DaoBaseRuntimeException;
	
	public int updateModelByExample(E e,Object example)  throws DaoBaseRuntimeException;
	
	public boolean updateModelSelective(E e) throws DaoBaseRuntimeException;
	
	public int updateModelSelectiveByExample(E e,Object example)  throws DaoBaseRuntimeException;
	
	public boolean deleteModel(String key) throws DaoBaseRuntimeException;
	
	public int deleteModel(E e) throws DaoBaseRuntimeException;
	
	public int deleteModelByExample(Object example) throws DaoBaseRuntimeException;
	
	public boolean deleteFakeByKey(String key) throws DaoBaseRuntimeException;
	
	public boolean applyChange(E e) throws DaoBaseRuntimeException;
	
	public boolean applyChanges(List<E> list) throws DaoBaseRuntimeException;
	
	//sqlMapper自带的方法封装
	public int update(String sql) throws DaoBaseRuntimeException;
	
	public int update(String sql,Object params) throws DaoBaseRuntimeException;
	
	public int delete(String sql) throws DaoBaseRuntimeException;
	
	public int delete(String sql,Object params) throws DaoBaseRuntimeException;
	
	public int insert(String sql) throws DaoBaseRuntimeException;
	
	public int insert(String sql,Object params) throws DaoBaseRuntimeException;
	
	public List<Map<String, Object>> selectList(String sql) throws DaoBaseRuntimeException;
	
	public <T> List<T> selectList(String sql, Class<T> resultType) throws DaoBaseRuntimeException;
	
	public List<Map<String, Object>> selectList(String sql, Object value) throws DaoBaseRuntimeException;
	
	public List<Map<String, Object>> selectListEx(String sql, Object value,RowBounds rowBounds) throws DaoBaseRuntimeException;
	
	public <T> List<T> selectList(String sql, Object value, Class<T> resultType) throws DaoBaseRuntimeException;
	
	public Map<String, Object> selectOne(String sql) throws DaoBaseRuntimeException;
	
	public <T> T selectOne(String sql, Class<T> resultType) throws DaoBaseRuntimeException;
	
	public Map<String, Object> selectOne(String sql, Object value) throws DaoBaseRuntimeException;
	
	public <T> T selectOne(String sql, Object value, Class<T> resultType) throws DaoBaseRuntimeException;
	
	public int queryCount(String sql, Object value) throws DaoBaseRuntimeException;
	
	public List<E> selectList(String sql,Object value,RowBounds rowBounds) throws DaoBaseRuntimeException;
	
	public <T> List<T> selectList(String sql,RowBounds rowBounds, Class<T> resultType) throws DaoBaseRuntimeException;
	
	public <T> List<T> selectList(String sql, Object value,RowBounds rowBounds,Class<T> resultType) throws DaoBaseRuntimeException;
	
	//自定义
	public E queryModel(ISqlExpression sqlExpression);
	
	public <T> T queryModel(ISqlExpression sqlExpression,Class<T> resultType);
	
	public List<E> queryList(ISqlExpression sqlExpression);
	
	public List<E> queryList(ISqlExpression sqlExpression,SearchModel search);
	
	public <T> List<T> queryList(ISqlExpression sqlExpression,Class<T> resultType);
	
	public <T> List<T> queryList(ISqlExpression sqlExpression,SearchModel search,Class<T> resultType);
	
	public List<E> queryList(ISqlExpression sqlExpression,PagerModel pagerModel);
	
	public List<E> queryList(ISqlExpression sqlExpression,SearchModel search,PagerModel pagerModel);
	
	public List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression,PagerModel pagerModel);
	
	public List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression,SearchModel search,PagerModel pagerModel);
	
	public <T> List<T> queryList(ISqlExpression sqlExpression,PagerModel pagerModel,Class<T> resultType);
	
	public <T> List<T> queryList(ISqlExpression sqlExpression,SearchModel search,PagerModel pagerModel,Class<T> resultType);
	
	public int queryCount(ISqlExpression sqlExpression);
	
	public int queryCount(ISqlExpression sqlExpression,SearchModel search);
	
	public int update(ISqlExpression sqlExpression);
	
	public int delete(ISqlExpression sqlExpression);
	
	public int insert(ISqlExpression sqlExpression);
	
	public double querySum(ISqlExpression sqlExpression);
	
	public double queryAggreg(ISqlExpression sqlExpression);
	
	public String queryScalar(ISqlExpression sqlExpression);
	
	public List<E> queryListMP(SearchModel search,PagerModel pagerModel);
	
	public int queryCountMP(SearchModel search);
	
	public int insertListEx(List<E> list);
}
