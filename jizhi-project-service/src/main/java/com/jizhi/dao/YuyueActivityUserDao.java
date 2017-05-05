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
	
	public List<YuyueActivityUser> getListByPhone(String phone,int begin,int size) {
		YuyueActivityUser a = new YuyueActivityUser();
		a.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("phone", phone);
		param.put("begin", begin);
		param.put("size", size);
		return this.sqlSession.selectList("yuyueActivityUser.queryByPhone",param);
	}
	
	public Integer queryCounts(String phone,List<String> activityIds) {
		YuyueActivityUser a = new YuyueActivityUser();
		a.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("phone", phone);
		param.put("activityIds", activityIds);
		return this.sqlSession.selectOne("yuyueActivityUser.queryCounts",param);
	}
}
