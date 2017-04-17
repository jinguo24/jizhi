package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.Activity;
import com.jizhi.model.WxUser;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all_wx")
public class WxUserDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addWxUser(WxUser wxUser) {
		this.sqlSession.insert("wxUser.insert", wxUser);
	}
	
	public WxUser getByOpenId(String openId) {
		WxUser a = new WxUser();
		a.setOpenId(openId);
		Map param = new HashMap();
		param.put("tbindex", a.getTbindex());
		param.put("openId", openId);
		return this.sqlSession.selectOne("wxUser.queryByOpenId",param);
	}
	
	public void updatePhone(String phone,String openId) {
		WxUser a = new WxUser();
		a.setOpenId(openId);
		a.setPhone(phone);
		this.sqlSession.update("wxUser.updatePhone",a);
	}
	
	public void updateWxInfo(WxUser wxUser) {
		this.sqlSession.update("wxUser.updatePhone",wxUser);
	}
}
