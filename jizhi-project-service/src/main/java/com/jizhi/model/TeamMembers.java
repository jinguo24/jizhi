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
	private String name;//名称
	private String remark;//备注
	private int main;//主力球员 1-是 0-不是
	private int leader;//领队 1-是 0-不是
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
			this.tbinedex = Math.abs(teamId.hashCode()%10) ;
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
	public int getLeader() {
		return leader;
	}
	public void setLeader(int leader) {
		this.leader = leader;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
