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
	
	public List<YuyueActivity> getListByOpenId(String openId,int status,int begin,int size) {
		YuyueActivity a = new YuyueActivity();
		a.setOpenId(openId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("openId", openId);
		param.put("status", status);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("yuyueActivity.queryByOpenId",param);
	}
	
	public void updateStatus(YuyueActivity ya) {
		this.sqlSession.update("yuyueActivity.updateStatus",ya);
	}
	
	public YuyueActivity getYuyueActivityById(String openId,String id) {
		YuyueActivity a = new YuyueActivity();
		a.setOpenId(openId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("openId", openId);
		param.put("id", id);
		return this.sqlSession.selectOne("yuyueActivity.queryById",param);
	}
	
	
	
}
