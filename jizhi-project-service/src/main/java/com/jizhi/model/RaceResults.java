package com.jizhi.model;

import java.io.Serializable;

/**
 * 比赛结果，人员，分表
 * @author zhengfy1
 *
 */
public class RaceResults implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int raceId;//赛事编号
	private int racePeriodsId;//赛程分期编号
	private int raceScheduleId;//赛程小组编号
	private int raceScheduleTeamId;//赛程小组队伍编号
	private String teamId;//队编号
	private String phone;//队员电话
	private int number;//号码
	private int scores;//进球数
	private int shots;//射门数
	private int ontimes;//上场时长(秒)
	private int pushs;//扑救数
	private String remark;//备注
	private int tbinedex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRaceScheduleId() {
		return raceScheduleId;
	}
	public void setRaceScheduleId(int raceScheduleId) {
		this.raceScheduleId = raceScheduleId;
	}
	public int getRaceScheduleTeamId() {
		return raceScheduleTeamId;
	}
	public void setRaceScheduleTeamId(int raceScheduleTeamId) {
		this.raceScheduleTeamId = raceScheduleTeamId;
		if (raceScheduleId > 0 ) {
			this.tbinedex = raceScheduleId%10;
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getScores() {
		return scores;
	}
	public void setScores(int scores) {
		this.scores = scores;
	}
	public int getTbinedex() {
		return tbinedex;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getRacePeriodsId() {
		return racePeriodsId;
	}
	public void setRacePeriodsId(int racePeriodsId) {
		this.racePeriodsId = racePeriodsId;
	}
	public int getShots() {
		return shots;
	}
	public void setShots(int shots) {
		this.shots = shots;
	}
	public int getOntimes() {
		return ontimes;
	}
	public void setOntimes(int ontimes) {
		this.ontimes = ontimes;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public int getPushs() {
		return pushs;
	}
	public void setPushs(int pushs) {
		this.pushs = pushs;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
