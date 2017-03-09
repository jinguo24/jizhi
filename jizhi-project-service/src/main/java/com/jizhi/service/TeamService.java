package com.jizhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.TeamDao;
import com.jizhi.dao.TeamMemberDao;
import com.jizhi.model.Team;
import com.jizhi.model.TeamMembers;
@Service
public class TeamService {

	@Autowired
	private TeamDao teamDao;
	@Autowired
	private TeamMemberDao teamMemeberDao;
	
	public void addTeam(Team team) {
		teamDao.addTeam(team);
	}
	
	public Team getById(String id) {
		return teamDao.queryById(id);
	}
	
	public void addTeamMember(TeamMembers teamMember) {
		teamMemeberDao.addTeamMembers(teamMember);
	}
	
}
