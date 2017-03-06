package com.jizhi.model;

import java.io.Serializable;

/**
 * 球队队员--分表
 * @author zhengfy1
 */
public class TeamMembers implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private int teamId;//队编号
	private String phone;//电话
	private String nickName;//昵称
	private String remark;//备注
	private int main;//主力球员 1-是 0-不是
	private int tbinedex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
		if ( teamId > 0) {
			this.tbinedex = teamId%10 ;
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getMain() {
		return main;
	}
	public void setMain(int main) {
		this.main = main;
	}
	public int getTbinedex() {
		return tbinedex;
	}
}
