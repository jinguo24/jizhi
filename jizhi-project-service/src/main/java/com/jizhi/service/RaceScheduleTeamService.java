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
	
	public List<RaceScheduleTeam> queryList(int raceId,String teamId,int type,int status, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceScheduleTeamDao.query(raceId,teamId,type,status,(pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(int raceId,String teamId,int type,int status) {
		return raceScheduleTeamDao.queryCount(raceId,teamId,type,status);
	}
	
	public PageResult getRacePageResult(int raceId,String teamId,int type,int status,int pageIndex,int pageSize) {
		List<RaceScheduleTeam> races = queryList(raceId,teamId,type,status,pageIndex,pageSize);
		int count = queryCount(raceId,teamId,type,status);
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
	
	public void updateRaceScheduleTeamStatus(int raceId,int status) {
		raceScheduleTeamDao.updateStatus(raceId, status);
	}
	
	public void updateRaceScheduleTeamResults(RaceScheduleTeam race) {
		raceScheduleTeamDao.updateRaceScheduleTeamResults(race);
	}
	
	
	public void delete(int id) {
		raceScheduleTeamDao.deleteById(id);
	}
}
