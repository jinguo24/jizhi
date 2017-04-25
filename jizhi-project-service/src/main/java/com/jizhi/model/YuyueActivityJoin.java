package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class YuyueActivityJoin implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String activityId;
	private Date createTime;
	private String phone;
	private int tbindex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
		if (!StringUtils.isEmpty(activityId)) {
			this.tbindex = Math.abs(activityId.hashCode()%100);
		}
	}
	public int getTbindex() {
		return tbindex;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
