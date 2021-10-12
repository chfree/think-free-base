package com.cditer.free.data.message;

import com.cditer.free.core.enums.OrderEnum;
import lombok.Data;

/**
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2016年5月6日 下午2:36:28
 * @comment 
 */
@Data
public class OrderInfo {
	private String property;
	
	private OrderEnum orderBy;

	
	public OrderInfo(){}
	
	public OrderInfo(String property){
		this.property=property;
		this.orderBy= OrderEnum.ASC;
	}
	
	public OrderInfo(String property,OrderEnum orderBy){
		this.property=property;
		this.orderBy=orderBy;
	}
}
