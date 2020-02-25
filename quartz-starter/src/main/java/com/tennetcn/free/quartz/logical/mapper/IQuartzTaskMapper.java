package com.tennetcn.free.quartz.logical.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.tennetcn.free.data.dao.base.IMapper;
import com.tennetcn.free.quartz.logical.model.QuartzTask;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:16:34
 * @comment     定时任务表
 */

@Mapper
public interface IQuartzTaskMapper extends IMapper<QuartzTask>{

}