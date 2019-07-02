package com.tennetcn.free.data.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.tennetcn.free.data.message.DaoBaseException;
import com.tennetcn.free.data.message.ModelBase;
import com.tennetcn.free.data.message.OrderInfo;
import com.tennetcn.free.data.message.PagerModel;
import com.tennetcn.free.data.message.SearchModel;

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
	
	public List<E> queryList() throws DaoBaseException;
	
	public List<E> queryListByIds(String ids) throws DaoBaseException;
	
	public List<E> queryListByIds(List<String> ids) throws DaoBaseException;
	
	public List<E> queryListByIds(String ...ids) throws DaoBaseException;
	
	public List<E> queryList(SearchModel search) throws DaoBaseException;
	
	public List<E> queryList(PagerModel pagerModel) throws DaoBaseException;
	
	public List<E> queryList(SearchModel search,PagerModel pagerModel) throws DaoBaseException;
	
	public List<E> queryList(boolean deleteMark,PagerModel pagerModel) throws DaoBaseException;
	
	public List<E> queryList(boolean deleteMark) throws DaoBaseException;
	
	public List<E> queryList(int deleteMark,PagerModel pagerModel) throws DaoBaseException;
	
	public List<E> queryList(int deleteMark) throws DaoBaseException;
	
	public List<E> queryList(E e) throws DaoBaseException;
	
	public List<E> queryListByExample(Object example) throws DaoBaseException;
	
	public List<E> queryList(Object example,PagerModel pagerModel) throws DaoBaseException;
	
	public List<E> queryListByExampleAndRowBounds(Object example,RowBounds rowBounds) throws DaoBaseException;
	
	public int queryCount() throws DaoBaseException;
	
	public int queryCount(SearchModel search) throws DaoBaseException;
	
	public int queryCount(boolean deleteMark) throws DaoBaseException;
	
	public int queryCount(E e) throws DaoBaseException;
	
	public int queryCountByExample(Object example) throws DaoBaseException;
	
	public E queryModel(String key) throws DaoBaseException;
	
	public E queryModel(E e) throws DaoBaseException;
	
	public E queryModelByExample(Object example) throws DaoBaseException;
	
	public boolean addModel(E e) throws DaoBaseException;
	
	public boolean addModelSelective(E e) throws DaoBaseException;
	
	public boolean updateModel(E e)  throws DaoBaseException;
	
	public int updateModelByExample(E e,Object example)  throws DaoBaseException;
	
	public boolean updateModelSelective(E e) throws DaoBaseException;
	
	public int updateModelSelectiveByExample(E e,Object example)  throws DaoBaseException;
	
	public boolean deleteModel(String key) throws DaoBaseException;
	
	public int deleteModel(E e) throws DaoBaseException;
	
	public int deleteModelByExample(Object example) throws DaoBaseException;
	
	public boolean deleteFakeByKey(String key) throws DaoBaseException;
	
	public boolean applyChange(E e) throws DaoBaseException;
	
	public boolean applyChanges(List<E> list) throws DaoBaseException;
	
	//sqlMapper自带的方法封装
	public int update(String sql) throws DaoBaseException;
	
	public int update(String sql,Object params) throws DaoBaseException;
	
	public int delete(String sql) throws DaoBaseException;
	
	public int delete(String sql,Object params) throws DaoBaseException;
	
	public int insert(String sql) throws DaoBaseException;
	
	public int insert(String sql,Object params) throws DaoBaseException;
	
	public List<Map<String, Object>> selectList(String sql) throws DaoBaseException;
	
	public <T> List<T> selectList(String sql, Class<T> resultType) throws DaoBaseException;
	
	public List<Map<String, Object>> selectList(String sql, Object value) throws DaoBaseException;
	
	public List<Map<String, Object>> selectListEx(String sql, Object value,RowBounds rowBounds) throws DaoBaseException;
	
	public <T> List<T> selectList(String sql, Object value, Class<T> resultType) throws DaoBaseException;
	
	public Map<String, Object> selectOne(String sql) throws DaoBaseException;
	
	public <T> T selectOne(String sql, Class<T> resultType) throws DaoBaseException;
	
	public Map<String, Object> selectOne(String sql, Object value) throws DaoBaseException;
	
	public <T> T selectOne(String sql, Object value, Class<T> resultType) throws DaoBaseException;
	
	public int queryCount(String sql, Object value) throws DaoBaseException;
	
	public List<E> selectList(String sql,Object value,RowBounds rowBounds) throws DaoBaseException;
	
	public <T> List<T> selectList(String sql,RowBounds rowBounds, Class<T> resultType) throws DaoBaseException;
	
	public <T> List<T> selectList(String sql, Object value,RowBounds rowBounds,Class<T> resultType) throws DaoBaseException;
	
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
