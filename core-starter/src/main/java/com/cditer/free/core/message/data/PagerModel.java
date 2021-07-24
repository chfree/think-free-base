package com.cditer.free.core.message.data;

import lombok.Data;

import java.io.Serializable;


/** 
 * @author  chenghuan
 * @email   79763939@qq.com
 * @comment 
 */

@Data
public class PagerModel implements Serializable {
	/**
	 * 当前页数
	 */
	private int pageIndex;
	
	/**
	 * 每页显示的条数
	 */
	private int pageSize;

	private int currentSize = -1;
	
	public PagerModel(){
		setDefault();
	}
	
	public PagerModel(int pageSize){
		setDefault();
		this.pageSize=pageSize;
	}
	
	public PagerModel(int pageSize, int pageIndex){
		setDefault();
		this.pageSize=pageSize;
		this.pageIndex=pageIndex;
	}
	
	private void setDefault(){
		this.pageSize=15;
		this.pageIndex=1;
	}

	public int getCurrentSize(){
		if(this.currentSize<0){
			return (this.pageIndex - 1) * this.pageSize;
		}
		return this.currentSize;
	}
}
