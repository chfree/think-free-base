package com.tennetcn.free.quartz.logical.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.tennetcn.free.data.dao.base.IMapper;
import com.tennetcn.free.quartz.logical.model.QuartzTaskLog;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 22:12:26
 * @comment     定时任务日志表
 */

@Mapper
public interface IQuartzTaskLogMapper extends IMapper<QuartzTaskLog>{

}