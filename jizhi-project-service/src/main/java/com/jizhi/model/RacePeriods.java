package com.jizhi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 赛事周期
 * @author zhengfy1
 */
public class RacePeriods implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private int number;//第几期
	private int raceId;//赛事编号
	private Date createTime;//创建时间
	private Date beginDate;//开始时间
	private Date endDate;//结束时间
	private String remark;//备注
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
