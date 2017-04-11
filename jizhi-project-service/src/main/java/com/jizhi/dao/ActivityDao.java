package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.Activity;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_activity")
public class ActivityDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addActivity(Activity activity) {
		this.sqlSession.insert("activity.insert", activity);
	}
	
	public Activity getByPhoneAndId(String phone,String id) {
		Activity a = new Activity();
		a.setOwnerPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("ownerPhone", phone);
		param.put("id", id);
		return this.sqlSession.selectOne("activity.queryByPhoneAndId",param);
	}
	
	public List<Activity> getActivityList(String phone,int pageIndex,int pageSize) {
		Activity a = new Activity();
		a.setOwnerPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("ownerPhone", phone);
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("activity.queryByPhone",param);
	}
	
	public void updateUnvalid(String phone,String id) {
		Activity a = new Activity();
		a.setOwnerPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("ownerPhone", phone);
		param.put("id", id);
		this.sqlSession.update("activity.updateUnvalid",param);
	}
}
