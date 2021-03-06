package com.jizhi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.WxUserDao;
import com.jizhi.dao.YuyueActivityDao;
import com.jizhi.dao.YuyueActivityJoinDao;
import com.jizhi.dao.YuyueActivityUserDao;
import com.jizhi.model.WxUser;
import com.jizhi.model.YuyueActivity;
import com.jizhi.model.YuyueActivityJoin;
import com.jizhi.model.YuyueActivityUser;
import com.simple.common.util.PageResult;

@Service
public class YuyueService {

	@Autowired
	private WxUserDao wxUserDao;
	@Autowired
	private YuyueActivityUserDao yuyueActivityUserDao;
	@Autowired
	private YuyueActivityJoinDao yuyueActivityJoinDao;
	@Autowired
	private YuyueActivityDao yuyueActivityDao;
	
	private static final Object yuyueLock = new Object();
	
	
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
	
	public PageResult getActivityPageResult(int status,int pageIndex,int pageSize) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		List<YuyueActivity> yards = yuyueActivityDao.query(status, (pageIndex-1)*pageSize, pageSize);
		int count = yuyueActivityDao.queryCount(status);
		return new PageResult(count,pageSize,pageIndex,yards);
	}
	
	
	public YuyueActivity queryActivityById(String activityId) {
		return yuyueActivityDao.queryById(activityId);
	}
	
	public void addYuyueActivity(YuyueActivity ya) {
		ya.setCreateTime(new Date());
		ya.setStatus(3);
		yuyueActivityDao.addYuyueActivity(ya);
		
	}
	
	public void updateYuyueActivity(YuyueActivity ya) {
		yuyueActivityDao.update(ya);
	}
	
	public void updateYuyueActivityStatus(String activityId,int status) {
		yuyueActivityDao.updateStatus(activityId, status);
	}
	
	public void addYuyueActivityUser(YuyueActivityUser yau) {
		yuyueActivityUserDao.addYuyueActivityUser(yau);
	}
	
	public List<YuyueActivityUser> queryYuyueActivityUserList(String phone,int pageIndex,int pageSize) {
		if ( pageIndex < 1 ) {
			pageIndex = 1;
		}
		return yuyueActivityUserDao.getListByPhone(phone, (pageIndex-1)*pageSize, pageSize);
	}
	
	public List<YuyueActivityJoin> queryYuyueActivityJoins(String activityUserId,int pageIndex,int pageSize) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		return yuyueActivityJoinDao.getByActivityId(activityUserId, (pageIndex-1)*pageSize, pageSize);
	}
	
	public void addYuyueActivityJoin(YuyueActivityJoin yuyueActivityJoin) {
		yuyueActivityJoinDao.addYuyueActivity(yuyueActivityJoin);
	}
	
	public YuyueActivityJoin queryJoinByActivityIdAndPhone(String activityId,String phone) {
		return yuyueActivityJoinDao.getByActivityIdAndPhone(activityId, phone);
	}
	
	public List<String> queryChildActivityIds(String activityId) {
		return yuyueActivityDao.queryChildIds(activityId);
	}
	
	public Integer queryUserCount(String phone,List<String> activityIds) {
		return yuyueActivityUserDao.queryCounts(phone,activityIds);
	}
	
	
	
	public boolean updatejoin(String activityId,String phone) {
		synchronized (yuyueLock) {
			int count = yuyueActivityDao.updateUsed(activityId);
			if (count>0) {
				YuyueActivityJoin yaj = new YuyueActivityJoin();
				yaj.setActivityId(activityId);
				yaj.setPhone(phone);
				yaj.setCreateTime(new Date());
				yuyueActivityJoinDao.addYuyueActivity(yaj);
				
				YuyueActivityUser yau = new YuyueActivityUser();
				yau.setActivityId(activityId);
				yau.setCreateTime(new Date());
				yau.setPhone(phone);
				yuyueActivityUserDao.addYuyueActivityUser(yau);
				return true;
			}else {
				return false;
			}
		}

	}
}
