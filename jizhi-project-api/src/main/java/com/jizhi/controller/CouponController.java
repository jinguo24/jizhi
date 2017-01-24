package com.jizhi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jizhi.model.Coupon;
import com.jizhi.service.CouponService;
import com.simple.common.filter.LoginUserUtil;
import com.simple.common.util.AjaxWebUtil;

@Controller
@RequestMapping(value = "/coupon")
public class CouponController {

	@Autowired
	CouponService couponService;
	
	@RequestMapping(value = "mylist",method=RequestMethod.GET)
	public ModelAndView mylist(int page,int status,HttpServletRequest request, HttpServletResponse response) {
		try {
			String phone = LoginUserUtil.getCurrentUser(request);
			List<Coupon> coupons = couponService.getCouponList(phone, status, page, 10);
			AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", coupons);
		}catch(Exception e) {
			e.printStackTrace();
			AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败", e.getLocalizedMessage());
		}
		return null;
	}
	
}
