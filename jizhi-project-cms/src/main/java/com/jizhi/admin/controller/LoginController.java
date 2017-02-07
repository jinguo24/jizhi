package com.jizhi.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.admin.filter.SysUser;
import com.jizhi.service.UserService;
import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.filter.LoginUserUtil;
import com.simple.common.util.AjaxWebUtil;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "doLogin",method=RequestMethod.POST)
	@ResponseBody
	public String doLogin(String name,String password,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(name)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"帐号不能为空", null);
			}
			
			if (StringUtils.isEmpty(password)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"密码不能为空", null);
			}
			
			String account = EnvPropertiesConfiger.getValue("admin.name");
			String pas = EnvPropertiesConfiger.getValue("admin.password");
			if (name.equals(account) && pas.equals(password)) {
				SysUser u = new SysUser();
				u.setName(name);
				LoginUserUtil.setCurrentUser(request, u);
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"登录成功", null);
			}else {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"帐号或密码错误", null);
			}
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
