package com.cditer.free.quartz.logical.mapper;

import com.cditer.free.data.dao.base.IMapper;
import org.apache.ibatis.annotations.Mapper;
import com.cditer.free.quartz.logical.model.QuartzTaskLog;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 22:12:26
 * @comment     定时任务日志表
 */

@Mapper
public interface IQuartzTaskLogMapper extends IMapper<QuartzTaskLog> {

}