package com.cditer.free.data.dao.base;

import com.cditer.free.core.message.data.ModelBase;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.message.DaoBaseRuntimeException;
import com.cditer.free.data.message.OrderInfo;
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
	 * 获得mapper
	 * @return
	 */
	IMapper<E> getMapper();

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
	 * 获得当前泛型参数的属性的@Idd第一个值
	 * @return
	 */
	String getDbFirstColumnKey();
	
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
	
	int queryCount() throws DaoBaseRuntimeException;
	
	int queryCount(E e) throws DaoBaseRuntimeException;
	
	E queryModel(String key) throws DaoBaseRuntimeException;
	
	E queryModel(E e) throws DaoBaseRuntimeException;
	
	boolean addModel(E e) throws DaoBaseRuntimeException;
	
	boolean addModelSelective(E e) throws DaoBaseRuntimeException;
	
	boolean updateModel(E e)  throws DaoBaseRuntimeException;
	
	boolean updateModelSelective(E e) throws DaoBaseRuntimeException;
	
	boolean deleteModel(String key) throws DaoBaseRuntimeException;
	
	boolean deleteModel(E e) throws DaoBaseRuntimeException;
	
	boolean applyChange(E e) throws DaoBaseRuntimeException;
	
	boolean applyChanges(List<E> list) throws DaoBaseRuntimeException;
	
	//sqlMapper自带的方法封装
	int update(String sql) throws DaoBaseRuntimeException;
	
	int update(String sql,Object params) throws DaoBaseRuntimeException;
	
	int delete(String sql) throws DaoBaseRuntimeException;
	
	int delete(String sql,Object params) throws DaoBaseRuntimeException;
	
	int insert(String sql) throws DaoBaseRuntimeException;
	
	int insert(String sql,Object params) throws DaoBaseRuntimeException;
	
	int queryCount(String sql, Object value) throws DaoBaseRuntimeException;

	//自定义
	E queryModel(ISqlExpression sqlExpression);

	Map<String, Object> queryModelEx(ISqlExpression sqlExpression);
	
	<T> T queryModel(ISqlExpression sqlExpression,Class<T> resultType);
	
	List<E> queryList(ISqlExpression sqlExpression);
	
	<T> List<T> queryList(ISqlExpression sqlExpression,Class<T> resultType);
	
	List<E> queryList(ISqlExpression sqlExpression,PagerModel pagerModel);

	List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression);

	List<Map<String, Object>> queryListEx(ISqlExpression sqlExpression,PagerModel pagerModel);
	
	<T> List<T> queryList(ISqlExpression sqlExpression,PagerModel pagerModel,Class<T> resultType);
	
	int queryCount(ISqlExpression sqlExpression);
	
	int update(ISqlExpression sqlExpression);
	
	int delete(ISqlExpression sqlExpression);

	int deleteByIds(List<String> ids);

	int deleteByIds(String ...ids);

	int deleteByIds(String ids);
	
	int insert(ISqlExpression sqlExpression);
	
	double queryScalarDouble(ISqlExpression sqlExpression);

	int queryScalarInt(ISqlExpression sqlExpression);
	
	String queryScalar(ISqlExpression sqlExpression);
	
	int insertListEx(List<E> list);

	int insertListEx(List<E> list, int batchSize);

	int batchInsertList(List<E> list);

	int batchInsertList(List<E> list,int batchSize);

	int batchUpdateList(List<E> list);

	int batchInsertSelectiveList(List<E> list);

	int batchInsertSelectiveList(List<E> list,int batchSize);

	int batchUpdateSelectiveList(List<E> list);
}
