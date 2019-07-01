package com.tennetcn.free.data.message;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年9月27日 上午10:38:00
 * @comment 
 */

public class ReplaceRule {
	private String replaceKey;
	
	private String column;
	
	private String valueScope;
	
	private String scopeKey;

	public String getReplaceKey() {
		return replaceKey;
	}

	public void setReplaceKey(String replaceKey) {
		this.replaceKey = replaceKey;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getValueScope() {
		return valueScope;
	}

	public void setValueScope(String valueScope) {
		this.valueScope = valueScope;
	}

	public String getScopeKey() {
		return scopeKey;
	}

	public void setScopeKey(String scopeKey) {
		this.scopeKey = scopeKey;
	}
}
