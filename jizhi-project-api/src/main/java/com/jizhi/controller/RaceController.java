package com.jizhi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jizhi.service.CouponService;
import com.jizhi.service.SysPhoneService;
import com.jizhi.service.UserService;
import com.simple.common.util.AjaxWebUtil;

@Controller
@RequestMapping(value = "/race")
public class RaceController {
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysPhoneService sysPhoneService;
	
	@RequestMapping(value = "get",method=RequestMethod.GET)
	@ResponseBody
	public String get(String phone,String code,String validCode,HttpServletRequest request, HttpServletResponse response) {
		try {
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
}
