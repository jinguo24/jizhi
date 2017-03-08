package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.RaceSchedule;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RaceScheduleDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRaceSchedule(RaceSchedule raceSchedule) {
		this.sqlSession.insert("raceSchedule.insert", raceSchedule);
	}
	
	public void updateRaceSchedule(RaceSchedule raceSchedule) {
		this.sqlSession.update("raceSchedule.update",raceSchedule);
	}
	
	public List<RaceSchedule> queryByPeriods(int periodsId) {
		Map param = new HashMap();
		param.put("racePeriodsId", periodsId);
		return this.sqlSession.selectList("race.queryById",param);
	}
}
