package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.ActivityPerson;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_activity")
public class ActivityPersonDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addActivityPerson(ActivityPerson activityPerson) {
		this.sqlSession.insert("activityPerson.insert", activityPerson);
	}
	
	public List<ActivityPerson> getPersonList(String activityId,int pageIndex,int pageSize) {
		ActivityPerson a = new ActivityPerson();
		a.setActivityId(activityId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("activityId", activityId);
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("activityPerson.queryByActivity",param);
	}
}
