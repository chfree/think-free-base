package com.cditer.free.data.dao.base.interceptor.executor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.cditer.free.data.dao.base.interceptor.IBaseInterceptor;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年2月25日 上午11:16:35
 * @comment 
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class })})
public class ExecutorUpdateInterceptor implements IBaseInterceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("executorUpdateInterceptor");
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if(target instanceof Executor){
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
