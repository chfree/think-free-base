package com.tennetcn.free.data.dao.base.interceptor.executor;

import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.tennetcn.free.data.dao.base.interceptor.IBaseInterceptor;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年2月27日 下午8:53:57
 * @comment 
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
			 @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }) })
public class ExecutorQueryInterceptor implements IBaseInterceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("executorQueryInterceptor");
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if(target instanceof Executor){
			Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
