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
	
	public List<Race> queryRaceList(String name,int type, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceDao.queryList(name,type, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(String name,int type) {
		return raceDao.queryCount(name, type);
	}
	
	public PageResult getRacePageResult(String name,int type,int pageIndex,int pageSize) {
		List<Race> races = queryRaceList(name,type,pageIndex,pageSize);
		int count = queryCount(name,type);
		return new PageResult(count,pageSize,pageIndex,races);
	}
	
	public void addRace(Race race) {
		raceDao.addRace(race);
	}
	
	public Race queryById(int id) {
		return raceDao.queryById(id);
	}
	
	public void updateRace(Race race) {
		raceDao.updateRace(race);
	}
}
