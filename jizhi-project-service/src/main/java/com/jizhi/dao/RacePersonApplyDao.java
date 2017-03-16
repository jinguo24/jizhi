package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.RacePersonApply;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RacePersonApplyDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRacePersonApply(RacePersonApply racePersonApply) {
		this.sqlSession.insert("racePersonApply.insert", racePersonApply);
	}
	
	public RacePersonApply getByRaceAndPhone(int raceId,String phone) {
		RacePersonApply u = new RacePersonApply();
		u.setRaceId(raceId);
		Map param = new HashMap();
		param.put("tbinedex", u.getTbinedex());
		param.put("phone", phone);
		param.put("raceId", raceId);
		return this.sqlSession.selectOne("racePersonApply.queryByPhoneAndRace",param);
	}
	
	public List<RacePersonApply> queryList(int raceId,String teamId,int begin,int size) {
		RacePersonApply u = new RacePersonApply();
		u.setRaceId(raceId);
		Map param = new HashMap();
		param.put("tbinedex", u.getTbinedex());
		param.put("teamId", teamId);
		param.put("raceId", raceId);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("racePersonApply.query",param);
	}
	
	public void delete(int raceId,int id) {
		RacePersonApply u = new RacePersonApply();
		u.setRaceId(raceId);
		Map param = new HashMap();
		param.put("tbinedex", u.getTbinedex());
		param.put("raceId", raceId);
		param.put("id", id);
		this.sqlSession.delete("racePersonApply.deleteByRaceId",param);
	}	
	
}
