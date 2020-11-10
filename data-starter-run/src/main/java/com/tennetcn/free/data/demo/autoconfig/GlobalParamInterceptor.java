package com.tennetcn.free.data.demo.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

/**
 * 非金融Mybatis全局变量设值
 * <p>ybatis_PageHelper
 *
 * @author chenhuan/yuangang
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "parameterize", args = {Statement.class}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        }
)
@Slf4j
public class GlobalParamInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(invocation.getTarget() instanceof StatementHandler ) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            BoundSql boundSql = statementHandler.getBoundSql();

            boundSql.setAdditionalParameter("glbName","cheng");
        }else if (invocation.getTarget() instanceof Executor) {
            invokeUpdate(invocation);
        }
        return invocation.proceed();

    }

    private void invokeUpdate(Invocation invocation) throws Throwable{
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];

        // 获取第一个参数
        Object parameter = args[1];
        if(parameter == null) {
            parameter = new MapperMethod.ParamMap();
        }

        if(parameter instanceof MapperMethod.ParamMap){
            ((MapperMethod.ParamMap) parameter).put("glbName", "cheng");
        }else{
            System.out.println("parameter");
        }

    }

    private void invokeSetParameter(Invocation invocation) throws Throwable{
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();

        PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];

        // 反射获取 BoundSql 对象，此对象包含生成的sql和sql的参数map映射
        Field boundSqlField = parameterHandler.getClass().getDeclaredField("boundSql");
        boundSqlField.setAccessible(true);
        BoundSql boundSql = (BoundSql) boundSqlField.get(parameterHandler);

        System.out.println("invokeSetParameter");

        boundSql.setAdditionalParameter("glbName","cheng");
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
