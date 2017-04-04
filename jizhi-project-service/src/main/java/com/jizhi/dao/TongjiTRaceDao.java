package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.TongjiTRace;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_tongji")
public class TongjiTRaceDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTongjiTeamRace(TongjiTRace tongjitRace) {
		this.sqlSession.insert("tongjitrace.insert", tongjitRace);
	}
	
	public void update(TongjiTRace tongjitRace) {
		this.sqlSession.update("tongjitrace.update",tongjitRace);
	}
	
	public TongjiTRace getByTeamAndRace(String teamId,int raceId) {
		TongjiTRace u = new TongjiTRace();
		u.setRaceId(raceId);
		Map param = new HashMap();
		param.put("tbindex", u.getTbindex());
		param.put("teamId", teamId);
		param.put("raceId", raceId);
		return this.sqlSession.selectOne("tongjitrace.queryByTeamAndRace",param);
	}
	
	public List<TongjiTRace> getByRaceId(int raceId) {
		Map param = new HashMap();
		TongjiTRace u = new TongjiTRace();
		u.setRaceId(raceId);
		param.put("tbindex", u.getTbindex());
		param.put("raceId", raceId);
		return this.sqlSession.selectList("tongjitrace.queryByRace",param);
	}
}
