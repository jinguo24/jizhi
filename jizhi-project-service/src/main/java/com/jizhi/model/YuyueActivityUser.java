package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

public class YuyueActivityUser implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String openId;
	private String activityId;
	private String image;
	private String nickName;
	private int joinCounts;
	private int tbindex;
	private List<YuyueActivityJoin> joins;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getTbindex() {
		return tbindex;
	}
	public List<YuyueActivityJoin> getJoins() {
		return joins;
	}
	public void setJoins(List<YuyueActivityJoin> joins) {
		this.joins = joins;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
		if (!StringUtils.isEmpty(activityId)) {
			this.tbindex = Math.abs(activityId.hashCode()%100);
		}
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getJoinCounts() {
		return joinCounts;
	}
	public void setJoinCounts(int joinCounts) {
		this.joinCounts = joinCounts;
	}
	public void setTbindex(int tbindex) {
		this.tbindex = tbindex;
	}
}
