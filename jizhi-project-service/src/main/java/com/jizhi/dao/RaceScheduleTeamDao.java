package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.RaceScheduleTeam;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RaceScheduleTeamDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRaceScheduleTeam(RaceScheduleTeam raceScheduleTeam) {
		this.sqlSession.insert("raceScheduleTeam.insert", raceScheduleTeam);
	}
	
	public void updateRaceScheduleTeam(RaceScheduleTeam raceScheduleTeam) {
		this.sqlSession.update("raceScheduleTeam.update",raceScheduleTeam);
	}
	
	public List<RaceScheduleTeam> query(int raceId,String teamId,int type,int status,int begin,int size) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		param.put("teamId", teamId);
		param.put("type", type);
		param.put("status", status);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("raceScheduleTeam.query",param);
	}
	
	public Integer queryCount(int raceId,String teamId,int type,int status) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		param.put("teamId", teamId);
		param.put("type", type);
		param.put("status", status);
		return this.sqlSession.selectOne("raceScheduleTeam.queryCount",param);
	}
	
	public RaceScheduleTeam queryTeamById(int id) {
		return this.sqlSession.selectOne("raceScheduleTeam.queryById",id);
	}
	
	public void deleteById(int id) {
		this.sqlSession.delete("raceScheduleTeam.delete",id);
	}
	
	public void updateStatus(int raceId,int status) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		param.put("status", status);
		this.sqlSession.update("raceScheduleTeam.updateStatusByRaceId",param);
	}
	
	public void updateRaceScheduleTeamResults(RaceScheduleTeam raceScheduleTeam) {
		this.sqlSession.update("raceScheduleTeam.updateResults",raceScheduleTeam);
	}
}
