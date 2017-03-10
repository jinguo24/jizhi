package com.jizhi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceDao;
import com.jizhi.dao.RacePeriodsDao;
import com.jizhi.model.Race;
import com.jizhi.model.RacePeriods;
import com.simple.common.util.PageResult;
@Service
public class RaceService {

	@Autowired
	private RaceDao raceDao;
	@Autowired
	private RacePeriodsDao racePeriodsDao;
	
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
	
	public List<RacePeriods> queryRacePeriodsList(int raceId, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return racePeriodsDao.queryList(raceId, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryRacePeriodsCount(int raceId) {
		return racePeriodsDao.queryCount(raceId);
	}
	
	public PageResult getRacePeriodsPageResult(int raceId,int pageIndex,int pageSize) {
		List<RacePeriods> racePeriods = queryRacePeriodsList(raceId,pageIndex,pageSize);
		int count = queryRacePeriodsCount(raceId);
		return new PageResult(count,pageSize,pageIndex,racePeriods);
	}
	
	public void addRacePeriods(RacePeriods racePeriods) {
		racePeriods.setCreateTime(new Date());
		racePeriodsDao.addRacePeriods(racePeriods);
	}
	
	public void updateRacePeriods(RacePeriods racePeriods) {
		racePeriodsDao.updateRace(racePeriods);
	}
	
	public RacePeriods queryRacePeridosById(int id) {
		return racePeriodsDao.queryById(id);
	}
	
	public void deleteRacePeriods(int id ) {
		racePeriodsDao.deleteById(id);
	}
}
