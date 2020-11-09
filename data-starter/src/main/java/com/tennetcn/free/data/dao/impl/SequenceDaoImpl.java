package com.tennetcn.free.data.dao.impl;

import com.tennetcn.free.data.dao.ISequenceDao;
import com.tennetcn.free.data.dao.base.ISqlExecutor;
import com.tennetcn.free.data.dao.base.ISqlExpression;
import com.tennetcn.free.data.utils.SqlExpressionFactory;
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
        sqlExpression.select("nextval(#{seqName})").setParam("seqName", seqName);

        return sqlExecutor.queryScalarInt(sqlExpression);
    }
}
