package com.jizhi.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloopen.rest.demo.SDKTestSendTemplateSMS;
import com.jizhi.service.SysConfigService;
import com.simple.common.util.AjaxWebUtil;

@Controller
@RequestMapping(value = "/msg")
public class MessageController {

	@Autowired
	private SysConfigService sysConfigService;
	
	private void sendSms(String phone,String appId,String templateId) throws Exception {
		String validateCode = getValidateCode();
		LocalCache.setCache(phone, validateCode);
		SDKTestSendTemplateSMS.sendSms(phone,appId, templateId, new String[]{validateCode});
	}
	
	@RequestMapping(value = "sendSms/lenovo",method=RequestMethod.GET)
	@ResponseBody
	public String sendSms(String phone,HttpServletRequest request, HttpServletResponse response) {
		try {
			String templateId = sysConfigService.getConfigValue("sys_sms_lenovo_templateId");
			String appId = sysConfigService.getConfigValue("sys_sms_lenovo_appId");
			sendSms(phone,appId,templateId);
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
	
}
