package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.YuyueActivity;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_wx")
public class YuyueActivityDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addYuyueActivity(YuyueActivity yuyueActivity) {
		this.sqlSession.insert("yuyueActivity.insert", yuyueActivity);
	}
	
	public void update(YuyueActivity yuyueActivity) {
		this.sqlSession.update("yuyueActivity.update",yuyueActivity);
	}
	
	public List<YuyueActivity> query(int status,int begin,int size) {
		Map param = new HashMap();
		param.put("status", status);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("yuyueActivity.query",param);
	}
	
	public Integer queryCount(int status) {
		Map param = new HashMap();
		param.put("status", status);
		return this.sqlSession.selectOne("yuyueActivity.queryCount",param);
	}
	
	public void updateStatus(String activityId,int status) {
		Map param = new HashMap();
		param.put("id", activityId);
		param.put("status", status);
		this.sqlSession.update("yuyueActivity.updateStatus",param);
	}

	public YuyueActivity queryById(String id) {
		return this.sqlSession.selectOne("yuyueActivity.queryById",id);
	}
	
	public int updateUsed(String id) {
		return this.sqlSession.update("yuyueActivity.updateUsed",id);
	}
	
}
