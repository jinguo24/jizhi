package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceScheduleTeamDao;
import com.jizhi.model.RaceScheduleTeam;
import com.simple.common.util.PageResult;
@Service
public class RaceScheduleTeamService {

	@Autowired
	private RaceScheduleTeamDao raceScheduleTeamDao;
	
	public List<RaceScheduleTeam> queryList(int raceId, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceScheduleTeamDao.query(raceId,(pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(int raceId) {
		return raceScheduleTeamDao.queryCount(raceId);
	}
	
	public PageResult getRacePageResult(int raceId,int pageIndex,int pageSize) {
		List<RaceScheduleTeam> races = queryList(raceId,pageIndex,pageSize);
		int count = queryCount(raceId);
		return new PageResult(count,pageSize,pageIndex,races);
	}
	
	public void addRacePageResult(RaceScheduleTeam race) {
		raceScheduleTeamDao.addRaceScheduleTeam(race);
	}
	
	public RaceScheduleTeam queryById(int id) {
		return raceScheduleTeamDao.queryTeamById(id);
	}
	
	public void updateRaceScheduleTeam(RaceScheduleTeam race) {
		raceScheduleTeamDao.updateRaceScheduleTeam(race);
	}
	
	public void delete(int id) {
		raceScheduleTeamDao.deleteById(id);
	}
}
