package com.jizhi.model;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private String phone;
	private String weiChatNo;
	private String weiChatImage;
	private int tbinedex;
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
}
