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
	
	public List<YuyueActivityJoin> getByActivityId(String activityUserId,int begin,int size) {
		YuyueActivityJoin a = new YuyueActivityJoin();
		a.setActivityUserId(activityUserId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("activityUserId", activityUserId);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("yuyueActivityJoin.queryByActiviyUserId",param);
	}
	
	public Integer queryCountByOpenId(String activityUserId,String openId) {
		YuyueActivityJoin a = new YuyueActivityJoin();
		a.setActivityUserId(activityUserId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("activityUserId", activityUserId);
		param.put("openId", openId);
		return this.sqlSession.selectOne("yuyueActivityJoin.queryCountByActivityIdAndOpenId",param);
	}
}
