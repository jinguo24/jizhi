package com.jizhi.model;

import java.io.Serializable;

public class TongjiItems implements Serializable{

	private static final long serialVersionUID = 1L;

	//配置项id
	private int id;
	//配置项值
	private Double value;
	//统计的次数
	private int counts;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
}
