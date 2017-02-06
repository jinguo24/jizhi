package com.jizhi.filter;

import java.io.Serializable;

public class SysUser implements Serializable{

	private static final long serialVersionUID = 1L;

	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
