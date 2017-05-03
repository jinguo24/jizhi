package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.UserRaceSupportDao;
import com.jizhi.model.UserRaceSupport;

@Service
public class UserSupportService {

	@Autowired
	private UserRaceSupportDao userSupportDao;
	
	public void addUserRaceSupport(UserRaceSupport userRaceSupport) {
		userSupportDao.addUserRaceSupport(userRaceSupport);
	}
	
	public List<UserRaceSupport> getList(String ownerPhone,int raceId,int pageIndex,int pageSize) {
		return userSupportDao.getList(ownerPhone, raceId, pageIndex, pageSize);
	}
	
	public Integer queryCount(String ownerPhone,int raceId) {
		return userSupportDao.queryCount(ownerPhone, raceId);
	}
	
	public UserRaceSupport getOne(int raceId,String ownerPhone,String phone) {
		return userSupportDao.getByRaceAndOwnerPhone(raceId, ownerPhone, phone);
	}
}
