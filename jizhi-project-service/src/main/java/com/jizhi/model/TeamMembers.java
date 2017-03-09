package com.jizhi.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * 球队队员--分表
 * @author zhengfy1
 */
public class TeamMembers implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String teamId;//队编号
	private String phone;//电话
	private String remark;//备注
	private int main;//主力球员 1-是 0-不是
	private int tbinedex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
		if (!StringUtils.isEmpty(teamId)) {
			this.tbinedex = teamId.hashCode()%10 ;
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
