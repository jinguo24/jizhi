package com.jizhi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PItems implements Serializable {

	private static final long serialVersionUID = 1L;

	private String posKey;//位置
	private List<ItemsValues> data = new ArrayList<ItemsValues>();
	public String getPosKey() {
		return posKey;
	}
	public void setPosKey(String posKey) {
		this.posKey = posKey;
	}
	public List<ItemsValues> getData() {
		return data;
	}
	public void setData(List<ItemsValues> data) {
		this.data = data;
	}
}
