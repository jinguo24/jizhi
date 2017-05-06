package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.PersonRaceApplyNoTeam;
import com.jizhi.model.RacePersonApplyNoTeam;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class PersonRaceApplyNoTeamDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addPersonRaceApplyNoTeam(PersonRaceApplyNoTeam personRaceApply) {
		this.sqlSession.insert("personRaceApplyNoTeam.insert", personRaceApply);
	}
	
	public PersonRaceApplyNoTeam getByRaceAndPhone(int raceId,String phone) {
		PersonRaceApplyNoTeam u = new PersonRaceApplyNoTeam();
		u.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", u.getTbindex());
		param.put("phone", phone);
		param.put("raceId", raceId);
		return this.sqlSession.selectOne("personRaceApplyNoTeam.queryByRaceAndPhone",param);
	}
	
	public List<PersonRaceApplyNoTeam> queryList(String phone,String remark,int begin,int size) {
		RacePersonApplyNoTeam u = new RacePersonApplyNoTeam();
		u.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", u.getTbindex());
		param.put("remark", remark);
		param.put("phone", phone);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("personRaceApplyNoTeam.query",param);
	}
	
	public void delete(String phone,int id) {
		RacePersonApplyNoTeam u = new RacePersonApplyNoTeam();
		u.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", u.getTbindex());
		param.put("phone", phone);
		param.put("id", id);
		this.sqlSession.delete("personRaceApplyNoTeam.deleteById",param);
	}
}
