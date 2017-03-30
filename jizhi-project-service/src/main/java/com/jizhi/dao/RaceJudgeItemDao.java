package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.RaceJudgeItem;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RaceJudgeItemDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRaceJudgeItem(RaceJudgeItem race) {
		this.sqlSession.insert("raceJudgeItem.insert", race);
	}
	
	public void updateRaceJudgeItem(RaceJudgeItem race) {
		this.sqlSession.update("raceJudgeItem.update",race);
	}
	
	public RaceJudgeItem queryById(int id) {
		return this.sqlSession.selectOne("raceJudgeItem.queryById",id);
	}
	
	public List<RaceJudgeItem> queryList(int scale,int type,int status,String position) {
		Map param = new HashMap();
		param.put("scale", scale);
		param.put("type", type);
		param.put("status", status);
		param.put("position", position);
		return this.sqlSession.selectList("raceJudgeItem.query",param);
	}
	
	public void updateStatus(int id,int status) {
		Map param = new HashMap();
		param.put("id", id);
		param.put("status", status);
		this.sqlSession.update("raceJudgeItem.updateStatus",param);
	}
	
}
