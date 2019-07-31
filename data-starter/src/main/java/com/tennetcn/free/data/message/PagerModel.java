package com.tennetcn.free.data.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import org.apache.ibatis.session.RowBounds;


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

	
	public PagerModel(){
		setDefault();
	}
	
	public PagerModel(int pageSize){
		setDefault();
		this.pageSize=pageSize;
	}
	
	public PagerModel(int pageSize,int pagerIndex){
		setDefault();
		this.pageSize=pageSize;
		this.pageIndex=pagerIndex;
	}
	
	private void setDefault(){
		this.pageSize=15;
		this.pageIndex=1;
	}
	
	
	public RowBounds getRowBounds(){
		return new RowBounds((this.pageIndex-1)*this.pageSize,this.pageSize);
	}

}
