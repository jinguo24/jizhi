package com.jizhi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jizhi.dao.CouponDao;
import com.jizhi.model.Coupon;
@Service
public class CouponService {

	@Autowired
	private CouponDao couponDao;
	
	public void addCoupon(Coupon coupon) {
		couponDao.addCoupon(coupon);
	}
	
	public List<Coupon> getCouponList(String phone,int status,int pageIndex,int pageSize) {
		return couponDao.getCouponList(phone, status, pageIndex, pageSize);
	}
	
	public Coupon getCoupon(String phone,String id) {
		return couponDao.getCouponById(phone, id);
	}
	
	public Coupon getCouponByDate(String phone, String date) {
		return couponDao.getCouponByPhoneAndDate(phone, date);
	}
	
	public void updateCouponUse(Coupon coupon) {
		couponDao.updateStatus(coupon);
	}
	
}
