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
	private int status = 0;//是否已过期 0-否 1-是
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
			this.status = 1;
		}
	}
	public int getStatus() {
		return status;
	}
}
