package com.jizhi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.RacePersonApplyDao;
import com.jizhi.dao.TeamRaceApplyDao;
import com.jizhi.model.RacePersonApply;
import com.jizhi.model.TeamRaceApply;
@Service
public class TeamApplyService {

	@Autowired
	private TeamRaceApplyDao teamRaceApplyDao;
	@Autowired
	private RacePersonApplyDao racePersonApplyDao;
	
	public TeamRaceApply queryTeamApply(String teamName,int raceId) {
		return teamRaceApplyDao.getByRaceAndTeam(raceId, teamName);
	}
	
	public List<TeamRaceApply> queryTeamRaceApplyList(String raceName,int status,String phone) {
		return teamRaceApplyDao.getTeamRaceApplyList(raceName, status, phone, 0, 500);
	}
	
	public void addTeamApply(TeamRaceApply teamapply) {
		teamRaceApplyDao.addTeamRaceApply(teamapply);
	}
	
	public TeamRaceApply queryTeamApplyById(String id) {
		return teamRaceApplyDao.getTeamRaceApplyById(id);
	}
	
	public RacePersonApply queryPersonApplyByPhone(int raceId,String phone) {
		return racePersonApplyDao.getByRaceAndPhone(raceId, phone);
	}
	
	public void addPersonApply(RacePersonApply racePersonApply) {
		racePersonApplyDao.addRacePersonApply(racePersonApply);
	}
	
	public List<RacePersonApply> queryPersonApplysByTeamApply(int raceId,String teamApplyId) {
		return racePersonApplyDao.queryList(raceId, teamApplyId, 0, 500);
	}
	
	public List<TeamRaceApply> queryTeamApplyList(String phone) {
		//查询所有的leader
		List<TeamRaceApply> alls = new ArrayList<TeamRaceApply>();
		List<TeamRaceApply> ts = queryTeamRaceApplyList(null,0,phone);
		if ( null != ts && ts.size() > 0 ) {
			alls.addAll(ts);
		}
		
		//查询所有成员
		List<String> alltemids = new ArrayList<String>();
		
		for (int i = 0 ; i < 10; i ++) {
			List<String> temids = racePersonApplyDao.queryTeamApplyIds(i, phone);
			if ( null != temids && temids.size() > 0 ) {
				alltemids.addAll(temids);
			}
		}
		if (alltemids.size() > 0 ) {
			List<TeamRaceApply> tss = teamRaceApplyDao.queryListByIds(alltemids);
			if ( null != tss && tss.size() > 0 ) {
				alls.removeAll(tss);
				alls.addAll(tss);
			}
		}
		return alls;
	}
	
}
