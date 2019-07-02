package com.tennetcn.free.data.message;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年3月16日 下午8:17:39
 * @comment 
 */
public class SearchModel {
	private String searchKey;
	
	private int deleteMark;
	
	private List<SearchItem> searchItemList=new ArrayList<SearchItem>();

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	public int getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(int deleteMark) {
		this.deleteMark = deleteMark;
	}

	public List<SearchItem> getSearchItemList() {
		return searchItemList;
	}

	public void setSearchItemList(List<SearchItem> searchItemList) {
		this.searchItemList = searchItemList;
	}
	
	public void addSearchItem(SearchItem searchItem){
		this.searchItemList.add(searchItem);
	}
	
	public SearchModel(){
		this.deleteMark=-1;
	}
	

	public boolean isSearch() {
		if(searchItemList==null||searchItemList.size()<=0){
			return false;
		}
		return searchItemList.stream().filter(item->item.isToSearch()).count()>0;
	}

	
	
}
