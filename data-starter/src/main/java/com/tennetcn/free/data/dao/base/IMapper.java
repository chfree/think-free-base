package com.tennetcn.free.data.dao.base;

import com.tennetcn.free.core.message.data.ModelBase;
import com.tennetcn.free.data.dao.base.mapper.InsertListExMapper;
import com.tennetcn.free.data.dao.base.mapper.QueryMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.base.select.SelectByPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.select.SelectCountMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.base.select.SelectOneMapper;
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
