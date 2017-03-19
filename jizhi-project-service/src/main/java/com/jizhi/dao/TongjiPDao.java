package com.jizhi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.TongjiP;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("ds_all_tongji")
public class TongjiPDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addTongjiPerson(TongjiP tongjip) {
		this.sqlSession.insert("tongjip.insert", tongjip);
	}
	
	public void update(TongjiP tongjip) {
		this.sqlSession.update("tongjip.update",tongjip);
	}
	
	public TongjiP getByPhoneAndType(String phone,int type) {
		TongjiP u = new TongjiP();
		u.setPhone(phone);
		Map param = new HashMap();
		param.put("tbinedex", u.getTbinedex());
		param.put("phone", phone);
		param.put("type", type);
		return this.sqlSession.selectOne("tongjip.queryByPhoneAndType",param);
	}
	
	public List<TongjiP> getByPhone(String phone) {
		TongjiP u = new TongjiP();
		u.setPhone(phone);
		Map param = new HashMap();
		param.put("tbinedex", u.getTbinedex());
		param.put("phone", phone);
		return this.sqlSession.selectList("tongjip.queryByPhone",param);
	}
}
