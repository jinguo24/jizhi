package com.jizhi.dao;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.TongjiT;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_tongji")
public class TongjiTDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTongjiTeam(TongjiT tongjit) {
		this.sqlSession.insert("tongjit.insert", tongjit);
	}
	
	public void update(TongjiT tongjit) {
		this.sqlSession.update("tongjit.update",tongjit);
	}
	
	public TongjiT getById(String teamId) {
		Map param = new HashMap();
		param.put("teamId", teamId);
		return this.sqlSession.selectOne("tongjit.queryById",param);
	}
}
