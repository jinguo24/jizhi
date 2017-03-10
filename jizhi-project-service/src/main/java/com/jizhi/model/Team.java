package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 球队
 * @author zhengfy1
 */
public class Team implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;//名称
	private String image;//队标
	private String leaderPhone;//领队电话
	private String description;//描述
	private int type;//1-足球队 2-羽毛球队 3-篮球队
	private Date createTime;//创建时间
	private int status=1 ;//状态 1-审核中 2-审核通过 3-审核拒绝
	private String remark;//审核备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLeaderPhone() {
		return leaderPhone;
	}
	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
