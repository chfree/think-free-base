package com.tennetcn.free.data.dao.base.interceptor.tools;

import java.text.DateFormat;
import java.util.*;

import com.tennetcn.free.core.utils.SpringContextUtils;
import com.tennetcn.free.data.boot.autoconfig.DataBootConfig;
import com.tennetcn.free.data.dao.base.interceptor.annotation.MyBatisPluginRegister;
import com.tennetcn.free.data.dao.base.interceptor.handler.ISqlExecInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tennetcn.free.core.utils.SystemClockUtils;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年2月27日 下午7:19:57
 * @comment 
 */

@Slf4j
@MyBatisPluginRegister
@Intercepts({
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
			RowBounds.class, ResultHandler.class }),
	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class PerformanceInterceptor implements Interceptor {

	@Autowired
	DataBootConfig dataBootConfig;

	/**
	 * SQL 执行最大时长，超过指定时间则进行error输出，有助于发现问题。
	 */
	private long maxTime = 0;

	public Object intercept(Invocation invocation) throws Throwable {
		if(!dataBootConfig.isPrintSql()){
			return invocation.proceed();
		}
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameterObject = invocation.getArgs()[1];
		
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
		Configuration configuration = mappedStatement.getConfiguration();
		
		String sql=getSql(configuration, boundSql, boundSql.getSql());
		
		String statementId = mappedStatement.getId();
		long start = SystemClockUtils.now();
		Object result = invocation.proceed();
		long end = SystemClockUtils.now();
		long timing = end - start;
		

		StringBuilder builder = new StringBuilder();
		builder.append("\n\n");
		builder.append("Time：" + timing +" ms");
		builder.append("\n");
		builder.append("ID：" + statementId);
		builder.append("\n");
		builder.append("Execute SQL：" + sql);
		builder.append("\n");

		String info = builder.toString();
		if (getMaxTime() >= 1 && timing > getMaxTime()) {
			log.error(info);
		}else{
			log.info(info);
		}

		try {
			execCall(statementId, timing, sql);
		}catch (Exception ex){
			log.error("回调ISqlExecInterceptor的实例出错",ex);
		}
		return result;
	}

	private void execCall(String statementId,long timing,String sql){
		Map<String,ISqlExecInterceptor> maps = SpringContextUtils.getCurrentContext().getBeansOfType(ISqlExecInterceptor.class);
		if(maps==null||maps.isEmpty()){
			return;
		}
		for (String key: maps.keySet()) {
			ISqlExecInterceptor sqlExecInterceptor = maps.get(key);

			sqlExecInterceptor.execCall(timing,statementId,sql);
		}
	}

	public static String getSql(Configuration configuration, BoundSql boundSql, String sql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		sql = sql.replaceAll("[\\s]+", " ");
		if (parameterMappings != null && parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}
		return sql;
	}

	private static String getParameterValue(Object obj) {
		String value;
		if (obj instanceof String) {
			value = obj != null ? "'" + obj.toString() + "'" : "''";
		} else if (obj instanceof Date) {
			if (obj instanceof java.sql.Date) {
				value = obj != null ? "'" + obj.toString() + "'" : "''";
			} else {
				DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT,
						Locale.CHINA);
				value = obj != null ? "'" + formatter.format(obj) + "'" : "''";
			}
		} else {
			value = obj != null ? obj.toString() : "";
		}
		return value;
	}

	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties prop) {
		String maxTime = prop.getProperty("maxTime");
		if (!StringUtils.isEmpty(maxTime)) {
			this.maxTime = Long.parseLong(maxTime);
		}
	}

	public long getMaxTime() {
		return maxTime == 0 ? dataBootConfig.getSqlExecMaxTime() : maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}


}
