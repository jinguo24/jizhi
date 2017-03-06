package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 赛程 -- 分表
 * @author zhengfy1
 */
public class RaceSchedule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int racePeriodsId;//第几期的
	private String name;//赛程名称
	private int sort;//排序
	private Date beginTime;//开始时间
	private Date endTime;//结束时间
	private int teamOne;//第一队
	private int teamTwo;//第二队
	private int teamOneScores;//第一队进球数
	private int teamTwoScores;//第二对进球数
	private int successTeamId;//获胜球队编号
	private int tbinedex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRacePeriodsId() {
		return racePeriodsId;
	}
	public void setRacePeriodsId(int racePeriodsId) {
		this.racePeriodsId = racePeriodsId;
		if (racePeriodsId > 0 ) {
			this.tbinedex = racePeriodsId%10;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getTeamOne() {
		return teamOne;
	}
	public void setTeamOne(int teamOne) {
		this.teamOne = teamOne;
	}
	public int getTeamTwo() {
		return teamTwo;
	}
	public void setTeamTwo(int teamTwo) {
		this.teamTwo = teamTwo;
	}
	public int getTeamOneScores() {
		return teamOneScores;
	}
	public void setTeamOneScores(int teamOneScores) {
		this.teamOneScores = teamOneScores;
	}
	public int getTeamTwoScores() {
		return teamTwoScores;
	}
	public void setTeamTwoScores(int teamTwoScores) {
		this.teamTwoScores = teamTwoScores;
	}
	public int getSuccessTeamId() {
		return successTeamId;
	}
	public void setSuccessTeamId(int successTeamId) {
		this.successTeamId = successTeamId;
	}
	public int getTbinedex() {
		return tbinedex;
	}
}
