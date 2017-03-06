package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 赛事
 * @author zhengfy1
 *
 */
public class Race implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String description;
	private String image;
	private int type;//1-擂台赛  2-联赛  3-挑战赛
	private Date createTime;//创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
