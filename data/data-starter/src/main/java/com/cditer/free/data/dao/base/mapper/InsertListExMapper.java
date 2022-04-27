package com.cditer.free.data.dao.base.mapper;

import org.apache.ibatis.annotations.InsertProvider;

import java.util.List;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2016年5月15日 下午1:33:52
 * @comment 
 */
public interface InsertListExMapper<T>  {
	@InsertProvider(type = InsertListExProvider.class, method = "dynamicSQL")
    int insertListEx(List<? extends T> recordList);
}
