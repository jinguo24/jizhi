package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceResultsDao;
import com.jizhi.model.RaceResults;
import com.jizhi.model.RaceSchedule;
import com.jizhi.model.RaceScheduleTeam;
import com.simple.common.util.PageResult;
@Service
public class RaceResultService {

	@Autowired
	private RaceResultsDao raceResultsDao;
	
	public List<RaceResults> queryRaceResultsList(int scheduleTeamId, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceResultsDao.query(scheduleTeamId, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(int scheduleTeamId) {
		return raceResultsDao.queryCount(scheduleTeamId);
	}
	
	public PageResult getRaceResultsPageResult(int scheduleTeamId,int pageIndex,int pageSize) {
		List<RaceResults> raceSchedules = queryRaceResultsList(scheduleTeamId,pageIndex,pageSize);
		int count = queryCount(scheduleTeamId);
		return new PageResult(count,pageSize,pageIndex,raceSchedules);
	}
	
	public void addRaceResults(RaceResults raceResults) {
		raceResultsDao.addRaceResults(raceResults);
	}
	
	public void updateRaceResults(RaceResults raceResults) {
		raceResultsDao.updateRaceResults(raceResults);
	}
	
	public RaceResults queryById(int teamId,int id) {
		return raceResultsDao.queryById(teamId,id);
	}
	
	public void deleteById(int teamId,int id) {
		raceResultsDao.deleteById(teamId,id);
	}
	
}
