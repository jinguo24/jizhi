package com.jizhi.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.Race;
import com.jizhi.model.TeamAwards;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class TeamAwardsDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTeamAwards(TeamAwards teamAwards) {
		teamAwards.setCreateTime(new Date());
		this.sqlSession.insert("teamAwards.insert", teamAwards);
	}
	
	public void updateTeamAwards(TeamAwards teamAwards) {
		this.sqlSession.update("teamAwards.update",teamAwards);
	}
	
	public List<TeamAwards> queryList(String teamId,int begin,int size) {
		Map param = new HashMap();
		param.put("teamId", teamId);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("teamAwards.query",param);
	}
	
	public Integer queryCount(String teamId) {
		Map param = new HashMap();
		param.put("teamId", teamId);
		return this.sqlSession.selectOne("teamAwards.queryCount",param);
	}
	
}
