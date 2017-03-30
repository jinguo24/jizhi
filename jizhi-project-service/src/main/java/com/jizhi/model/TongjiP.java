package com.jizhi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TongjiP implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String phone;
	private int type;
	//位置统计项
	private String collectItems;
	private Map<String,Map<String,Double>> collectItemsMap = new HashMap<String,Map<String,Double>>();
	//位置统计项-统计次数
	private String collectCounts;
	private Map<String,Map<String,Integer>> collectCountsMap = new HashMap<String,Map<String,Integer>>();
	//位置评分项
	private String judgeItems;
	private Map<String,Map<String,Double>> judgeItemsMap = new HashMap<String,Map<String,Double>>();
	private String judgeCounts;
	private Map<String,Map<String,Integer>> judgeCountsMap = new HashMap<String,Map<String,Integer>>(); 
	//平均评分项
	private String allJudges;
	private Map<String,Double> allJudgeItemsMap = new HashMap<String,Double>();
	private String raceCounts;//赛事总数
	private Map<String,Integer> raceCountsMap;
	private String points;
	private Map<String,Double> pointsMap;
	//擅长位置
	private String gpositions;
	private Map<String,Double> gpositionsMap = new HashMap<String,Double>();
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
			this.collectItemsMap  = (Map<String, Map<String, Double>>) JSONObject.parse(collectItems);
		}
	}
	public String getJudgeItems() {
		return judgeItems;
	}
	public void setJudgeItems(String judgeItems) {
		this.judgeItems = judgeItems;
		if (!StringUtils.isEmpty(judgeItems)) {
			this.judgeItemsMap = (Map<String, Map<String, Double>>) JSONObject.parse(judgeItems);
		}
	}
	public void setCollectItemsMap(Map<String, Map<String, Double>> collectItemsMap) {
		this.collectItemsMap = collectItemsMap;
		if (null != collectItemsMap) {
			this.collectItems = JSONObject.toJSONString(collectItemsMap);
		}
	}
	public void setJudgeItemsMap(Map<String, Map<String, Double>> judgeItemsMap) {
		this.judgeItemsMap = judgeItemsMap;
		if ( null != judgeItemsMap) {
			this.judgeItems = JSONObject.toJSONString(judgeItemsMap);
		}
	}
	public Map<String, Map<String, Double>> getCollectItemsMap() {
		return collectItemsMap;
	}
	public Map<String, Map<String, Double>> getJudgeItemsMap() {
		return judgeItemsMap;
	}
	public String getRaceCounts() {
		return raceCounts;
	}
	public void setRaceCounts(String raceCounts) {
		this.raceCounts = raceCounts;
		if (!StringUtils.isEmpty(raceCounts)) {
			this.raceCountsMap = (Map<String, Integer>) JSONObject.parse(raceCounts);
		}
	}
	public Map<String, Integer> getRaceCountsMap() {
		return raceCountsMap;
	}
	public void setRaceCountsMap(Map<String, Integer> raceCountsMap) {
		this.raceCountsMap = raceCountsMap;
		if ( null != raceCountsMap) {
			this.raceCounts = JSONObject.toJSONString(raceCountsMap);
		}
	}
	public String getCollectCounts() {
		return collectCounts;
	}
	public void setCollectCounts(String collectCounts) {
		this.collectCounts = collectCounts;
		if (!StringUtils.isEmpty(collectCounts)) {
			this.collectCountsMap = (Map<String, Map<String, Integer>>) JSONObject.parse(collectCounts);
		}
	}
	public Map<String, Map<String, Integer>> getCollectCountsMap() {
		return collectCountsMap;
	}
	public void setCollectCountsMap(
			Map<String, Map<String, Integer>> collectCountsMap) {
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
			this.judgeCountsMap = (Map<String, Map<String, Integer>>) JSONObject.parse(judgeCounts);
		}
	}
	public Map<String, Map<String, Integer>> getJudgeCountsMap() {
		return judgeCountsMap;
	}
	public void setJudgeCountsMap(Map<String, Map<String, Integer>> judgeCountsMap) {
		this.judgeCountsMap = judgeCountsMap;
		if ( null != judgeCountsMap) {
			this.judgeCounts = JSONObject.toJSONString(judgeCountsMap);
		}
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
		if (StringUtils.isEmpty(points)) {
			this.pointsMap = (Map<String, Double>) JSONObject.parse(points);
		}
	}
	public Map<String, Double> getPointsMap() {
		return pointsMap;
	}
	public void setPointsMap(Map<String, Double> pointsMap) {
		this.pointsMap = pointsMap;
		if (null != pointsMap) {
			this.points = JSONObject.toJSONString(pointsMap);
		}
	}
	public String getAllJudges() {
		return allJudges;
	}
	public void setAllJudges(String allJudges) {
		this.allJudges = allJudges;
		if (!StringUtils.isEmpty(allJudges)) {
			this.allJudgeItemsMap = (Map<String, Double>) JSONObject.parse(allJudges);
		}
	}
	public Map<String, Double> getAllJudgeItemsMap() {
		return allJudgeItemsMap;
	}
	public void setAllJudgeItemsMap(Map<String, Double> allJudgeItemsMap) {
		this.allJudgeItemsMap = allJudgeItemsMap;
		if (null != allJudgeItemsMap) {
			this.allJudges = JSONObject.toJSONString(allJudgeItemsMap);
		}
	}
	public String getGpositions() {
		return gpositions;
	}
	public void setGpositions(String gpositions) {
		this.gpositions = gpositions;
		if (StringUtils.isEmpty(gpositions)) {
			this.gpositionsMap = (Map<String, Double>) JSONObject.parse(gpositions);
		}
	}
	public Map<String, Double> getGpositionsMap() {
		return gpositionsMap;
	}
	public void setGpositionsMap(Map<String, Double> gpositionsMap) {
		this.gpositionsMap = gpositionsMap;
		if ( null != gpositionsMap) {
			this.gpositions = JSONObject.toJSONString(gpositionsMap);
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
		this.allJudgeItemsMap = null;
		this.allJudges = null;
		this.pointsMap = null;
		this.points = null;
		this.gpositions = null;
		this.gpositionsMap = null;
	}
}
