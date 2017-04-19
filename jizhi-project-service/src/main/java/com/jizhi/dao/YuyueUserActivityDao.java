package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.YuyueUserActivity;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_wx")
public class YuyueUserActivityDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addYuyueActivity(YuyueUserActivity yuyueActivity) {
		this.sqlSession.insert("yuyueUserActivity.insert", yuyueActivity);
	}
	
	public List<YuyueUserActivity> getListByOpenId(String openId,int status,int begin,int size) {
		YuyueUserActivity a = new YuyueUserActivity();
		a.setOpenId(openId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("openId", openId);
		param.put("status", status);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("yuyueUserActivity.queryByOpenId",param);
	}
	
	public void updateStatus(YuyueUserActivity ya) {
		this.sqlSession.update("yuyueUserActivity.updateStatus",ya);
	}
	
	public YuyueUserActivity getYuyueActivityById(String openId,String id) {
		YuyueUserActivity a = new YuyueUserActivity();
		a.setOpenId(openId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("openId", openId);
		param.put("id", id);
		return this.sqlSession.selectOne("yuyueUserActivity.queryById",param);
	}
	
	
	
}
