package com.tennetcn.free.data.utils;

import com.tennetcn.free.data.dao.base.ISqlExpression;
import com.tennetcn.free.data.dao.base.impl.SqlExpression;

/**
 * @author  chenghuan-home
 * @email   79763939@qq.com
 * @comment 
 */

public class SqlExpressionFactory {
	public static ISqlExpression createExpression(){
		return new SqlExpression();
	}
}
