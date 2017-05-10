package com.jizhi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceUserSupportDao;
import com.jizhi.dao.UserRaceSupportDao;
import com.jizhi.model.RaceUserSupport;
import com.jizhi.model.UserRaceSupport;

@Service
public class UserSupportService {

	@Autowired
	private UserRaceSupportDao userSupportDao;
	@Autowired
	private RaceUserSupportDao raceUserSupportDao;
	
	public void addUserRaceSupport(String phone,String ownerPhone,int raceId) {
		UserRaceSupport urs = new UserRaceSupport();
		urs.setCreateTime(new Date());
		urs.setOwnerPhone(ownerPhone);
		urs.setPhone(phone);
		urs.setRaceId(raceId);
		userSupportDao.addUserRaceSupport(urs);
		
		RaceUserSupport rus = new RaceUserSupport();
		rus.setCreateTime(urs.getCreateTime());
		rus.setOwnerPhone(ownerPhone);
		rus.setPhone(phone);
		rus.setRaceId(raceId);
		raceUserSupportDao.addUserRaceSupport(rus);
		
	}
	
	public List<UserRaceSupport> getList(String ownerPhone,int raceId,int pageIndex,int pageSize) {
		return userSupportDao.getList(ownerPhone, raceId, pageIndex, pageSize);
	}
	
	public Integer queryCount(String ownerPhone,int raceId) {
		return userSupportDao.queryCount(ownerPhone, raceId);
	}
	
	public Integer queryUserSupportCount(String phone,int raceId,Date date) {
		return raceUserSupportDao.queryCount(phone, raceId,date);
	}
}
