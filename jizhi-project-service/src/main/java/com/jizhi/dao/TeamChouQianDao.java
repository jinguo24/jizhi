package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.TeamChouQianDetail;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class TeamChouQianDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTeamChouQianDetail(TeamChouQianDetail teamChouQian) {
		this.sqlSession.insert("teamChouQianDetail.insert", teamChouQian);
	}
	
	public TeamChouQianDetail queryByPhoneAndRace(String phone,int raceId) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		param.put("leaderPhone", phone);
		return this.sqlSession.selectOne("teamChouQianDetail.queryByRaceAndPhone",param);
	}
	
	public void updateLabel(String label,String phone,int raceId) {
		Map param = new HashMap();
		param.put("label", label);
		param.put("raceId", raceId);
		param.put("leaderPhone", phone);
		this.sqlSession.update("teamChouQianDetail.updateLabel",param);
	}
	
	public List<TeamChouQianDetail> queryDetails(int raceId) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		return this.sqlSession.selectList("teamChouQianDetail.query",param);
	}
	
	
}
