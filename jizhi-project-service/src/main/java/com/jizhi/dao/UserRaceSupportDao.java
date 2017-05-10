package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.ActivityPerson;
import com.jizhi.model.UserRaceSupport;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_us")
public class UserRaceSupportDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addUserRaceSupport(UserRaceSupport userRaceSupport) {
		this.sqlSession.insert("userRaceSupport.insert", userRaceSupport);
	}
	
	public List<UserRaceSupport> getList(String ownerPhone,int raceId,int pageIndex,int pageSize) {
		UserRaceSupport a = new UserRaceSupport();
		a.setOwnerPhone(ownerPhone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("ownerPhone", ownerPhone);
		param.put("raceId", raceId);
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("userRaceSupport.queryByRaceAndPhone",param);
	}
	
	public Integer queryCount(String ownerPhone,int raceId) {
		UserRaceSupport a = new UserRaceSupport();
		a.setOwnerPhone(ownerPhone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("ownerPhone", ownerPhone);
		param.put("raceId", raceId);
		return this.sqlSession.selectOne("userRaceSupport.queryCountByRaceAndPhone",param);
	}
}
