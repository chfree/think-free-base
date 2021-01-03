package com.tennetcn.free.data.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.*;

import com.tennetcn.free.data.dao.base.mapper.CommonProvider;
import com.tennetcn.free.data.dao.base.mapper.InsertListExMapper;
import com.tennetcn.free.data.dao.base.mapper.QueryMapper;
import com.tennetcn.free.core.message.data.ModelBase;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.base.select.*;
import tk.mybatis.mapper.common.rowbounds.SelectRowBoundsMapper;

/**
 * @author chenghuan
 * @email 79763939@qq.com
 * @comment
 */

@RegisterMapper
public interface IMapper<E extends ModelBase> extends
		SelectOneMapper<E>,
		SelectMapper<E>,
		SelectCountMapper<E>,
		SelectByPrimaryKeyMapper<E>,
		BaseInsertMapper<E>,
		BaseUpdateMapper<E>,
		BaseDeleteMapper<E>,
		SelectRowBoundsMapper<E>,
		InsertListExMapper<E>,
		QueryMapper<E>,
		Marker {
}
