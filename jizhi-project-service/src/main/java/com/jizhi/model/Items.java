package com.jizhi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Items implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tid;
	private List<ItemsValues> data = new ArrayList<ItemsValues>();
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public List<ItemsValues> getData() {
		return data;
	}
	public void setData(List<ItemsValues> data) {
		this.data = data;
	}
}
