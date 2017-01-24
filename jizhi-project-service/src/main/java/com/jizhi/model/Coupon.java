package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class Coupon implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String phone;
	private int money;
	private Date createTime;
	private int status;
	private Date useTime;
	private int tbinedex;
	private int expirDate;
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
			this.tbinedex = (int) (Long.parseLong(phone)%10);
		}
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	public int getTbinedex() {
		return tbinedex;
	}
	public int getExpirDate() {
		return expirDate;
	}
	public void setExpirDate(int expirDate) {
		this.expirDate = expirDate;
	}
}
