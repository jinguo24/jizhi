package com.jizhi.model;

import java.io.Serializable;

/**
 * 比赛结构，人员，分表
 * @author zhengfy1
 *
 */
public class RaceResults implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int raceScheduleId;//赛程编号
	private String phone;//队员电话
	private String nickname;//昵称
	private int number;//号码
	private int scores;//进球数
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
}
