package com.jizhi.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.Coupon;
import com.jizhi.model.TeamRaceApply;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class TeamRaceApplyDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTeamRaceApply(TeamRaceApply teamRaceApply) {
		this.sqlSession.insert("teamRaceApply.insert", teamRaceApply);
	}
	
	public void updateStatus(TeamRaceApply teamRaceApply) {
		this.sqlSession.update("teamRaceApply.updateStatus",teamRaceApply);
	}
	
	public List<TeamRaceApply> getTeamRaceApplyList(String raceName,int status,String phone,int pageIndex,int pageSize) {
		Map param = new HashMap();
		param.put("raceName", raceName);
		param.put("leaderPhone", phone);
		param.put("status", status);
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("teamRaceApply.query",param);
	}
	
	public TeamRaceApply getTeamRaceApplyById(String id) {
		return this.sqlSession.selectOne("teamRaceApply.queryById",id);
	}
	
	public TeamRaceApply getByRaceAndTeam(int raceId,String teamName) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		param.put("teamName", teamName);
		return this.sqlSession.selectOne("teamRaceApply.queryByRaceAndTeam",param);
	}
	
	public List<TeamRaceApply> queryListByIds(List<String> ids) {
		Map param = new HashMap();
		param.put("ids", ids);
		return this.sqlSession.selectList("teamRaceApply.queryByIds",param);
	}
}
