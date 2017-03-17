package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TeamRaceApplyReject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private int raceId;
	private int status;
	private String raceName;
	private Race race;
	private String teamName;
	private String remark;
	private int type;
	private String teamImage;
	private String leaderPhone;
	private String leaderName;
	private Date createTime;
	private String members;
	private List<RacePersonApply> memberList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTeamImage() {
		return teamImage;
	}
	public void setTeamImage(String teamImage) {
		this.teamImage = teamImage;
	}
	public String getLeaderPhone() {
		return leaderPhone;
	}
	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public Race getRace() {
		return race;
	}
	public void setRace(Race race) {
		this.race = race;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public List<RacePersonApply> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<RacePersonApply> memberList) {
		this.memberList = memberList;
		if (null != memberList) {
			this.members = JSONArray.toJSONString(memberList);
		}
	}
}
