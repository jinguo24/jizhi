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
	private Team teamOneObj;
	private String teamTwo;//第二队
	private Team teamTwoObj;
	private String successTeamId;//获胜球队编号
	private String collectItemslists;
	private String collectItems;//收集数据项json
	private Map<String,Map<Integer,Double>> collectItemsMap;
	private String judgetItemslists;
	private String judgeItems;//评判数据项json
	private Map<String,Map<Integer,Double>> judgeItemsMap;
	private int type;
	private String address;
	private int status ;
	private String tags="";
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
			this.collectItemsMap = (Map<String,Map<Integer,Double>>) JSONArray.parse(collectItems);
		}
	}
	public String getJudgeItems() {
		return judgeItems;
	}
	public void setJudgeItems(String judgeItems) {
		this.judgeItems = judgeItems;
		if (!StringUtils.isEmpty(judgeItems)) {
			this.judgeItemsMap = (Map<String,Map<Integer,Double>>) JSONArray.parse(judgeItems);
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Map<String,Map<Integer, Double>> getCollectItemsMap() {
		return collectItemsMap;
	}
	public void setCollectItemsMap(Map<String,Map<Integer, Double>> collectItemsMap) {
		this.collectItemsMap = collectItemsMap;
		if ( null != collectItemsMap) {
			this.collectItems = JSONObject.toJSONString(collectItemsMap);
		}
	}
	public Map<String,Map<Integer, Double>> getJudgeItemsMap() {
		return judgeItemsMap;
	}
	public void setJudgeItemsMap(Map<String,Map<Integer, Double>> judgeItemsMap) {
		this.judgeItemsMap = judgeItemsMap;
		if ( null != judgeItemsMap) {
			this.judgeItems = JSONObject.toJSONString(judgeItemsMap);
		}
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Team getTeamOneObj() {
		return teamOneObj;
	}
	public void setTeamOneObj(Team teamOneObj) {
		this.teamOneObj = teamOneObj;
	}
	public Team getTeamTwoObj() {
		return teamTwoObj;
	}
	public void setTeamTwoObj(Team teamTwoObj) {
		this.teamTwoObj = teamTwoObj;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCollectItemslists() {
		return collectItemslists;
	}
	public void setCollectItemslists(String collectItemslists) {
		this.collectItemslists = collectItemslists;
	}
	public String getJudgetItemslists() {
		return judgetItemslists;
	}
	public void setJudgetItemslists(String judgetItemslists) {
		this.judgetItemslists = judgetItemslists;
	}
}
