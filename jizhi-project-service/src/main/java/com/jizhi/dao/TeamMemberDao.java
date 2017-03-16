package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.Team;
import com.jizhi.model.TeamMembers;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class TeamMemberDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTeamMembers(TeamMembers teammembers) {
		this.sqlSession.insert("teamMembers.insert", teammembers);
	}
	
	public void updateTeamMembers(TeamMembers teammembers) {
		this.sqlSession.update("teamMembers.update",teammembers);
	}
	
	public List<TeamMembers> queryByTeam(String teamId,int begin,int size) {
		Map param = new HashMap();
		TeamMembers tm = new TeamMembers();
		tm.setTeamId(teamId);
		param.put("teamId", teamId);
		param.put("tbinedex", tm.getTbinedex());
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("teamMembers.queryByTeam",param);
	}
	
	public Integer queryCount(String teamId) {
		Map param = new HashMap();
		TeamMembers tm = new TeamMembers();
		tm.setTeamId(teamId);
		param.put("teamId", teamId);
		param.put("tbinedex", tm.getTbinedex());
		return this.sqlSession.selectOne("teamMembers.queryCount",param);
	}
	
	public void deleteByTeamId(String teamId) {
		Map param = new HashMap();
		TeamMembers tm = new TeamMembers();
		tm.setTeamId(teamId);
		param.put("teamId", teamId);
		param.put("tbinedex", tm.getTbinedex());
		this.sqlSession.delete("teamMembers.deleteByTeamId",param);
	}
	
	public Integer queryCountByPhone(String teamId,String phone) {
		Map param = new HashMap();
		TeamMembers tm = new TeamMembers();
		tm.setTeamId(teamId);
		param.put("teamId", teamId);
		param.put("tbinedex", tm.getTbinedex());
		param.put("phone", phone);
		return this.sqlSession.selectOne("teamMembers.queryCountByPhone",param);
	}
	
	public List<String> queryTeamIds(int index,String phone) {
		Map param = new HashMap();
		param.put("tbinedex", index);
		param.put("phone", phone);
		return this.sqlSession.selectList("teamMembers.queryIds",param);
	}
	
}
