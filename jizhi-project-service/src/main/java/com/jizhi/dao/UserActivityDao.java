package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.ActivityPerson;
import com.jizhi.model.UserActivity;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_activity")
public class UserActivityDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addUserActivity(UserActivity userAcitvity) {
		this.sqlSession.insert("userActivity.insert", userAcitvity);
	}
	
	public List<UserActivity> getActivityList(String phone,int pageIndex,int pageSize) {
		UserActivity a = new UserActivity();
		a.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("phone", phone);
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("userActivity.queryByUser",param);
	}
	
	public UserActivity getUserActivity(String phone,String activityId) {
		UserActivity a = new UserActivity();
		a.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("phone", phone);
		param.put("activityId", activityId);
		return this.sqlSession.selectOne("userActivity.queryByUserAndActivity",param);
	}
}
