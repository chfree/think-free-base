package com.cditer.free.quartz.logical.mapper;

import com.cditer.free.data.dao.base.IMapper;
import com.cditer.free.quartz.logical.model.QuartzTask;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:16:34
 * @comment     定时任务表
 */

@Mapper
public interface IQuartzTaskMapper extends IMapper<QuartzTask> {

}