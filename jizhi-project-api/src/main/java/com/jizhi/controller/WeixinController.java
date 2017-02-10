package com.jizhi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.AjaxWebUtil;
import com.simple.weixin.auth.JsConfigInfo;
import com.simple.weixin.auth.WeiXinAuth;

@Controller
@RequestMapping(value = "/wx")
public class WeixinController {

	@RequestMapping(value = "homeTicket",method=RequestMethod.GET)
	@ResponseBody
	public String homeTicket(HttpServletRequest request, HttpServletResponse response) {
		try {
			JsConfigInfo config = WeiXinAuth.getJsConfigInfo(EnvPropertiesConfiger.getValue("homeUrl"));
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", config);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "validateTicket",method=RequestMethod.GET)
	@ResponseBody
	public String validateTicket(HttpServletRequest request, HttpServletResponse response) {
		try {
			JsConfigInfo config = WeiXinAuth.getJsConfigInfo(EnvPropertiesConfiger.getValue("validatePageUrl"));
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", config);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "couponTicket",method=RequestMethod.GET)
	@ResponseBody
	public String couponTicket(String id,String phone,String status,HttpServletRequest request, HttpServletResponse response) {
		try {
			JsConfigInfo config = WeiXinAuth.getJsConfigInfo(String.format(EnvPropertiesConfiger.getValue("couponPageUrl"),id,phone,status));
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", config);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "yardTicket",method=RequestMethod.GET)
	@ResponseBody
	public String yardTicket(HttpServletRequest request, HttpServletResponse response) {
		try {
			JsConfigInfo config = WeiXinAuth.getJsConfigInfo(EnvPropertiesConfiger.getValue("yardPageUrl"));
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", config);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
}
