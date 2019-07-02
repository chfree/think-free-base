package com.tennetcn.free.data.message;
/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2017年3月16日 下午8:59:21
 * @comment 
 */
public class SearchItem {
	private String columnName;
	
	private String displayName;
	
	private String tablePrefix;
	
	private String searchValue;
	
	private boolean toSearch;
	
	private boolean displayShow;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public boolean isToSearch() {
		return toSearch;
	}

	public void setToSearch(boolean toSearch) {
		this.toSearch = toSearch;
	}
	
	public boolean isDisplayShow() {
		return displayShow;
	}

	public void setDisplayShow(boolean displayShow) {
		this.displayShow = displayShow;
	}

	public SearchItem(){}
	
	public SearchItem(String columnName,String displayName){
		this.columnName=columnName;
		this.displayName=displayName;
		this.displayShow=true;
	}
	
	public SearchItem(String columnName,boolean displayShow){
		this.columnName=columnName;
		if(!displayShow){
			this.toSearch=true;
		}
		this.displayShow=displayShow;
	}
	
	public SearchItem(String columnName,boolean toSearch,boolean displayShow){
		this.columnName=columnName;
		this.displayShow=displayShow;
		this.toSearch=toSearch;
	}
	
	public SearchItem(String columnName,String displayName,boolean toSearch){
		this.columnName=columnName;
		this.displayName=displayName;
		this.toSearch=toSearch;
		this.displayShow=true;
	}
	
	public SearchItem(String columnName,String displayName,boolean toSearch,boolean displayShow){
		this.columnName=columnName;
		this.displayName=displayName;
		this.toSearch=toSearch;
		this.displayShow=displayShow;
	}
	
	public SearchItem(String columnName,String displayName,boolean toSearch,String tablePrefix){
		this.columnName=columnName;
		this.displayName=displayName;
		this.toSearch=toSearch;
		this.tablePrefix=tablePrefix;
		this.displayShow=true;
	}
	
	public SearchItem(String columnName,String displayName,boolean toSearch,String tablePrefix,boolean displayShow){
		this.columnName=columnName;
		this.displayName=displayName;
		this.toSearch=toSearch;
		this.tablePrefix=tablePrefix;
		this.displayShow=displayShow;
	}
}
