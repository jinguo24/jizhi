package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.Team;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class TeamDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTeam(Team team) {
		this.sqlSession.insert("team.insert", team);
	}
	
	public void updateTeam(Team team) {
		this.sqlSession.update("team.update",team);
	}
	
	public Team queryById(String id) {
		return this.sqlSession.selectOne("team.queryById",id);
	}
	
	public List<Team> queryList(int status,int begin,int size) {
		Map param = new HashMap();
		param.put("status", status);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("team.query",param);
	}
	
	public Integer queryCount(int status) {
		Map param = new HashMap();
		param.put("status", status);
		return this.sqlSession.selectOne("team.queryCount",param);
	}
	
}
