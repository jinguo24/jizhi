package com.jizhi.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloopen.rest.demo.SDKTestSendTemplateSMS;
import com.jizhi.model.User;
import com.jizhi.service.SysConfigService;
import com.jizhi.service.UserService;
import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.filter.LoginUserUtil;
import com.simple.common.util.AjaxWebUtil;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	private static final String PRE = "【极指】";
	
	@RequestMapping(value = "sendSms",method=RequestMethod.POST)
	@ResponseBody
	public String sendSms(String phone,HttpServletRequest request, HttpServletResponse response) {
		try {
			String validateCode = getValidateCode();
			LocalCache.setCache(phone, validateCode);
			String templateId = sysConfigService.getConfigValue("sys_sms_templateId");
			SDKTestSendTemplateSMS.sendSms(phone, templateId, new String[]{validateCode});
			//SmsResult sr = SmsClient.sendMsg(PRE,phone, "验证码:"+validateCode);
			//if (sr.isSuccess()) {
				//return  AjaxWebUtil.sendAjaxResponse(request, response, true,"获取验证码成功", sr.getMsg());
			//}else {
				//return  AjaxWebUtil.sendAjaxResponse(request, response, false,"获取验证码失败:"+validateCode, sr.getMsg());
			//}
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"获取验证码成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"获取验证码失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	private String getValidateCode(){
		int[] chars = {0,1,2,3,4,5,6,7,8,9};
		String a = new String();
		for (int i = 0; i < 6; i++) {
			a += chars[(int)(Math.random()*10)];
		}
		return a;
	}
	
	@RequestMapping(value = "doLogin",method=RequestMethod.POST)
	@ResponseBody
	public String doLogin(String phone,String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(code)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"验证码不能为空", null);
			}
			boolean valid = LocalCache.codeValid(phone, code);
			if(!valid){
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"验证码错误", null);
			}
			User u = userService.getUser(phone);
			if ( null == u) {
				u = new User();
				u.setPhone(phone);
				u.setCreateTime(new Date());
				userService.addUser(u);
			}
			LoginUserUtil.setCurrentUser(request, u);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"登录成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"登录失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "login",method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		return EnvPropertiesConfiger.getValue("loginPage");
	}
}
