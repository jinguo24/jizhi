package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;
public class UserRaceSupport implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String ownerPhone;
	private String phone;
	private String name;
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
		if (!StringUtils.isEmpty(ownerPhone)) {
			this.tbindex = (int)(Long.parseLong(ownerPhone)%100);
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
