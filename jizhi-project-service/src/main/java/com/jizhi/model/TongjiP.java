package com.jizhi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class TongjiP implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String phone;
	private int type;
	//位置统计项
	private String collectItems;
	private Map<Integer,Map<Integer,Double>> collectItemsMap = new HashMap<Integer,Map<Integer,Double>>();
	//位置统计项-统计次数
	private String collectCounts;
	private Map<Integer,Map<Integer,Integer>> collectCountsMap = new HashMap<Integer,Map<Integer,Integer>>();
	//位置评分项
	private String judgeItems;
	private Map<Integer,Map<Integer,Double>> judgeItemsMap = new HashMap<Integer,Map<Integer,Double>>();
	private String judgeCounts;
	private Map<Integer,Map<Integer,Integer>> judgeCountsMap = new HashMap<Integer,Map<Integer,Integer>>(); 
	private String raceCounts;//赛事总数
	private Map<Integer,Integer> raceCountsMap;
	private Double points;
	private int tbinedex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
		if (!StringUtils.isEmpty(phone)) {
			this.tbinedex = (int) (Long.parseLong(phone)%10);
		}
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTbinedex() {
		return tbinedex;
	}
	public String getCollectItems() {
		return collectItems;
	}
	public void setCollectItems(String collectItems) {
		this.collectItems = collectItems;
		if (!StringUtils.isEmpty(collectItems)) {
			this.collectItemsMap  = (Map<Integer, Map<Integer, Double>>) JSONObject.parse(collectItems);
		}
	}
	public String getJudgeItems() {
		return judgeItems;
	}
	public void setJudgeItems(String judgeItems) {
		this.judgeItems = judgeItems;
		if (!StringUtils.isEmpty(judgeItems)) {
			this.judgeItemsMap = (Map<Integer, Map<Integer, Double>>) JSONObject.parse(judgeItems);
		}
	}
	public void setCollectItemsMap(Map<Integer, Map<Integer, Double>> collectItemsMap) {
		this.collectItemsMap = collectItemsMap;
		if (null != collectItemsMap) {
			this.collectItems = JSONObject.toJSONString(collectItemsMap);
		}
	}
	public void setJudgeItemsMap(Map<Integer, Map<Integer, Double>> judgeItemsMap) {
		this.judgeItemsMap = judgeItemsMap;
		if ( null != judgeItemsMap) {
			this.judgeItems = JSONObject.toJSONString(judgeItemsMap);
		}
	}
	public Map<Integer, Map<Integer, Double>> getCollectItemsMap() {
		return collectItemsMap;
	}
	public Map<Integer, Map<Integer, Double>> getJudgeItemsMap() {
		return judgeItemsMap;
	}
	public String getRaceCounts() {
		return raceCounts;
	}
	public void setRaceCounts(String raceCounts) {
		this.raceCounts = raceCounts;
		if (!StringUtils.isEmpty(raceCounts)) {
			this.raceCountsMap = (Map<Integer, Integer>) JSONObject.parse(raceCounts);
		}
	}
	public Map<Integer, Integer> getRaceCountsMap() {
		return raceCountsMap;
	}
	public void setRaceCountsMap(Map<Integer, Integer> raceCountsMap) {
		this.raceCountsMap = raceCountsMap;
		if ( null != raceCountsMap) {
			this.raceCounts = JSONObject.toJSONString(raceCountsMap);
		}
	}
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}
	public String getCollectCounts() {
		return collectCounts;
	}
	public void setCollectCounts(String collectCounts) {
		this.collectCounts = collectCounts;
		if (!StringUtils.isEmpty(collectCounts)) {
			this.collectCountsMap = (Map<Integer, Map<Integer, Integer>>) JSONObject.parse(collectCounts);
		}
	}
	public Map<Integer, Map<Integer, Integer>> getCollectCountsMap() {
		return collectCountsMap;
	}
	public void setCollectCountsMap(
			Map<Integer, Map<Integer, Integer>> collectCountsMap) {
		this.collectCountsMap = collectCountsMap;
		if ( null != collectCountsMap) {
			this.collectCounts = JSONObject.toJSONString(collectCountsMap);
		}
	}
	public String getJudgeCounts() {
		return judgeCounts;
	}
	public void setJudgeCounts(String judgeCounts) {
		this.judgeCounts = judgeCounts;
		if (!StringUtils.isEmpty(judgeCounts)) {
			this.judgeCountsMap = (Map<Integer, Map<Integer, Integer>>) JSONObject.parse(judgeCounts);
		}
	}
	public Map<Integer, Map<Integer, Integer>> getJudgeCountsMap() {
		return judgeCountsMap;
	}
	public void setJudgeCountsMap(Map<Integer, Map<Integer, Integer>> judgeCountsMap) {
		this.judgeCountsMap = judgeCountsMap;
		if ( null != judgeCountsMap) {
			this.judgeCounts = JSONObject.toJSONString(judgeCountsMap);
		}
	}
	public void clearItems() {
		this.collectItems = null;
		this.collectItemsMap = null;
		this.judgeItems = null;
		this.judgeItemsMap = null;
		this.raceCounts = null;
		this.raceCountsMap = null;
		this.judgeCounts = null;
		this.judgeCountsMap = null;
		this.collectCounts = null;
		this.collectCountsMap = null;
	}
}
