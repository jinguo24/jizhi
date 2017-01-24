package com.jizhi.dao;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.jizhi.model.User;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class UserDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addUser(User user) {
		this.sqlSession.insert("user.insert", user);
	}
	
	public void update(User user) {
		this.sqlSession.update("user.update",user);
	}
	
	public User getUserByPhone(String phone) {
		User u = new User();
		u.setPhone(phone);
		Map param = new HashMap();
		param.put("tbinedex", u.getTbinedex());
		param.put("phone", phone);
		return this.sqlSession.selectOne("user.queryByPhone",param);
	}
}
