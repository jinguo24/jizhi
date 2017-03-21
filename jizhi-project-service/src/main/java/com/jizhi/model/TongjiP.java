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
	private String collectItems;
	//位置统计项
	private Map<Integer,Map<Integer,Double>> collectItemsMap = new HashMap<Integer,Map<Integer,Double>>();
	private String judgeItems;
	private Map<Integer,Map<Integer,Double>> judgeItemsMap = new HashMap<Integer,Map<Integer,Double>>();
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
		if (StringUtils.isEmpty(raceCounts)) {
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
	public void clearItems() {
		this.collectItems = null;
		this.collectItemsMap = null;
		this.judgeItems = null;
		this.judgeItemsMap = null;
		this.raceCounts = null;
		this.raceCountsMap = null;
	}
}
