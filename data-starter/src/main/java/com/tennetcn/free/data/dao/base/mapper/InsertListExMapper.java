package com.tennetcn.free.data.dao.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2016年5月15日 下午1:33:52
 * @comment 
 */
public interface InsertListExMapper<T>  {
	@InsertProvider(type = SpecialExProvider.class, method = "dynamicSQL")
    public int insertListEx(List<T> recordList);
}
