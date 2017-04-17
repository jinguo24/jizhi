package com.jizhi.model;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class WxUser implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String openId;
	private String nickName;
	private String image;
	private String phone;
	private int tbindex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
		if (!StringUtils.isEmpty(openId)) {
			this.tbindex = Math.abs(openId.hashCode()%100);
		}
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getTbindex() {
		return tbindex;
	}
}
