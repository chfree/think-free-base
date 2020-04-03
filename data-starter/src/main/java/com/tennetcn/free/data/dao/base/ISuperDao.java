package com.tennetcn.free.data.dao.base;

import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.data.message.DaoBaseRuntimeException;
import com.tennetcn.free.core.message.data.ModelBase;
import com.tennetcn.free.data.message.OrderInfo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * @author chenghuan
 * @email 79763939@qq.com
 * @comment
 */

public interface ISuperDao<E extends ModelBase> {
	/**
	 * 获得当前泛型参数的类型名称
	 * 
	 * @return
	 */
	String getModelName();
	
	/**
	 * 获得当前泛型参数的@Table的name名称
	 * @return
	 */
	String getTableName();
	
	/**
	 * 根据类型获取TableName
	 * @param classType
	 * @return
	 */
	<T extends ModelBase> String getTableName(Class<T> classType);
	
	/**
	 * 获得当前泛型参数的属性的@Id值
	 * @return
	 */
	String getPrimaryKey();
	
	/**
	 * 取得实体类的的排序信息集合
	 * @return
	 */
	List<OrderInfo> getOrderInfoList();
	
	List<E> queryList() throws DaoBaseRuntimeException;
	
	List<E> queryListByIds(String ids) throws DaoBaseRuntimeException;
	
	List<E> queryListByIds(List<String> ids) throws DaoBaseRuntimeException;
	
	List<E> queryListByIds(String ...ids) throws DaoBaseRuntimeException;
	
	List<E> queryList(PagerModel pagerModel) throws DaoBaseRuntimeException;
	
	List<E> queryList(E e) throws DaoBaseRuntimeException;
	
	List<E> queryListByExample(Object example) throws DaoBaseRuntimeException;
	
	List<E> queryList(Object example, PagerModel pagerModel) throws DaoBaseRuntimeException;
	
	List<E> queryList(Object example,RowBounds rowBounds) throws DaoBaseRuntimeException;
	
	int queryCount() throws DaoBaseRuntimeException;
	
	int queryCount(boolean deleteMark) throws DaoBaseRuntimeException;
	
	int queryCount(E e) throws DaoBaseRuntimeException;
	
	int queryCountByExample(Object example) throws DaoBaseRuntimeException;
	
	E queryModel(String key) throws DaoBaseRuntimeException;
	
	E queryModel(E e) throws DaoBaseRuntimeException;
	
	E queryModelByExample(Object example) throws DaoBaseRuntimeException;
	
	boolean addModel(E e) throws DaoBaseRuntimeException;
	
	boolean addModelSelective(E e) throws DaoBaseRuntimeException;
	
	boolean updateModel(E e)  throws DaoBaseRuntimeException;
	
	int updateModelByExample(E e,Object example)  throws DaoBaseRuntimeException;
	
	boolean updateModelSelective(E e) throws DaoBaseRuntimeException;
	
	int updateModelSelectiveByExample(E e,Object example)  throws DaoBaseRuntimeException;
	
	boolean deleteModel(String key) throws DaoBaseRuntimeException;
	
	int deleteModel(E e) throws DaoBaseRuntimeException;
	
	int deleteModelByExample(Object example) throws DaoBaseRuntimeException;
	
	boolean deleteFakeByKey(String key) throws DaoBaseRuntimeException;
	
	boolean applyChange(E e) throws DaoBaseRuntimeException;
	
	boolean applyChanges(List<E> list) throws DaoBaseRuntimeException;
	
	//sqlMapper自带的方法封装
	int update(String sql) throws DaoBaseRuntimeException;
	
	int update(String sql,Object params) throws DaoBaseRuntimeException;
	
	int delete(String sql) throws DaoBaseRuntimeException;
	
	int delete(String sql,Object params) throws DaoBaseRuntimeException;
	
	int insert(String sql) throws DaoBaseRuntimeException;
	
	int insert(String sql,Object params) throws DaoBaseRuntimeException;
	
	List<Map<String, Object>> selectList(String sql) throws DaoBaseRuntimeException;
	
	<T> List<T> selectList(String sql, Class<T> resultType) throws DaoBaseRuntimeException;
	
	List<Map<String, Object>> selectList(String sql, Object value) throws DaoBaseRuntimeException;
	
	List<Map<String, Object>> selectListEx(String sql, Object value,RowBounds rowBounds) throws DaoBaseRuntimeException;
	
	<T> List<T> selectList(String sql, Object value, Class<T> resultType) throws DaoBaseRuntimeException;
	
	Map<String, Object> selectOne(String sql) throws DaoBaseRuntimeException;
	
	<T> T selectOne(String sql, Class<T> resultType) throws DaoBaseRuntimeException;
	
	Map<String, Object> selectOne(String sql, Object value) throws DaoBaseRuntimeException;
	
	<T> T selectOne(String sql, Object value, Class<T> resultType) throws DaoBaseRuntimeException;
	
	int queryCount(String sql, Object value) throws DaoBaseRuntimeException;
	
	List<E> selectList(String sql,Object value,RowBounds rowBounds) throws DaoBaseRuntimeException;
	
	<T> List<T> selectList(String sql,RowBounds rowBounds, Class<T> resultType) throws DaoBaseRuntimeException;
	
	<T> List<T> selectList(String sql, Object value,RowBounds rowBounds,Class<T> resultType) throws DaoBaseRuntimeException;
	
	//自定义
	E queryModel(ISqlExpression sqlExpression);
	
	<T> T queryModel(ISqlExpression sqlExpression,Class<T> resultType);
	
	List<E> queryList(ISqlExpression sqlExpression);
	
	<T> List<T> queryList(ISqlExpression sqlExpression,Class<T> resultType);
	
	List<E> queryList(ISqlExpression sqlExpression,PagerModel pagerModel);
	
	List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression,PagerModel pagerModel);
	
	<T> List<T> queryList(ISqlExpression sqlExpression,PagerModel pagerModel,Class<T> resultType);
	
	int queryCount(ISqlExpression sqlExpression);
	
	int update(ISqlExpression sqlExpression);
	
	int delete(ISqlExpression sqlExpression);
	
	int insert(ISqlExpression sqlExpression);
	
	double querySum(ISqlExpression sqlExpression);
	
	double queryAggreg(ISqlExpression sqlExpression);
	
	String queryScalar(ISqlExpression sqlExpression);
	
	int insertListEx(List<E> list);
}
