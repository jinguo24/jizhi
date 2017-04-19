package com.jizhi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.WxUser;
import com.jizhi.service.YuyueService;
import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.AjaxWebUtil;
import com.simple.weixin.auth.JsConfigInfo;
import com.simple.weixin.auth.OAuthUserInfo;
import com.simple.weixin.auth.WeiXinAuth;

@Controller
@RequestMapping(value = "/wx")
public class WeixinController {

	@Autowired
	private YuyueService yuyueService;
	
	@RequestMapping(value = "homeTicket",method=RequestMethod.GET)
	@ResponseBody
	public String homeTicket(String url,HttpServletRequest request, HttpServletResponse response) {
		try {
			//JsConfigInfo config = WeiXinAuth.getJsConfigInfo(EnvPropertiesConfiger.getValue("homeUrl"));
			if(url.contains("from=groupmessage") && (!url.contains("&isappinstalled=0"))) {
				url = url +"&isappinstalled=0";
			}
			JsConfigInfo config = WeiXinAuth.getJsConfigInfo(url);
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
	
	@RequestMapping(value = "mycouponTicket",method=RequestMethod.GET)
	@ResponseBody
	public String mycouponTicket(HttpServletRequest request, HttpServletResponse response) {
		try {
			JsConfigInfo config = WeiXinAuth.getJsConfigInfo(EnvPropertiesConfiger.getValue("mycouponUrl"));
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", config);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "accountMycouponTicket",method=RequestMethod.GET)
	@ResponseBody
	public String accountMycouponTicket(HttpServletRequest request, HttpServletResponse response) {
		try {
			JsConfigInfo config = WeiXinAuth.getJsConfigInfo(EnvPropertiesConfiger.getValue("accountMycouponUrl"));
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", config);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
}
