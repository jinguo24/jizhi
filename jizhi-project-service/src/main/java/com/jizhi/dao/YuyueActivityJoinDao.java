package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.YuyueActivityJoin;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_wx")
public class YuyueActivityJoinDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addYuyueActivity(YuyueActivityJoin yuyueActivityJoin) {
		this.sqlSession.insert("yuyueActivityJoin.insert", yuyueActivityJoin);
	}
	
	public List<YuyueActivityJoin> getByActivityId(String activityId,int begin,int size) {
		YuyueActivityJoin a = new YuyueActivityJoin();
		a.setActivityId(activityId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("activityId", activityId);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("yuyueActivityJoin.queryByActiviyId",param);
	}
	
	public YuyueActivityJoin getByActivityIdAndPhone(String activityId,String phone) {
		YuyueActivityJoin a = new YuyueActivityJoin();
		a.setActivityId(activityId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("activityId", activityId);
		param.put("phone", phone);
		return this.sqlSession.selectOne("yuyueActivityJoin.queryByActiviyIdAndPhone",param);
	}
	
}
