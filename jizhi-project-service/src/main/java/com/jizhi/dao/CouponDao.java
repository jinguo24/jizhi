package com.jizhi.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jizhi.model.Coupon;
import com.simple.common.mybatis.annotation.DatabaseTemplate;
import com.simple.common.mybatis.dao.BaseIbatisDao;

@Repository
@DatabaseTemplate("st_all")
public class CouponDao extends BaseIbatisDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void addCoupon(Coupon coupon) {
		this.sqlSession.insert("coupon.insert", coupon);
	}
	
	public void updateStatus(Coupon coupon) {
		coupon.setUseTime(new Date());
		this.sqlSession.update("coupon.updateUseStatus",coupon);
	}
	
	public List<Coupon> getCouponList(String phone,int status,int pageIndex,int pageSize) {
		Coupon c = new Coupon();
		c.setPhone(phone);
		Map param = new HashMap();
		param.put("tbinedex", c.getTbinedex());
		param.put("phone", phone);
		param.put("status", status);
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		param.put("begin", (pageIndex-1)*pageSize);
		param.put("size", pageSize);
		return this.sqlSession.selectList("coupon.query",param);
	}
	
	public Coupon getCouponById(String phone,int id) {
		Coupon c = new Coupon();
		c.setPhone(phone);
		Map param = new HashMap();
		param.put("tbinedex", c.getTbinedex());
		param.put("id", id);
		return this.sqlSession.selectOne("coupon.queryById",param);
	}
	
	public Coupon getCouponByPhoneAndDate(String phone,String date) {
		Coupon c = new Coupon();
		c.setPhone(phone);
		Map param = new HashMap();
		param.put("tbinedex", c.getTbinedex());
		param.put("phone", phone);
		param.put("createDate", date);
		return this.sqlSession.selectOne("coupon.queryByPhoneAndDate",param);
	}
	
}
