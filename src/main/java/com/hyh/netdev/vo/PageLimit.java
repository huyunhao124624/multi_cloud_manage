package com.hyh.netdev.vo;

/**
 * @author Albumen/Chongsaid
 */
public class PageLimit {
    
	private Integer page;
    private Integer limit;
    
    public PageLimit(Integer page, Integer limit ) {
    	this.page = page;
    	this.limit = limit;
    }
    
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
