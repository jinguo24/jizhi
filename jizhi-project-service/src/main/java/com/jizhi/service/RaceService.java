package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceDao;
import com.jizhi.model.Race;
import com.simple.common.util.PageResult;
@Service
public class RaceService {

	@Autowired
	private RaceDao raceDao;
	
	public List<Race> queryRaceList(String name,int type,int status, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceDao.queryList(name,type, status,(pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(String name,int type,int status) {
		return raceDao.queryCount(name, type, status);
	}
	
	public PageResult getRacePageResult(String name,int type,int status,int pageIndex,int pageSize) {
		List<Race> races = queryRaceList(name,type,status,pageIndex,pageSize);
		int count = queryCount(name,type,status);
		return new PageResult(count,pageSize,pageIndex,races);
	}
	
	public void addRace(Race race) {
		race.setStatus(1);
		raceDao.addRace(race);
	}
	
	public Race queryById(int id) {
		return raceDao.queryById(id);
	}
	
	public void updateRace(Race race) {
		raceDao.updateRace(race);
	}
	
	public void updateStatus(int raceId,int status) {
		raceDao.updateStatus(raceId, status);
	}
}
