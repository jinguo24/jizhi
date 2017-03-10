package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RaceScheduleDao;
import com.jizhi.dao.RaceScheduleTeamDao;
import com.jizhi.model.RaceSchedule;
import com.jizhi.model.RaceScheduleTeam;
import com.simple.common.util.PageResult;
@Service
public class RaceScheduleService {

	@Autowired
	private RaceScheduleDao raceScheduleDao;
	@Autowired
	private RaceScheduleTeamDao raceScheduleTeamDao;
	
	public List<RaceSchedule> queryRaceScheduleList(int racePeriodsId,String name, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceScheduleDao.query(racePeriodsId, name, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(int racePeriodsId,String name) {
		return raceScheduleDao.queryCount(racePeriodsId, name);
	}
	
	public PageResult getRacePageResult(int racePeriodsId,String name,int pageIndex,int pageSize) {
		List<RaceSchedule> raceSchedules = queryRaceScheduleList(racePeriodsId,name,pageIndex,pageSize);
		int count = queryCount(racePeriodsId,name);
		return new PageResult(count,pageSize,pageIndex,raceSchedules);
	}
	
	public void addRaceSchedule(RaceSchedule raceSchedule) {
		raceScheduleDao.addRaceSchedule(raceSchedule);
	}
	
	public void updateRaceSchedule(RaceSchedule raceSchedule) {
		raceScheduleDao.updateRaceSchedule(raceSchedule);
	}
	
	public RaceSchedule queryById(int id) {
		return raceScheduleDao.queryById(id);
	}
	
	public void deleteById(int id) {
		raceScheduleDao.deleteById(id);
	}
	
	public List<RaceScheduleTeam> queryRaceScheduleTeamList(int scheduleId,int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return raceScheduleTeamDao.query(scheduleId, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryTeamCount(int scheduleId) {
		return raceScheduleTeamDao.queryCount(scheduleId);
	}
	
	public PageResult getRaceScheduleTeamPageResult(int scheduleId,int pageIndex,int pageSize) {
		List<RaceScheduleTeam> raceSchedules = queryRaceScheduleTeamList(scheduleId,pageIndex,pageSize);
		int count = queryTeamCount(scheduleId);
		return new PageResult(count,pageSize,pageIndex,raceSchedules);
	}
	
	public void addRaceScheduleTeam(RaceScheduleTeam raceScheduleTeam) {
		raceScheduleTeamDao.addRaceScheduleTeam(raceScheduleTeam);
	}
	
	public void updateRaceScheduleTeam(RaceScheduleTeam raceScheduleTeam) {
		raceScheduleTeamDao.updateRaceScheduleTeam(raceScheduleTeam);
	}
	
	public RaceScheduleTeam queryTeamById(int id) {
		return raceScheduleTeamDao.queryTeamById(id);
	}
	
	public void deleteTeamById(int id) {
		raceScheduleTeamDao.deleteById(id);
	}
	
}
