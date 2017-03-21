package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 赛程小组队伍
 * @author zhengfy1
 */
public class RaceScheduleTeam implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private int raceId;//赛事编号
	private Date beginDate;//开始日期
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private int sort;
	private String teamOne;//第一队
	private String teamOneName;
	private String teamTwo;//第二队
	private String teamTwoName;
	private String successTeamId;//获胜球队编号
	private Double teamOnePoints;
	private Double teamTwoPoints;
	private String collectItems;//收集数据项json
	private Map<Integer,Double> collectItemsMap;
	private String judgeItems;//评判数据项json
	private Map<Integer,Double> judgeItemsMap;
	private int type;
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
		if (!StringUtils.isEmpty(collectItems)) {
			this.collectItemsMap = (Map<Integer,Double>) JSONArray.parse(collectItems);
		}
	}
	public String getJudgeItems() {
		return judgeItems;
	}
	public void setJudgeItems(String judgeItems) {
		this.judgeItems = judgeItems;
		if (!StringUtils.isEmpty(judgeItems)) {
			this.judgeItemsMap = (Map<Integer,Double>) JSONArray.parse(judgeItems);
		}
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTeamOneName() {
		return teamOneName;
	}
	public void setTeamOneName(String teamOneName) {
		this.teamOneName = teamOneName;
	}
	public String getTeamTwoName() {
		return teamTwoName;
	}
	public void setTeamTwoName(String teamTwoName) {
		this.teamTwoName = teamTwoName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Double getTeamOnePoints() {
		return teamOnePoints;
	}
	public void setTeamOnePoints(Double teamOnePoints) {
		this.teamOnePoints = teamOnePoints;
	}
	public Double getTeamTwoPoints() {
		return teamTwoPoints;
	}
	public void setTeamTwoPoints(Double teamTwoPoints) {
		this.teamTwoPoints = teamTwoPoints;
	}
	public Map<Integer, Double> getCollectItemsMap() {
		return collectItemsMap;
	}
	public void setCollectItemsMap(Map<Integer, Double> collectItemsMap) {
		this.collectItemsMap = collectItemsMap;
		if ( null != collectItemsMap) {
			this.collectItems = JSONObject.toJSONString(collectItemsMap);
		}
	}
	public Map<Integer, Double> getJudgeItemsMap() {
		return judgeItemsMap;
	}
	public void setJudgeItemsMap(Map<Integer, Double> judgeItemsMap) {
		this.judgeItemsMap = judgeItemsMap;
		if ( null != judgeItemsMap) {
			this.judgeItems = JSONObject.toJSONString(judgeItemsMap);
		}
	}
}
