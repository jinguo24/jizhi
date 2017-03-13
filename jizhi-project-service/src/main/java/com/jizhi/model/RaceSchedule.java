package com.jizhi.model;

import java.io.Serializable;
/**
 * 赛程小组
 * @author zhengfy1
 */
public class RaceSchedule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int raceId;//第几期的
	private String name;//赛程名称
	private int sort;//排序
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
}
