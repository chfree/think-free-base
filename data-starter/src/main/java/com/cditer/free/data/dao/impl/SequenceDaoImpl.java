package com.cditer.free.data.dao.impl;

import com.cditer.free.data.dao.base.ISqlExpression;
import com.cditer.free.data.utils.SqlExpressionFactory;
import com.cditer.free.data.dao.ISequenceDao;
import com.cditer.free.data.dao.base.ISqlExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2020-10-27 18:50
 * @comment
 */

@Component
public class SequenceDaoImpl implements ISequenceDao {

    @Autowired
    ISqlExecutor sqlExecutor;

    @Override
    public int getSeq(String seqName) {
        ISqlExpression sqlExpression = SqlExpressionFactory.createExpression();
        sqlExpression.callFunction("nextval").setFunParam("seqName", seqName);

        return sqlExecutor.queryScalarInt(sqlExpression);
    }
}
