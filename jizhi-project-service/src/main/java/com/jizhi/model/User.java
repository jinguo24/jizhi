package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private String phone;
	private String name;
	private String nickName;
	private String weiChatNo;
	private String weiChatImage;
	private Date createTime;
	private int tbinedex;
	private String positions;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
		if (!StringUtils.isEmpty(phone)) {
			this.tbinedex = (int) (Long.parseLong(phone)%10);
		}
	}
	public String getWeiChatNo() {
		return weiChatNo;
	}
	public void setWeiChatNo(String weiChatNo) {
		this.weiChatNo = weiChatNo;
	}
	public String getWeiChatImage() {
		return weiChatImage;
	}
	public void setWeiChatImage(String weiChatImage) {
		this.weiChatImage = weiChatImage;
	}
	public int getTbinedex() {
		return tbinedex;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPositions() {
		return positions;
	}
	public void setPositions(String positions) {
		this.positions = positions;
	}
}
