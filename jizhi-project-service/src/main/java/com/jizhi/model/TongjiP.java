package com.jizhi.model;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class TongjiP implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String phone;
	private int shots;
	private int scores;
	private int type;
	private int counts;
	private int ontimes;
	private int pushs;
	private int points;
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
	public int getShots() {
		return shots;
	}
	public void setShots(int shots) {
		this.shots = shots;
	}
	public int getScores() {
		return scores;
	}
	public void setScores(int scores) {
		this.scores = scores;
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
	public int getOntimes() {
		return ontimes;
	}
	public void setOntimes(int ontimes) {
		this.ontimes = ontimes;
	}
	public int getPushs() {
		return pushs;
	}
	public void setPushs(int pushs) {
		this.pushs = pushs;
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
}
