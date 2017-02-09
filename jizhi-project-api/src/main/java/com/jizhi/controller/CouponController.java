package com.jizhi.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.filter.SysUser;
import com.jizhi.model.Coupon;
import com.jizhi.model.User;
import com.jizhi.service.CouponService;
import com.jizhi.service.UserService;
import com.simple.common.filter.LoginUserUtil;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.DateUtil;
import com.simple.common.util.PrimaryKeyUtil;

@Controller
@RequestMapping(value = "/coupon")
public class CouponController {

	@Autowired
	CouponService couponService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "get",method=RequestMethod.GET)
	@ResponseBody
	public String get(String phone,String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean valid = LocalCache.codeValid(phone, code);
			if (!valid) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"验证码错误", null);
			}
			//SysUser user = (SysUser) LoginUserUtil.getCurrentUser(request);
			User u = userService.getUser(phone);
			if ( null == u) {
				u = new User();
				u.setPhone(phone);
				u.setCreateTime(new Date());
				userService.addUser(u);
			}
			LoginUserUtil.setCurrentUser(request, u);
			
			Coupon c = couponService.getCoupon(u.getPhone(), DateUtil.date2String(new Date()));
			if ( null != c ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","已经存在", c); 
			}else {
				c = new Coupon();
				String id = PrimaryKeyUtil.getUUID();
				c.setId(id);
				c.setCreateTime(new Date());
				c.setPhone(u.getPhone());
				couponService.addCoupon(c);
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", c);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(String phone,String id,HttpServletRequest request, HttpServletResponse response) {
		try {
			Coupon c = couponService.getCoupon(phone, id);
			if ( null != c ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", c); 
			}else {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"优惠券无记录", c);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "use",method=RequestMethod.GET)
	@ResponseBody
	public String use(String phone,String id,HttpServletRequest request, HttpServletResponse response) {
		try {
			Coupon c = couponService.getCoupon(phone, id);
			if ( null != c ) {
				c.setUseStatus(2);
				c.setUseTime(new Date());
				couponService.updateCouponUse(c);
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"使用成功", c); 
			}else {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"优惠券无记录", c);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"使用失败", e.getLocalizedMessage());
		}
	}
	
}
