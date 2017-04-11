package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

public class Activity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String ownerPhone;
	private String ownerName;
	private String address;
	private String activityTime;
	private String remark;
	private int tbindex;
	private int status = 0;//0-有效 1-过期
	private Date createTime;
	private List<ActivityPerson> members;
	private String token;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<ActivityPerson> getMembers() {
		return members;
	}
	public void setMembers(List<ActivityPerson> members) {
		this.members = members;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
