package com.jizhi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.TeamDao;
import com.jizhi.dao.TeamMemberDao;
import com.jizhi.dao.TeamRaceApplyDao;
import com.jizhi.model.Team;
import com.jizhi.model.TeamMembers;
import com.simple.common.util.PageResult;
@Service
public class TeamService {

	@Autowired
	private TeamDao teamDao;
	@Autowired
	private TeamMemberDao teamMemeberDao;
	@Autowired
	private TeamRaceApplyDao teamRaceApplyDao;
	
	public void addTeam(Team team) {
		teamDao.addTeam(team);
	}
	
	public void updateTeam(Team team) {
		teamDao.updateTeam(team);
	}
	
	public Team getById(String id) {
		return teamDao.queryById(id);
	}
	
	public Team getByName(String name) {
		return teamDao.queryByName(name);
	}
	
	public void delete(String id) {
		teamDao.delete(id);
		teamMemeberDao.deleteByTeamId(id);
	}
	
	public void addTeamMember(TeamMembers teamMember) {
		teamMemeberDao.addTeamMembers(teamMember);
	}
	
	public List<Team> queryTeamList(String name,int status,int type, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return teamDao.queryList(name,status,type, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(String name,int status,int type) {
		return teamDao.queryCount(name,status, type);
	}
	
	public PageResult getTeamPageResult(String name,int type,int status,int pageIndex,int pageSize) {
		List<Team> teams = queryTeamList(name,status,type,pageIndex,pageSize);
		int count = queryCount(name,status,type);
		return new PageResult(count,pageSize,pageIndex,teams);
	}
	
	public List<Team> queryTeamList(int raceId) {
		List<String> names = teamRaceApplyDao.getTeamNames(raceId);
		if (null == names || names.size() == 0) {
			return null;
		}
		return teamDao.queryByNames(names);
	}
	
	public List<TeamMembers> queryTeamMembers(String team) {
		return teamMemeberDao.queryByTeam(team, 0, 500);
	}
	
	public TeamMembers queryMembersByPhone(String team,String phone) {
		return teamMemeberDao.queryByPhone(team, phone);
	}
	
	public List<Team> queryTeamsByPhone(String phone) {
		List<String> alltemids = new ArrayList<String>();
		for (int i = 0 ; i < 10; i ++) {
			List<String> temids = teamMemeberDao.queryTeamIds(i, phone);
			if ( null != temids) {
				alltemids.addAll(temids);
			}
		}
		return teamDao.queryList(alltemids);
	}
	
}
