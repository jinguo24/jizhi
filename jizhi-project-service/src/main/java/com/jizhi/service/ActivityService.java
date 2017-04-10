package com.jizhi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.ActivityDao;
import com.jizhi.dao.ActivityPersonDao;
import com.jizhi.dao.UserActivityDao;
import com.jizhi.model.Activity;
import com.jizhi.model.ActivityPerson;
import com.jizhi.model.UserActivity;
@Service
public class ActivityService {

	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private ActivityPersonDao activityPersonDao;
	@Autowired
	private UserActivityDao userActivityDao;
	
	public List<Activity> getOwnerActivites(String phone,int pageIndex,int pageSize) {
		if (pageIndex<=0) {
			pageIndex  = 1;
		}
		return activityDao.getActivityList(phone, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Activity getActivityByPhoneAndId(String phone,String id) {
		return activityDao.getByPhoneAndId(phone, id);
	}
	
	public void addActivity(Activity activity) {
		activityDao.addActivity(activity);
		
		ActivityPerson ap = new ActivityPerson();
		ap.setActivityId(activity.getId());
		ap.setCreateTime(new Date());
		ap.setName(activity.getOwnerName());
		ap.setPhone(activity.getOwnerPhone());
		ap.setIsOwner(1);
		activityPersonDao.addActivityPerson(ap);
		
		UserActivity ua = new UserActivity();
		ua.setActivityId(activity.getId());
		ua.setActivityName(activity.getName());
		ua.setAddress(activity.getAddress());
		ua.setBeginTime(activity.getBeginTime());
		ua.setEndTime(activity.getEndTime());
		ua.setOwnerName(activity.getOwnerName());
		ua.setOwnerPhone(activity.getOwnerPhone());
		ua.setPhone(activity.getOwnerPhone());
		userActivityDao.addUserActivity(ua);
	}
	
	public void addMember(String activityId,String ownerPhone,String phone,String name) {
		Activity activity = getActivityByPhoneAndId(ownerPhone,activityId);
		ActivityPerson ap = new ActivityPerson();
		ap.setActivityId(activity.getId());
		ap.setCreateTime(new Date());
		ap.setName(activity.getOwnerName());
		ap.setPhone(activity.getOwnerPhone());
		ap.setIsOwner(0);
		activityPersonDao.addActivityPerson(ap);
		
		UserActivity ua = new UserActivity();
		ua.setActivityId(activity.getId());
		ua.setActivityName(activity.getName());
		ua.setAddress(activity.getAddress());
		ua.setBeginTime(activity.getBeginTime());
		ua.setEndTime(activity.getEndTime());
		ua.setOwnerName(activity.getOwnerName());
		ua.setOwnerPhone(activity.getOwnerPhone());
		ua.setPhone(activity.getOwnerPhone());
		userActivityDao.addUserActivity(ua);
	}
	
	public List<ActivityPerson> getPersons(String activityId,int pageIndex,int pageSize) {
		if (pageIndex<=0) {
			pageIndex  = 1;
		}
		return activityPersonDao.getPersonList(activityId, (pageIndex-1)*pageSize, pageSize);
	}
	
	public List<UserActivity> getActivites(String phone,int pageIndex,int pageSize) {
		if (pageIndex<=0) {
			pageIndex  = 1;
		}
		return userActivityDao.getActivityList(phone, (pageIndex-1)*pageSize, pageSize);
	}
	
	public UserActivity getUserActivity(String activityId,String phone) {
		return  userActivityDao.getUserActivity(phone, activityId);
	}
}
