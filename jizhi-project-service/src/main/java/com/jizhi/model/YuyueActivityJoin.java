package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class YuyueActivityJoin implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String activityOpenId;
	private String activityId;
	private String openId;
	private String nickName;
	private String image;
	private int tbindex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActivityOpenId() {
		return activityOpenId;
	}
	public void setActivityOpenId(String activityOpenId) {
		this.activityOpenId = activityOpenId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
		if (StringUtils.isEmpty(activityId)) {
			this.tbindex = Math.abs(activityId.hashCode()%100);
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
}
