package com.jizhi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class TongjiT implements Serializable{

	private static final long serialVersionUID = 1L;

	private String teamId;
	private Team team;
	private int counts;
	private Double points;
	private int wins;
	private int loses;
	private int even;
	private String collectItems;
	private Map<Integer,Double> collectItemsMap = new HashMap<Integer,Double>();
	private String collectItemsCounts;
	private Map<Integer,Integer> collectItemsCountsMap = new HashMap<Integer,Integer>();
	private String judgeItems;
	private Map<Integer,Double> judgeItemsMap = new HashMap<Integer,Double>();
	private String judgeItemsCounts;
	private Map<Integer,Integer> judgeItemsCountsMap = new HashMap<Integer,Integer>();
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}
	public String getCollectItems() {
		return collectItems;
	}
	public void setCollectItems(String collectItems) {
		this.collectItems = collectItems;
		if (!StringUtils.isEmpty(collectItems)) {
			this.collectItemsMap  = (Map<Integer, Double>) JSONObject.parse(collectItems);
		}
	}
	public String getJudgeItems() {
		return judgeItems;
	}
	public void setJudgeItems(String judgeItems) {
		this.judgeItems = judgeItems;
		if (!StringUtils.isEmpty(judgeItems)) {
			this.judgeItemsMap = (Map<Integer, Double>) JSONObject.parse(judgeItems);
		}
	}
	public void setCollectItemsMap(Map<Integer, Double> collectItemsMap) {
		this.collectItemsMap = collectItemsMap;
		if (null != collectItemsMap) {
			this.collectItems = JSONObject.toJSONString(collectItemsMap);
		}
	}
	public void setJudgeItemsMap(Map<Integer, Double> judgeItemsMap) {
		this.judgeItemsMap = judgeItemsMap;
		if ( null != judgeItemsMap) {
			this.judgeItems = JSONObject.toJSONString(judgeItemsMap);
		}
	}
	public String getCollectItemsCounts() {
		return collectItemsCounts;
	}
	public void setCollectItemsCounts(String collectItemsCounts) {
		this.collectItemsCounts = collectItemsCounts;
		if (StringUtils.isEmpty(collectItemsCounts)) {
			this.collectItemsCountsMap = (Map<Integer, Integer>) JSONObject.parse(collectItemsCounts);
		}
	}
	public Map<Integer, Integer> getCollectItemsCountsMap() {
		return collectItemsCountsMap;
	}
	public void setCollectItemsCountsMap(Map<Integer, Integer> collectItemsCountsMap) {
		this.collectItemsCountsMap = collectItemsCountsMap;
		if ( null != collectItemsCountsMap) {
			this.collectItemsCounts = JSONObject.toJSONString(collectItemsCountsMap);
		}
	}
	public String getJudgeItemsCounts() {
		return judgeItemsCounts;
	}
	public void setJudgeItemsCounts(String judgeItemsCounts) {
		this.judgeItemsCounts = judgeItemsCounts;
		if (StringUtils.isEmpty(judgeItemsCounts)) {
			this.judgeItemsCountsMap = (Map<Integer, Integer>) JSONObject.parse(judgeItemsCounts);
		}
	}
	public Map<Integer, Integer> getJudgeItemsCountsMap() {
		return judgeItemsCountsMap;
	}
	public void setJudgeItemsCountsMap(Map<Integer, Integer> judgeItemsCountsMap) {
		this.judgeItemsCountsMap = judgeItemsCountsMap;
		if ( null != judgeItemsCountsMap) {
			this.judgeItemsCounts = JSONObject.toJSONString(judgeItemsCountsMap);
		}
	}
	public Map<Integer, Double> getCollectItemsMap() {
		return collectItemsMap;
	}
	public Map<Integer, Double> getJudgeItemsMap() {
		return judgeItemsMap;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLoses() {
		return loses;
	}
	public void setLoses(int loses) {
		this.loses = loses;
	}
	public int getEven() {
		return even;
	}
	public void setEven(int even) {
		this.even = even;
	}
	public void clearItems() {
		this.collectItems = null;
		this.collectItemsMap = null;
		this.judgeItems = null;
		this.judgeItemsMap = null;
		this.collectItemsCounts = null;
		this.collectItemsCountsMap = null;
		this.judgeItemsCounts = null;
		this.judgeItemsCountsMap = null;
	}
}
