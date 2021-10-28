package com.cditer.free.quartz.logical.dao.impl;

import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.dao.base.impl.SuperDao;
import com.cditer.free.data.utils.SqlExpressionFactory;
import com.cditer.free.quartz.logical.viewmodel.QuartzTaskLogSearch;
import org.springframework.stereotype.Component;
import com.cditer.free.quartz.logical.dao.IQuartzTaskLogDao;
import com.cditer.free.quartz.logical.model.QuartzTaskLog;

import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-29 21:57:16
 * @comment     定时任务日志表
 */

@Component
public class QuartzTaskLogDaoImpl extends SuperDao<QuartzTaskLog> implements IQuartzTaskLogDao{
    @Override
    public int queryCountBySearch(QuartzTaskLogSearch search) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectCount().from(QuartzTaskLog.class);

        appendExpression(sqlExpression,search);

        return queryCount(sqlExpression);
    }

    @Override
    public List<QuartzTaskLog> queryListBySearch(QuartzTaskLogSearch search, PagerModel pagerModel) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(QuartzTaskLog.class)
                     .addOrder("record_time", OrderEnum.DESC);

        appendExpression(sqlExpression,search);

        return queryList(sqlExpression,pagerModel);
    }

    private void appendExpression(ISqlExpression sqlExpression, QuartzTaskLogSearch search){
        sqlExpression.andEqNoEmpty("id",search.getId());

        sqlExpression.andNotEqNoEmpty("id",search.getNotId());

        sqlExpression.andEqNoEmpty("task_name",search.getTaskName());

        sqlExpression.andEqNoEmpty("bean_name",search.getBeanName());

        sqlExpression.andEqNoEmpty("method_name",search.getMethodName());

        sqlExpression.andEqNoEmpty("parameter",search.getParameter());

        sqlExpression.andEqNoEmpty("exec_phase",search.getExecPhase());

        sqlExpression.andEqNoEmpty("exec_id",search.getExecId());

        sqlExpression.andEqNoEmpty("result",search.getResult());

        sqlExpression.andEqNoEmpty("error_message",search.getErrorMessage());

        sqlExpression.andLikeNoEmpty("task_name",search.getLikeTaskName());

        sqlExpression.andLikeNoEmpty("method_name",search.getLikeMethodName());

        if(search.getMsDiffMin()!=null&&search.getMsDiffMin()>=0){
            sqlExpression.andWhere("ms_diff>=#{msDiffMin}").setParam("msDiffMin",search.getMsDiffMin());
        }
        if(search.getMsDiffMax()!=null&&search.getMsDiffMax()>0){
            sqlExpression.andWhere("ms_diff<=#{msDiffMax}").setParam("msDiffMax",search.getMsDiffMax());
        }

        if(search.getRecordTimeStart()!=null){
            sqlExpression.andWhere("record_time>=#{recordTimeStart}").setParam("recordTimeStart",search.getRecordTimeStart());
        }
        if(search.getRecordTimeEnd()!=null){
            sqlExpression.andWhere("record_time<=#{recordTimeEnd}").setParam("recordTimeEnd",search.getRecordTimeEnd());
        }
    }
}