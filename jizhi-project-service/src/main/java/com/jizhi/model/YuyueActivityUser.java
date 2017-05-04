package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class YuyueActivityUser implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String phone;
	private String activityId;
	private YuyueActivity activity;
	private Date createTime;
	private int tbindex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
		if (!StringUtils.isEmpty(phone)) {
			this.tbindex = (int)(Long.parseLong(phone)%100);
		}
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
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
	public YuyueActivity getActivity() {
		return activity;
	}
	public void setActivity(YuyueActivity activity) {
		this.activity = activity;
	}
}
