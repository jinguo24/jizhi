package com.jizhi.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.UserResults;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class UserResultsDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addUser(UserResults user) {
		this.sqlSession.insert("userResults.insert", user);
	}
	
	public void update(UserResults user) {
		this.sqlSession.update("userResults.update",user);
	}
	
	public UserResults getUserByPhone(String phone) {
		UserResults u = new UserResults();
		u.setPhone(phone);
		Map param = new HashMap();
		param.put("tbinedex", u.getTbinedex());
		param.put("phone", phone);
		return this.sqlSession.selectOne("userResults.queryByPhone",param);
	}
}
