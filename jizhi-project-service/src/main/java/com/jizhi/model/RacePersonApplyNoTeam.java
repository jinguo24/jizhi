package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

public class RacePersonApplyNoTeam implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int raceId;
	private String phone;
	private Date createTime;
	private String name;
	private int tbindex;
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
		this.tbindex = raceId%10;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTbindex() {
		return tbindex;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
