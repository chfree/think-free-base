package com.tennetcn.free.data.dao.base.interceptor.tools;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

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
import org.springframework.util.StringUtils;

import com.tennetcn.free.core.utils.SystemClockUtils;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年2月27日 下午7:19:57
 * @comment 
 */
@Intercepts({
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
			RowBounds.class, ResultHandler.class }),
	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class PerformanceInterceptor implements Interceptor {

	/**

	 * SQL 执行最大时长，超过自动停止运行，有助于发现问题。

	 */
	private long maxTime = 0;

	public Object intercept(Invocation invocation) throws Throwable {
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
		
		
		if (maxTime >= 1 && timing > maxTime) {
			System.err.println(" Time：" + timing + " ms" + " - ID：" + statementId + "\n Execute SQL：" + sql + "\n");
			//throw new DaoBaseRuntimeException(" The SQL execution time is too large, please optimize ! ");
		}
		return result;
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
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}


}
