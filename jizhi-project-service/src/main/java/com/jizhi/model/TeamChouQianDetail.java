package com.jizhi.model;

import java.io.Serializable;

public class TeamChouQianDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String leaderPhone;
	private String label;
	private int raceId;
	public String getLeaderPhone() {
		return leaderPhone;
	}
	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
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
}
