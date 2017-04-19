package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class YuyueActivityJoin implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String activityUserId;
	private String openId;
	private String nickName;
	private String image;
	private Date createTime;
	private int tbindex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActivityUserId() {
		return activityUserId;
	}
	public void setActivityUserId(String activityUserId) {
		this.activityUserId = activityUserId;
		if (!StringUtils.isEmpty(activityUserId)) {
			this.tbindex = Math.abs(activityUserId.hashCode()%100);
		}
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getTbindex() {
		return tbindex;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
