package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.RaceResults;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RaceResultsDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRaceResults(RaceResults raceResults) {
		this.sqlSession.insert("raceResults.insert", raceResults);
	}
	
	public void updateRaceResults(RaceResults raceResults) {
		this.sqlSession.update("raceResults.update",raceResults);
	}
	
	public List<RaceResults> query(int scheduleTeamId,int begin,int size) {
		Map param = new HashMap();
		RaceResults rr = new RaceResults();
		rr.setRaceScheduleTeamId(scheduleTeamId);
		param.put("raceScheduleTeamId", scheduleTeamId);
		param.put("tbinedex", rr.getTbinedex());
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("raceResults.query",param);
	}
	
	public Integer queryCount(int scheduleTeamId) {
		Map param = new HashMap();
		RaceResults rr = new RaceResults();
		rr.setRaceScheduleTeamId(scheduleTeamId);
		param.put("raceScheduleTeamId", scheduleTeamId);
		param.put("tbinedex", rr.getTbinedex());
		return this.sqlSession.selectOne("raceResults.queryCount",param);
	}
	
	public RaceResults queryById(int scheduleTeamId, int id) {
		Map param = new HashMap();
		RaceResults rr = new RaceResults();
		rr.setRaceScheduleTeamId(scheduleTeamId);
		param.put("raceScheduleTeamId", scheduleTeamId);
		param.put("id", id);
		param.put("tbinedex", rr.getTbinedex());
		return this.sqlSession.selectOne("raceResults.queryById",param);
	}
	
	public void deleteById(int scheduleTeamId, int id) {
		Map param = new HashMap();
		RaceResults rr = new RaceResults();
		rr.setRaceScheduleTeamId(scheduleTeamId);
		param.put("raceScheduleTeamId", scheduleTeamId);
		param.put("id", id);
		param.put("tbinedex", rr.getTbinedex());
		this.sqlSession.delete("raceResults.delete",param);
	}
}
