package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.simple.common.util.DateUtil;

public class TeamRaceApply implements Serializable,Comparable<TeamRaceApply>{

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
	private List<RacePersonApply> members;
	private String token;//token
	private int currentLeader;//当前用户是否领队
	private String studentNo;//学号
	private String className;//班级名称
	
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
	public List<RacePersonApply> getMembers() {
		return members;
	}
	public void setMembers(List<RacePersonApply> members) {
		this.members = members;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getCurrentLeader() {
		return currentLeader;
	}
	public void setCurrentLeader(int currentLeader) {
		this.currentLeader = currentLeader;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	@Override
	public int compareTo(TeamRaceApply o) {
		return o.getCreateTime().compareTo(this.getCreateTime());
	}
}
