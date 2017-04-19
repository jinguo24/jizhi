package com.jizhi.service;

import java.util.List;

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
	
	public YuyueActivity queryActivityById(String activityId) {
		return yuyueActivityDao.queryById(activityId);
	}
	
	public void addYuyueActivity(YuyueActivity ya) {
		yuyueActivityDao.addYuyueActivity(ya);
		
	}
	
	public void addYuyueActivityUser(YuyueActivityUser yau) {
		yuyueActivityUserDao.addYuyueActivityUser(yau);
	}
	
	public YuyueActivityUser queryYuyueActivityUserByOpenId(String activityId,String openId) {
		return yuyueActivityUserDao.getByOpenId(activityId, openId);
	}
	
	public List<YuyueActivityUser> queryYuyueActivityUserList(String activityId,int pageIndex,int pageSize) {
		if ( pageIndex < 1 ) {
			pageIndex = 1;
		}
		return yuyueActivityUserDao.getListByActivityId(activityId, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryYuyueActivityJoinCounts(String activityUserId,String openId) {
		return yuyueActivityJoinDao.queryCountByOpenId(activityUserId, openId);
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
	
	public void increaseActivityJoinCount(String activityId,String activiyUserId) {
		yuyueActivityUserDao.increaseJoinCount(activityId, activiyUserId);
	}
}
