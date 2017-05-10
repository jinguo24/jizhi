package com.jizhi.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.RaceUserSupport;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;
import com.simple.common.util.DateUtil;

@Repository
@DatabaseTemplate("st_all_us")
public class RaceUserSupportDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addUserRaceSupport(RaceUserSupport userRaceSupport) {
		this.sqlSession.insert("raceUserSupport.insert", userRaceSupport);
	}
	
	public List<RaceUserSupport> getList(String phone,int pageIndex,int pageSize) {
		RaceUserSupport a = new RaceUserSupport();
		a.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("phone", phone);
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("raceUserSupport.queryByPhone",param);
	}
	
	public Integer queryCount(String phone,int raceId,Date date) {
		RaceUserSupport a = new RaceUserSupport();
		a.setPhone(phone);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("phone", phone);
		param.put("raceId", raceId);
		if ( null != date) {
			param.put("createTime", DateUtil.date2String(date));
		}else {
			param.put("createTime", "");
		}
		return this.sqlSession.selectOne("raceUserSupport.queryCountByRaceAndPhone",param);
	}
}
