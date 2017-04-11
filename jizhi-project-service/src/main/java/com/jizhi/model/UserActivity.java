package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class UserActivity implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String phone;
	private String activityId;
	private Activity activity;
	private Date activityCreatTime;
	private String ownerPhone;
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
			this.tbindex = (int) (Long.parseLong(phone)%10);
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
	public Date getActivityCreatTime() {
		return activityCreatTime;
	}
	public void setActivityCreatTime(Date activityCreatTime) {
		this.activityCreatTime = activityCreatTime;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
