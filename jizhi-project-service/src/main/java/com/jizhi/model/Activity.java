package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class Activity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String ownerPhone;
	private String ownerName;
	private Date beginTime;
	private Date endTime;
	private Date deadLineTime;
	private String address;
	private String remark;
	private int tbindex;
	private int deadLineStatus = 0;//状态0-有效 1-停止报名
	private int endStatus = 0;//0-有效 1-过期
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
		if (!StringUtils.isEmpty(ownerPhone)) {
			this.tbindex = (int) (Long.parseLong(ownerPhone)%10);
		}
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
		if (null != endTime && endTime.before(new Date())) {
			this.endStatus = 1;
		}
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getTbindex() {
		return tbindex;
	}
	public Date getDeadLineTime() {
		return deadLineTime;
	}
	public void setDeadLineTime(Date deadLineTime) {
		this.deadLineTime = deadLineTime;
		if (null != deadLineTime && deadLineTime.before(new Date())) {
			this.deadLineStatus = 1;
		}
	}
	public int getDeadLineStatus() {
		return deadLineStatus;
	}
	public int getEndStatus() {
		return endStatus;
	}
}
