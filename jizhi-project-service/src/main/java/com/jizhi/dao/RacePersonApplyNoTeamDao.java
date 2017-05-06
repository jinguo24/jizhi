package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.RacePersonApplyNoTeam;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RacePersonApplyNoTeamDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRacePersonApplyNoTeam(RacePersonApplyNoTeam racePersonApply) {
		this.sqlSession.insert("racePersonApplyNoTeam.insert", racePersonApply);
	}
	
	public RacePersonApplyNoTeam getByRaceAndPhone(int raceId,String phone) {
		RacePersonApplyNoTeam u = new RacePersonApplyNoTeam();
		u.setRaceId(raceId);
		Map param = new HashMap();
		param.put("tbindex", u.getTbindex());
		param.put("phone", phone);
		param.put("raceId", raceId);
		return this.sqlSession.selectOne("racePersonApplyNoTeam.queryByRaceAndPhone",param);
	}
	
	public List<RacePersonApplyNoTeam> queryList(int raceId,String remark,int begin,int size) {
		RacePersonApplyNoTeam u = new RacePersonApplyNoTeam();
		u.setRaceId(raceId);
		Map param = new HashMap();
		param.put("tbindex", u.getTbindex());
		param.put("remark", remark);
		param.put("raceId", raceId);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("racePersonApplyNoTeam.query",param);
	}
	
	public void delete(int raceId,int id) {
		RacePersonApplyNoTeam u = new RacePersonApplyNoTeam();
		u.setRaceId(raceId);
		Map param = new HashMap();
		param.put("tbindex", u.getTbindex());
		param.put("raceId", raceId);
		param.put("id", id);
		this.sqlSession.delete("racePersonApplyNoTeam.deleteById",param);
	}
}
