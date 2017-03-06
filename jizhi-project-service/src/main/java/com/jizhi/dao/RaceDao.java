package com.jizhi.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.Race;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RaceDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRace(Race race) {
		race.setCreateTime(new Date());
		this.sqlSession.insert("race.insert", race);
	}
	
	public void updateRace(Race race) {
		this.sqlSession.update("race.update",race);
	}
	
	public Race queryById(int id) {
		return this.sqlSession.selectOne("race.queryById",id);
	}
	
	public List<Race> queryList(String name,int type,int begin,int size) {
		Map param = new HashMap();
		param.put("name", name);
		param.put("type", type);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("race.query",param);
	}
	
	public Integer queryCount(String name,int type) {
		Map param = new HashMap();
		param.put("name", name);
		param.put("type", type);
		return this.sqlSession.selectOne("race.queryCount",param);
	}
	
	
}
