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
	
	public List<RaceScheduleTeam> queryBySchedule(int scheduleId) {
		Map param = new HashMap();
		param.put("schedule", scheduleId);
		return this.sqlSession.selectList("race.queryById",param);
	}
}
