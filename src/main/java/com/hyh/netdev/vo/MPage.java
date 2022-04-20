package com.hyh.netdev.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 分页通用类
 *
 * @author hyh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MPage<T> {
    private Integer size;
    private List<T> list;
    
    public MPage(Integer size, List<T> list){
    	this.size = size;
    	this.list = list;
    }
    
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public List<T> getList() {
		return list;
	}
}
