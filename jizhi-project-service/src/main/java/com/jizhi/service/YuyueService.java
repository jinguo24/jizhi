package com.jizhi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.WxUserDao;
import com.jizhi.dao.YuyueActivityDao;
import com.jizhi.dao.YuyueActivityJoinDao;
import com.jizhi.dao.YuyueUserActivityDao;
import com.jizhi.model.WxUser;
import com.jizhi.model.YuyueActivity;
import com.jizhi.model.YuyueActivityJoin;
import com.jizhi.model.YuyueUserActivity;

@Service
public class YuyueService {

	@Autowired
	private WxUserDao wxUserDao;
	@Autowired
	private YuyueUserActivityDao yuyueUserActivityDao;
	@Autowired
	private YuyueActivityJoinDao yuyueActivityJoinDao;
	@Autowired
	private YuyueActivityDao yuyueActivityDao;
	
	
	public void addWxUser(WxUser wxUser) {
		wxUserDao.addWxUser(wxUser);
	}
	
	public WxUser getWxUserByOpenId(String openId) {
		return wxUserDao.getByOpenId(openId);
	}
	
	public void updateWxUserPhone(String phone,String openId) {
		wxUserDao.updatePhone(phone, openId);
	}
	
	public void updateWxInfo(WxUser wxUser) {
		wxUserDao.updateWxInfo(wxUser);
	}
	
	public void addYuyueActivity(YuyueUserActivity yua) {
		yua.setCreateTime(new Date());
		//TODO 根据创建时间来计算截止报名时间,这个礼拜的礼拜五中午12点，或者下个礼拜五的中午12点
		yua.setDeadLineTime(deadLineTime);
		yua.setEndTime(endTime);
		yuyueUserActivityDao.addYuyueActivity(yua);
		YuyueActivity ya = new YuyueActivity();
		ya.setActivityId(yua.getId());
		ya.setCreateTime(yua.getCreateTime());
		ya.setDeadLineTime(yua.getDeadLineTime());
		ya.setEndTime(yua.getEndTime());
		ya.setName(yua.getName());
		ya.setStatus(yua.getStatus());
		yuyueActivityDao.addYuyueActivity(ya);
		
	}
	
	public List<YuyueUserActivity> queryYuyueActivityList(String openId,int status,int pageIndex,int pageSize) {
		if (pageIndex<1) {
			pageIndex = 1;
		}
		return yuyueUserActivityDao.getListByOpenId(openId, status, (pageIndex-1)*pageSize, pageSize);
	}
	
	public YuyueUserActivity queryYuyueActivity(String openId,String id) {
		return yuyueUserActivityDao.getYuyueActivityById(openId, id);
	}
	
	public Integer queryYuyueActivityJoinCounts(String activityId,String openId) {
		return yuyueActivityJoinDao.queryCountByOpenId(activityId, openId);
	}
	
	public List<YuyueActivityJoin> queryYuyueActivityJoins(String activityId,int pageIndex,int pageSize) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		return yuyueActivityJoinDao.getByActivityId(activityId, (pageIndex-1)*pageSize, pageSize);
	}
	
	public void addYuyueActivityJoin(YuyueActivityJoin yuyueActivityJoin) {
		yuyueActivityJoinDao.addYuyueActivity(yuyueActivityJoin);
	}
	
	public void increaseActivityJoinCount(String activityId) {
		yuyueActivityDao.increaseCount(activityId);
	}
	
	
}
