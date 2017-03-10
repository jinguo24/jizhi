package com.jizhi.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.Race;
import com.jizhi.model.RacePeriods;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RacePeriodsDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRacePeriods(RacePeriods racePeriods) {
		racePeriods.setCreateTime(new Date());
		this.sqlSession.insert("racePeriods.insert", racePeriods);
	}
	
	public void updateRace(RacePeriods raracePeriodsce) {
		this.sqlSession.update("racePeriods.update",raracePeriodsce);
	}
	
	public RacePeriods queryById(int id) {
		return this.sqlSession.selectOne("racePeriods.queryById",id);
	}
	
	public List<RacePeriods> queryList(int raceId,int begin,int size) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("racePeriods.query",param);
	}
	
	public Integer queryCount(int raceId) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		return this.sqlSession.selectOne("racePeriods.queryCount",param);
	}
	
	public void deleteById(int id) {
		this.sqlSession.delete("racePeriods.delete",id);
	}
	
	
}
