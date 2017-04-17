package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class YuyueActivity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String openId;
	private String name;
	private String phone;
	private Date createTime;
	private Date deadLineTime;
	private int yeadId;
	private int tbindex;
	private int status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
		if (!StringUtils.isEmpty(openId)) {
			this.tbindex = Math.abs(openId.hashCode()%100);
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getYeadId() {
		return yeadId;
	}
	public void setYeadId(int yeadId) {
		this.yeadId = yeadId;
	}
	public int getTbindex() {
		return tbindex;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getDeadLineTime() {
		return deadLineTime;
	}
	public void setDeadLineTime(Date deadLineTime) {
		this.deadLineTime = deadLineTime;
	}
}
