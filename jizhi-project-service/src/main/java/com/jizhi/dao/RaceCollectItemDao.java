package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.RaceCollectItem;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class RaceCollectItemDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addRaceCollectItem(RaceCollectItem race) {
		this.sqlSession.insert("raceCollectItem.insert", race);
	}
	
	public void updateRaceCollectItem(RaceCollectItem race) {
		this.sqlSession.update("raceCollectItem.update",race);
	}
	
	public RaceCollectItem queryById(int id) {
		return this.sqlSession.selectOne("raceCollectItem.queryById",id);
	}
	
	public List<RaceCollectItem> queryList(int scale,int type,int status) {
		Map param = new HashMap();
		param.put("scale", scale);
		param.put("type", type);
		param.put("status", status);
		return this.sqlSession.selectList("raceCollectItem.query",param);
	}
	
	public void updateStatus(int raceId,int status) {
		Map param = new HashMap();
		param.put("raceId", raceId);
		param.put("status", status);
		this.sqlSession.update("raceCollectItem.updateStatus",param);
	}
	
}
