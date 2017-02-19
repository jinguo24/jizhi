package com.jizhi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.Coupon;
import com.jizhi.model.User;
import com.jizhi.service.CouponService;
import com.jizhi.service.SysPhoneService;
import com.jizhi.service.UserService;
import com.simple.common.filter.LoginUserUtil;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.CookieUtils;
import com.simple.common.util.DateUtil;
import com.simple.common.util.DesEncrypt;
import com.simple.common.util.PrimaryKeyUtil;

@Controller
@RequestMapping(value = "/coupon")
public class CouponController {
	
	private static final String entryKey = "jizhicoupkey";

	@Autowired
	CouponService couponService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysPhoneService sysPhoneService;
	
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
			
			Coupon c = couponService.getCouponByDate(u.getPhone(), DateUtil.date2String(new Date()));
			if ( null != c ) {
				Map result = new HashMap();
				result.put("id", c.getId());
				result.put("token", DesEncrypt.encrypt(phone, entryKey));
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","今天已经领取券", result); 
			}else {
				c = new Coupon();
				String id = PrimaryKeyUtil.getUUID();
				c.setId(id);
				c.setCreateTime(new Date());
				c.setPhone(u.getPhone());
				c.setUseStatus(1);
				c.setCreateDate(new Date());
				couponService.addCoupon(c);
				Map result = new HashMap();
				result.put("id", c.getId());
				result.put("token", DesEncrypt.encrypt(phone, entryKey));
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", result);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "getCurrent",method=RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		try {
			String currentPhone = CookieUtils.getCookie(request, "cp");
			if ( StringUtils.isEmpty(currentPhone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String token = CookieUtils.getCookie(request, "token");
			if ( StringUtils.isEmpty(token)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String dephone = DesEncrypt.decrypt(token, entryKey);
			if (!dephone.equals(currentPhone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			//SysUser user = (SysUser) LoginUserUtil.getCurrentUser(request);
			User u = userService.getUser(currentPhone);
			if ( null == u) {
				u = new User();
				u.setPhone(currentPhone);
				u.setCreateTime(new Date());
				userService.addUser(u);
			}
			LoginUserUtil.setCurrentUser(request, u);
			
			Coupon c = couponService.getCouponByDate(u.getPhone(), DateUtil.date2String(new Date()));
			if ( null != c ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","今天已经领取券", c); 
			}else {
				c = new Coupon();
				String id = PrimaryKeyUtil.getUUID();
				c.setId(id);
				c.setCreateTime(new Date());
				c.setPhone(u.getPhone());
				c.setUseStatus(1);
				c.setCreateDate(new Date());
				couponService.addCoupon(c);
				Map result = new HashMap();
				result.put("id", c.getId());
				result.put("token", DesEncrypt.encrypt(currentPhone, entryKey));
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", result);
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
			String token = CookieUtils.getCookie(request, "token");
			if ( StringUtils.isEmpty(token)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String dephone = DesEncrypt.decrypt(token, entryKey);
			if (!dephone.equals(phone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
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
	
	@RequestMapping(value = "use",method=RequestMethod.POST)
	@ResponseBody
	public String use(String id,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(id)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请选择优惠券", null);
			}
			
			String currentPhone = CookieUtils.getCookie(request, "cp");
			if ( StringUtils.isEmpty(currentPhone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","登录失效", null);
			}
			
			String token = CookieUtils.getCookie(request, "token");
			if ( StringUtils.isEmpty(token)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String dephone = DesEncrypt.decrypt(token, entryKey);
			if (!dephone.equals(currentPhone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String[] ids = id.split(",");
			//User u = (User) LoginUserUtil.getCurrentUser(request);
			Integer count = couponService.getUnusedCountByIds(currentPhone, ids);
			if ( null == count || count != ids.length) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"存在无效优惠券", null);
			}
			
//			String currentPhone = CookieUtils.getCookie(request, "cp");
//			if (StringUtils.isEmpty(currentPhone)) {
//				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请使用管理员手机号登录", null);
//			}
//			boolean exists = sysPhoneService.isExists(currentPhone);
//			if (!exists) {
//				return AjaxWebUtil.sendAjaxResponse(request, response, false,"非管理员手机号，不能扫码", null);
//			}
//			Coupon c = couponService.getCoupon(phone, id);
//			if ( null != c && 2 != c.getUseStatus()) {
//				c.setUseStatus(2);
//				c.setUseTime(new Date());
//				couponService.updateCouponUse(c);
//				return AjaxWebUtil.sendAjaxResponse(request, response, true,"使用成功", c); 
//			}else {
//				return AjaxWebUtil.sendAjaxResponse(request, response, false,"优惠券无记录或者优惠券已使用", c);
//			}
			
			couponService.useCoupon(ids, currentPhone);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"使用成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"使用失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "my",method=RequestMethod.GET)
	@ResponseBody
	public String my(String phone,String code,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			//boolean valid = LocalCache.codeValid(phone, code);
			//if (!valid) {
			//	return AjaxWebUtil.sendAjaxResponse(request, response, false,"验证码错误", null);
			//}
			
			String currentPhone = CookieUtils.getCookie(request, "cp");
			if ( StringUtils.isEmpty(currentPhone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String token = CookieUtils.getCookie(request, "token");
			if ( StringUtils.isEmpty(token)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String dephone = DesEncrypt.decrypt(token, entryKey);
			if (!dephone.equals(currentPhone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
//			User u = userService.getUser(phone);
//			if ( null == u) {
//				u = new User();
//				u.setPhone(phone);
//				u.setCreateTime(new Date());
//				userService.addUser(u);
//			}
//			LoginUserUtil.setCurrentUser(request, u);
			List<Coupon> list = couponService.getCouponList(currentPhone, 1, page, pageSize);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", list);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "validate",method=RequestMethod.GET)
	@ResponseBody
	public String validate(String phone,String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean valid = LocalCache.codeValid(phone, code);
			if (!valid) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"验证码错误", null);
			}
			Map result = new HashMap();
			result.put("token", DesEncrypt.encrypt(phone, entryKey));
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"验证成功", result);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
}
