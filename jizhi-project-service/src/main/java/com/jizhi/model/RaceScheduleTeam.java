package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 赛程小组队伍
 * @author zhengfy1
 */
public class RaceScheduleTeam implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private int raceScheduleId;//赛程编号
	private int sort;//排序
	private Date beginTime;//开始时间
	private Date endTime;//结束时间
	private String teamOne;//第一队
	private String teamTwo;//第二队
	private String successTeamId;//获胜球队编号
	private String collectItems;//收集数据项json
	private String judgeItems;//评判数据项json
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getTeamOne() {
		return teamOne;
	}
	public void setTeamOne(String teamOne) {
		this.teamOne = teamOne;
	}
	public String getTeamTwo() {
		return teamTwo;
	}
	public void setTeamTwo(String teamTwo) {
		this.teamTwo = teamTwo;
	}
	public String getSuccessTeamId() {
		return successTeamId;
	}
	public void setSuccessTeamId(String successTeamId) {
		this.successTeamId = successTeamId;
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
