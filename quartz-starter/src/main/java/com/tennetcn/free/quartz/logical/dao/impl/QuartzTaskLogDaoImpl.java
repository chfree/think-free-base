package com.tennetcn.free.quartz.logical.dao.impl;

import com.tennetcn.free.data.dao.base.ISqlExpression;
import com.tennetcn.free.core.message.data.PagerModel;
import com.tennetcn.free.data.utils.SqlExpressionFactory;
import org.springframework.stereotype.Component;
import com.tennetcn.free.data.dao.base.impl.SuperDao;
import com.tennetcn.free.quartz.logical.dao.IQuartzTaskLogDao;
import com.tennetcn.free.quartz.logical.model.QuartzTaskLog;
import com.tennetcn.free.quartz.logical.viewmodel.QuartzTaskLogSearch;
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
        sqlExpression.selectAllFrom(QuartzTaskLog.class);

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

    }
}