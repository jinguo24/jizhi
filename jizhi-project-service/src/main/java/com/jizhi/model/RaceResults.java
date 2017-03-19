package com.jizhi.model;

import java.io.Serializable;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 比赛结果，人员，分表
 * @author zhengfy1
 *
 */
public class RaceResults implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int raceId;//赛事编号
	private int raceScheduleTeamId;//赛程编号
	private int type=1;//类型
	private String teamId;//队编号
	private String phone;//队员电话
	private int number;//号码
	private int position;//场上位置
	private String collectItems;//收集数据项json
	private Map<Integer,Double> collectItemsMap;
	private String judgeItems;//评判数据项json
	private Map<Integer,Double> judgeItemsMap;
	private int tbinedex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRaceScheduleTeamId() {
		return raceScheduleTeamId;
	}
	public void setRaceScheduleTeamId(int raceScheduleTeamId) {
		this.raceScheduleTeamId = raceScheduleTeamId;
		if (raceScheduleTeamId > 0 ) {
			this.tbinedex = raceScheduleTeamId%10;
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTbinedex() {
		return tbinedex;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
