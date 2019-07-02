package com.tennetcn.free.data.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.tennetcn.free.data.dao.base.mapper.CommonProvider;
import com.tennetcn.free.data.dao.base.mapper.InsertListExMapper;
import com.tennetcn.free.data.dao.base.mapper.QueryMapper;
import com.tennetcn.free.data.message.ModelBase;

/**
 * @author chenghuan
 * @email 79763939@qq.com
 * @comment
 */

public interface IMapper<E extends ModelBase> extends Mapper<E>, MySqlMapper<E>,InsertListExMapper<E>,QueryMapper<E> {

	@Select(value = "${sql}")
	@ResultType(value = Integer.class)
	public int queryCount(@Param(value = "sql") String sql);


	/**
	 * 根据参数进行查询总数,record可以是Class<?>类型 <br>
	 * 查询条件为属性String类型不为空，其他类型!=null时 <br>
	 * where property = ? and property2 = ? 条件
	 *
	 * @param record
	 * @param <T>
	 * @return
	 */
	@SelectProvider(type = CommonProvider.class, method = "dynamicSQL")
	public <T> int countForMapper(T record);

	/**
	 * 通过Example类来查询count
	 *
	 * @param entityClass
	 * @param example
	 * @param <T>
	 * @return
	 */
	@SelectProvider(type = CommonProvider.class, method = "dynamicSQL")
	public <T> int countByExampleForMapper(Class<T> entityClass,Object example);

	/**
	 * 通过Example删除
	 *
	 * @param entityClass
	 * @param example
	 * @param <T>
	 * @return
	 */
	@DeleteProvider(type = CommonProvider.class, method = "dynamicSQL")
	public <T> int deleteByExampleForMapper(Class<T> entityClass,Object example);

	/**
	 * 通过Example来查询
	 *
	 * @param entityClass
	 * @param example
	 * @param <T>
	 * @return
	 */
	@SelectProvider(type = CommonProvider.class, method = "dynamicSQL")
	public <T> List<Map<String, Object>> selectByExampleForMapper(Class<T> entityClass,Object example);
}
