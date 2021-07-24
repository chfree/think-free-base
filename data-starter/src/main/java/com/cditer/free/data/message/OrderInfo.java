package com.cditer.free.data.message;

import com.cditer.free.core.message.data.OrderByEnum;

/**
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2016年5月6日 下午2:36:28
 * @comment 
 */
public class OrderInfo {
	private String property;
	
	private String orderBy;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public OrderInfo(){}
	
	public OrderInfo(String property){
		this.property=property;
		this.orderBy= OrderByEnum.ASC;
	}
	
	public OrderInfo(String property,String orderBy){
		this.property=property;
		this.orderBy=orderBy;
	}
}
