package com.jizhi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TongjiP implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String phone;
	private int type;
	private int counts;
	private int points;
	private String collectItems;
	//位置统计项
	private Map<Integer,Map<String,Double>> collectItemsMap = new HashMap<Integer,Map<String,Double>>();
	private String judgeItems;
	private Map<Integer,Map<String,Double>> judgeItemsMap = new HashMap<Integer,Map<String,Double>>();
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
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public int getTbinedex() {
		return tbinedex;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getCollectItems() {
		return collectItems;
	}
	public void setCollectItems(String collectItems) {
		this.collectItems = collectItems;
		if (!StringUtils.isEmpty(collectItems)) {
			this.collectItemsMap  = (Map<Integer, Map<String, Double>>) JSONObject.parse(collectItems);
		}
	}
	public String getJudgeItems() {
		return judgeItems;
	}
	public void setJudgeItems(String judgeItems) {
		this.judgeItems = judgeItems;
		if (!StringUtils.isEmpty(judgeItems)) {
			this.judgeItemsMap = (Map<Integer, Map<String, Double>>) JSONObject.parse(judgeItems);
		}
	}
	public Map<Integer, Map<String, Double>> getCollectItemsMap() {
		return collectItemsMap;
	}
	public Map<Integer, Map<String, Double>> getJudgeItemsMap() {
		return judgeItemsMap;
	}
}
