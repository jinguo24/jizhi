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
public class SysConfigDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public String getConfigValue(String type) {
		Map param = new HashMap();
		param.put("configtype", type);
		return this.sqlSession.selectOne("sysConfig.queryValue",param);
	}
}
