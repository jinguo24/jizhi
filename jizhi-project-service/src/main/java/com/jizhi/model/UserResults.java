package com.jizhi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class UserResults implements Serializable{

	private static final long serialVersionUID = 1L;

	private String phone;
	private String collectItems;
	//位置统计项
	private Map<Integer,Map<String,Double>> collectItemsMap = new HashMap<Integer,Map<String,Double>>();
	private String judgeItems;
	private Map<Integer,Map<String,Double>> judgeItemsMap = new HashMap<Integer,Map<String,Double>>();
	private int tbinedex;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
		if (!StringUtils.isEmpty(phone)) {
			this.tbinedex = (int) (Long.parseLong(phone)%10);
		}
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
	public Map<Integer, Map<String, Double>> getCollectItemsMap() {
		return collectItemsMap;
	}
	public Map<Integer, Map<String, Double>> getJudgeItemsMap() {
		return judgeItemsMap;
	}
	public int getTbinedex() {
		return tbinedex;
	}
}
