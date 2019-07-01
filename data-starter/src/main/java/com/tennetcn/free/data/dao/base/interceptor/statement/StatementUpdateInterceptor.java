package com.tennetcn.free.data.dao.base.interceptor.statement;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.tennetcn.free.data.dao.base.interceptor.IBaseInterceptor;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年2月27日 下午8:34:14
 * @comment 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "update", args = {Statement.class })})
public class StatementUpdateInterceptor implements IBaseInterceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("statementUpdateInterceptor");
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if(target instanceof StatementHandler){
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
