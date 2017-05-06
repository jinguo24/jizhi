package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;
public class RaceUserSupport implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String ownerPhone;
	private String phone;
	private Date createTime;
	private int raceId;
	private int tbindex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getTbindex() {
		return tbindex;
	}
}
