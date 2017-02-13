package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.simple.common.util.DateUtil;

public class Coupon implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String phone;
	private Date createTime;
	private Date createDate;
	private String createTimeStr;
	private int useStatus;
	private Date useTime;
	private String useTimeStr;
	private int tbinedex;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if (null != createTime) {
			this.createTimeStr = DateUtil.date2AllString(createTime);
		}
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
		if (null != useTime) {
			this.useTimeStr = DateUtil.date2AllString(useTime);
		}
	}
	public int getTbinedex() {
		return tbinedex;
	}
	public int getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(int useStatus) {
		this.useStatus = useStatus;
	}
	public String getUseTimeStr() {
		return useTimeStr;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
