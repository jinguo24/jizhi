 package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class YuyueActivity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String activityId;
	private String name;
	private Date createTime;
	private Date deadLineTime;
	private Date endTime;
	private int status;
	private int joinCount;
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDeadLineTime() {
		return deadLineTime;
	}
	public void setDeadLineTime(Date deadLineTime) {
		this.deadLineTime = deadLineTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(int joinCount) {
		this.joinCount = joinCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
