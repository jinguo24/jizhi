package com.jizhi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.TeamDao;
import com.jizhi.dao.TeamMemberDao;
import com.jizhi.model.Team;
import com.jizhi.model.TeamMembers;
import com.jizhi.model.Yard;
import com.simple.common.util.PageResult;
@Service
public class TeamService {

	@Autowired
	private TeamDao teamDao;
	@Autowired
	private TeamMemberDao teamMemeberDao;
	
	public void addTeam(Team team) {
		teamDao.addTeam(team);
	}
	
	public void updateTeam(Team team) {
		teamDao.updateTeam(team);
	}
	
	public Team getById(String id) {
		return teamDao.queryById(id);
	}
	
	public void delete(String id) {
		teamDao.delete(id);
		teamMemeberDao.deleteByTeamId(id);
	}
	
	public void addTeamMember(TeamMembers teamMember) {
		teamMemeberDao.addTeamMembers(teamMember);
	}
	
	public List<Team> queryTeamList(int status,int type, int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return teamDao.queryList(status,type, (pageIndex-1)*pageSize, pageSize);
	}
	
	public Integer queryCount(int status,int type) {
		return teamDao.queryCount(status, type);
	}
	
	public PageResult getTeamPageResult(int type,int status,int pageIndex,int pageSize) {
		List<Team> teams = queryTeamList(status,type,pageIndex,pageSize);
		int count = queryCount(status,type);
		return new PageResult(count,pageSize,pageIndex,teams);
	}
	
	public List<TeamMembers> queryTeamMembers(String team) {
		return teamMemeberDao.queryByTeam(team, 0, 500);
	}
	
	
}
