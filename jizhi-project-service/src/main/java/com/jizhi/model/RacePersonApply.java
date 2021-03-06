package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

public class RacePersonApply implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int raceId;
	private String phone;
	private Date createTime;
	private String teamName;
	private String raceName;
	private String teamApplyId;
	private String name;
	private int leader;
	private int tbinedex;
	private String positions="";
	private String headImage="";
	private String studentNo;//学号
	private String className;//班级名称
	private int supportCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
		this.tbinedex = raceId%10;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	public int getTbinedex() {
		return tbinedex;
	}
	public String getTeamApplyId() {
		return teamApplyId;
	}
	public void setTeamApplyId(String teamApplyId) {
		this.teamApplyId = teamApplyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLeader() {
		return leader;
	}
	public void setLeader(int leader) {
		this.leader = leader;
	}
	public String getPositions() {
		return positions;
	}
	public void setPositions(String positions) {
		this.positions = positions;
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
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public int getSupportCount() {
		return supportCount;
	}
	public void setSupportCount(int supportCount) {
		this.supportCount = supportCount;
	}
}
