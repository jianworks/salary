package com.csjian.util;

import java.util.List;

public class Pagination {

	public final static int PAGESIZE = 10;
	private int pageSize = PAGESIZE;
	private List items;
	private int totalCount;
	
	private int pageCount = 0;
	private int thisPage = 0;
  
	public Pagination(List items, int totalCount) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setThisPage(0);
	}

	public Pagination(List items, int totalCount, int thisPage) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setThisPage(thisPage);
	}

	public Pagination(List items, int totalCount, int pageSize, int thisPage) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setThisPage(thisPage);
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			pageCount = totalCount / pageSize;
			if (totalCount % pageSize > 0) pageCount++;
		} else {
			this.totalCount = 0;
		}
	}

	public int getThisPage() {
		return thisPage;
	}
	
	public int[] getPageList() {
		int startPage = thisPage - (thisPage % 10);
		int lastPage = startPage+9>this.pageCount-1?this.pageCount-1:startPage+9;
		int[] list = new int[lastPage-startPage+1];
		for (int i=0; i<(lastPage-startPage+1); i++) list[i] = i + startPage;
		return list;
	}
	
	public void setThisPage(int thisPage) {
		this.thisPage = (thisPage > pageCount-1)? (pageCount-1) : thisPage;
		if (this.thisPage < 0) this.thisPage=0;
	}

	public int getPageCount() {
		return this.pageCount;
	}
	
	public boolean hasPrev() {
		if (thisPage>0) return true;
		else return false;		
	}
	
	public boolean hasNext() {
		if (thisPage<this.pageCount-1) return true;
		else return false;		
	}
	
	public boolean hasPrev10() {
		if (thisPage - (thisPage % 10)>9) return true;
		else return false;		
	}
	
	public boolean hasNext10() {
		if (this.pageCount-(thisPage - (thisPage % 10))>9) return true;
		else return false;		
	}
}
