package com.jizhi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.WxUserDao;
import com.jizhi.dao.YuyueActivityDao;
import com.jizhi.model.WxUser;
import com.jizhi.model.YuyueActivity;

@Service
public class YuyueService {

	@Autowired
	private WxUserDao wxUserDao;
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
	
	public void addYuyueActivity(YuyueActivity ya) {
		ya.setCreateTime(new Date());
		//TODO 根据创建时间来计算截止报名时间,这个礼拜的礼拜五中午12点，或者下个礼拜五的中午12点
		ya.setDeadLineTime(deadLineTime);
		yuyueActivityDao.addYuyueActivity(ya);
	}
	
	public List<YuyueActivity> queryYuyueActivityList(String openId,int status,int pageIndex,int pageSize) {
		if (pageIndex<1) {
			pageIndex = 1;
		}
		return yuyueActivityDao.getListByOpenId(openId, status, (pageIndex-1)*pageSize, pageSize);
	}
	
	public YuyueActivity queryYuyueActivity(String openId,String id) {
		return yuyueActivityDao.getYuyueActivityById(openId, id);
	}
	
}
