package com.tennetcn.free.data.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.RowBounds;


/** 
 * @author  chenghuan
 * @email   79763939@qq.com
 * @comment 
 */

public class PagerModel {
	/**
	 * 当前页数
	 */
	private int pageIndex;
	
	/**
	 * 每页显示的条数
	 */
	private int pageSize;
	
	/**
	 * 总条数
	 */
	private int totalAmount;
	
	/**
	 * 待选每页条数
	 * 必须是10,20,30 这种格式 
	 * 因为c:forTokens有bug，在tomcat8下，所有重载一个list
	 */
	private String pageOption;
	
	@SuppressWarnings("unused")
	private List<String> pageOptionList;
	
	/**
	 * 界面排序用
	 */
	private List<OrderInfo> orderInfoList=new ArrayList<OrderInfo>();

	/**
	 * 最大页数
	 */
	private int maxResults;
	
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalPage() {
		return totalAmount%pageSize>0?totalAmount/pageSize+1:totalAmount/pageSize;
	}

	public String getPageOption() {
		return pageOption;
	}
	public void setPageOption(String pageOption) {
		this.pageOption = pageOption;
	}
	
	public List<String> getPageOptionList() {
		return Arrays.asList(pageOption.split(","));
	}
	
	public int getMaxResults(){
		maxResults= pageIndex*pageSize<totalAmount?pageIndex*pageSize:totalAmount;
		return maxResults;
	}
	
	public int getFirstResult(){
		return (pageIndex-1)*pageSize;
	}
	
	public int getCurrentSize(){
		return (pageIndex-1)*pageSize;
	}
	
	public List<OrderInfo> getOrderInfoList() {
		return orderInfoList;
	}
	public void setOrderInfoList(List<OrderInfo> orderInfoList) {
		this.orderInfoList = orderInfoList;
	}
	
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
		this.pageOption="10,15,20,30,50,100";
	}
	
	
	public RowBounds getRowBounds(){
		return new RowBounds((this.pageIndex-1)*this.pageSize,this.pageSize);
	}
	public void addDefaultOrder(OrderInfo orderInfo){
		if(orderInfoList!=null&&orderInfoList.size()==0){
			addOrder(orderInfo);
		}
	}
	public void addOrder(OrderInfo orderInfo){
		if(orderInfo==null){return;}
		orderInfoList.add(orderInfo);
	}
}
