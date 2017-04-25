package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class YuyueActivity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Date createTime;
	private int status;
	private int maxAllowed;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMaxAllowed() {
		return maxAllowed;
	}
	public void setMaxAllowed(int maxAllowed) {
		this.maxAllowed = maxAllowed;
	}
}
