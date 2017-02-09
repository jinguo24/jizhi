package com.jizhi.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class SysPhoneDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public Integer getCount(String phone) {
		Map param = new HashMap();
		param.put("phone", phone);
		return this.sqlSession.selectOne("sysPhone.queryCount",param);
	}
}
