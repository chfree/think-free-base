package com.cditer.free.quartz.logical.dao.impl;

import com.cditer.free.core.enums.OrderEnum;
import com.cditer.free.core.message.data.PagerModel;
import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.dao.base.impl.SuperDao;
import com.cditer.free.data.utils.SqlExpressionFactory;
import com.cditer.free.quartz.logical.dao.IQuartzTaskDao;
import com.cditer.free.quartz.logical.model.QuartzTask;
import com.cditer.free.quartz.logical.viewmodel.QuartzTaskSearch;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author      auto build code by think
 * @email       chfree001@gmail.com
 * @createtime  2020-02-25 23:21:46
 * @comment     定时任务表
 */

@Component
public class QuartzTaskDaoImpl extends SuperDao<QuartzTask> implements IQuartzTaskDao {
    @Override
    public int queryCountBySearch(QuartzTaskSearch search) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectCount().from(QuartzTask.class);

        appendExpression(sqlExpression,search);

        return queryCount(sqlExpression);
    }

    @Override
    public List<QuartzTask> queryListBySearch(QuartzTaskSearch search, PagerModel pagerModel) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.selectAllFrom(QuartzTask.class)
                     .addOrder("name", OrderEnum.ASC);

        appendExpression(sqlExpression,search);

        return queryList(sqlExpression,pagerModel);
    }

    private void appendExpression(ISqlExpression sqlExpression, QuartzTaskSearch search){
        sqlExpression.andEqNoEmpty("id",search.getId());

        sqlExpression.andNotEqNoEmpty("id",search.getNotId());

        sqlExpression.andEqNoEmpty("name",search.getName());

        sqlExpression.andEqNoEmpty("method_name",search.getMethodName());

        sqlExpression.andEqNoEmpty("bean_name",search.getBeanName());

        sqlExpression.andEqNoEmpty("cron",search.getCron());

        sqlExpression.andEqNoEmpty("parameter",search.getParameter());

        sqlExpression.andEqNoEmpty("description",search.getDescription());

        sqlExpression.andEqNoEmpty("status",search.getStatus());

        sqlExpression.andEqNoEmpty("concurrent",search.getConcurrent());

        sqlExpression.andLikeNoEmpty("name",search.getLikeName());

        sqlExpression.andLikeNoEmpty("method_name",search.getLikeMethodName());

    }
}