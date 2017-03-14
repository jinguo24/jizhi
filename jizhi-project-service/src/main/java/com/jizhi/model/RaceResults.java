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
	private int raceScheduleId;//赛程小组编号
	private int raceScheduleTeamId;//赛程小组队伍编号
	private String teamId;//队编号
	private String phone;//队员电话
	private int number;//号码
	private int position;//场上位置
	private String collectItems;//收集数据项json
	private String judgeItems;//评判数据项json
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
	public int getTbinedex() {
		return tbinedex;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getCollectItems() {
		return collectItems;
	}
	public void setCollectItems(String collectItems) {
		this.collectItems = collectItems;
	}
	public String getJudgeItems() {
		return judgeItems;
	}
	public void setJudgeItems(String judgeItems) {
		this.judgeItems = judgeItems;
	}
}
