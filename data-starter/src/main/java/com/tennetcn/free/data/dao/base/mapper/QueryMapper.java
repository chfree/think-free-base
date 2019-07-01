package com.tennetcn.free.data.dao.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tennetcn.free.data.message.PagerModel;
import com.tennetcn.free.data.message.SearchModel;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2016年5月15日 下午1:33:52
 * @comment 
 */
public interface QueryMapper<T>  {
	public List<T> queryListMP(@Param("search")SearchModel search,@Param("pager")PagerModel pagerModel);
	
	public int queryCountMP(@Param("search")SearchModel search);
}
