package com.jizhi.model;

import java.io.Serializable;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class TongjiTRace implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String teamId;
	private int raceId;
	private String collectItems;
	private Map<String,Double> collectItemsMap;
	private int points;
	private int wins;
	private int loses;
	private int evens;
	private int tbindex;
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
	public String getCollectItems() {
		return collectItems;
	}
	public void setCollectItems(String collectItems) {
		this.collectItems = collectItems;
		if (!StringUtils.isEmpty(collectItems)) {
			this.collectItemsMap = (Map<String, Double>) JSONObject.parse(collectItems);
		}
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getTbindex() {
		return tbindex;
	}
	public void setTbindex(int tbindex) {
		this.tbindex = tbindex;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
		if (!StringUtils.isEmpty(teamId)) {
			this.tbindex = teamId.hashCode()%10;
		}
	}
	public Map<String, Double> getCollectItemsMap() {
		return collectItemsMap;
	}
	public void setCollectItemsMap(Map<String, Double> collectItemsMap) {
		this.collectItemsMap = collectItemsMap;
		if ( null != collectItemsMap ) {
			this.collectItems = JSONObject.toJSONString(collectItemsMap);
		}
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
	public int getEvens() {
		return evens;
	}
	public void setEvens(int evens) {
		this.evens = evens;
	}
}
