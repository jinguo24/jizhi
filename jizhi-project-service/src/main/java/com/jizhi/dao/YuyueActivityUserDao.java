package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.YuyueActivityUser;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_wx")
public class YuyueActivityUserDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addYuyueActivityUser(YuyueActivityUser yuyueActivity) {
		this.sqlSession.insert("yuyueActivityUser.insert", yuyueActivity);
	}
	
	public List<YuyueActivityUser> getListByActivityId(String activityId,int begin,int size) {
		YuyueActivityUser a = new YuyueActivityUser();
		a.setActivityId(activityId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("activityId", activityId);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("yuyueActivityUser.queryByActivityId",param);
	}
	
	public YuyueActivityUser getByOpenId(String activityId,String openId) {
		YuyueActivityUser a = new YuyueActivityUser();
		a.setActivityId(activityId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("openId", openId);
		param.put("activityId", activityId);
		return this.sqlSession.selectOne("yuyueActivityUser.queryByOpenId",param);
	}
	
	public void increaseJoinCount(String activityId,String id) {
		YuyueActivityUser a = new YuyueActivityUser();
		a.setActivityId(activityId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("id", id);
		param.put("activityId", activityId);
		this.sqlSession.update("yuyueActivityUser.increaseCounts",param);
	}
	
	
}
