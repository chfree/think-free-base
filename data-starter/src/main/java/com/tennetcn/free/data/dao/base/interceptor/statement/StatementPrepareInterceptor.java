package com.tennetcn.free.data.dao.base.interceptor.statement;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.tennetcn.free.data.dao.base.interceptor.IBaseInterceptor;
import com.tennetcn.free.data.message.ReplaceRule;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年2月25日 上午11:18:10
 * @comment 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class })})
public class StatementPrepareInterceptor implements IBaseInterceptor{
	
	private List<ReplaceRule> replaceRules=new ArrayList<ReplaceRule>();
	
	public List<ReplaceRule> getReplaceRules() {
		return replaceRules;
	}

	public void setReplaceRules(List<ReplaceRule> replaceRules) {
		this.replaceRules = replaceRules;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("statementPrepareInterceptor");
		
		StatementHandler sh=(StatementHandler)invocation.getTarget();
		String sql=sh.getBoundSql().getSql();
		
		System.out.println("sql:"+sql);
		
		/*for (String key : replaceRules.keySet()) {
			sql=(replace(sql, key, replaceRules.get(key), key));
		}*/
		
		System.out.println("sql:"+sql);
		
        return invocation.proceed();
	}
	
	
	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }  
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
	
	

}
