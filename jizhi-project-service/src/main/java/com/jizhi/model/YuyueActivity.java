package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.simple.common.util.DateUtil;

public class YuyueActivity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Date createTime;
	private int status;
	private int maxAllowed;
	private int surplus;
	private String remark;
	private String parentId="";
	private Date beginDate;
	private String showBeginDate="";
	private String beginTime="";
	private String endTime="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMaxAllowed() {
		return maxAllowed;
	}
	public void setMaxAllowed(int maxAllowed) {
		this.maxAllowed = maxAllowed;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSurplus() {
		return surplus;
	}
	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
		if (null != beginDate) {
			this.showBeginDate = DateUtil.date2String(beginDate);
		}
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getShowBeginDate() {
		return showBeginDate;
	}
}
