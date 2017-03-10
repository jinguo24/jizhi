package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 球队荣誉
 * @author zhengfy1
 */
public class TeamAwards implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String teamId;
	private String content;
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
