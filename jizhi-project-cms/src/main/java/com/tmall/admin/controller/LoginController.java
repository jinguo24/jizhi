package com.tmall.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.admin.util.AjaxWebUtil;
import com.tmall.admin.util.LoginUserUtil;
import com.tmall.model.ResponseInfo;
import com.tmall.model.ResponseStatus;
import com.tmall.model.SysUser;
import com.tmall.service.SysUserService;
/**
 * 
 * @author zhenglong.wei
 *
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	SysUserService sysUserService;
	
	@RequestMapping(value = "login",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("login");
	}
	
	
	@RequestMapping(value = "doLogin",method=RequestMethod.POST)
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response) {
		String leaseholderId = request.getParameter("leaseholderId");
		String studentId = request.getParameter("studentId");
		SysUser user = sysUserService.querySysUser(leaseholderId,studentId);
		if(null == user ){
			if (!AjaxWebUtil.isAjaxRequest(request)) {// 不是ajax请求
				return new ModelAndView("redirect:/login/login");
			} else {
				AjaxWebUtil.sendAjaxResponse(request,response,new ResponseInfo(new ResponseStatus(false, "000001", "not exist user in the system"), "error"));
				return null;
			}
		}
		
//		if(!user.getPassword().equals(password)){
//			if (!AjaxWebUtil.isAjaxRequest(request)) {// 不是ajax请求
//				return new ModelAndView("redirect:/login/login");
//			} else {
//				AjaxWebUtil.sendAjaxResponse(request,response,new ResponseInfo(new ResponseStatus(false, "000001", "username or password is incorrect"), "error"));
//				return null;
//			}
//		}
		LoginUserUtil.setCurrentUser(request, user);
		if (!AjaxWebUtil.isAjaxRequest(request)) {// 不是ajax请求
			return new ModelAndView("redirect:/home/homepage");
		} else {
			AjaxWebUtil.sendAjaxResponse(request,response,new ResponseInfo(new ResponseStatus(true, "000000", "登录成功！"), "ok"));
			return null;
		}
		
	}
	
	@RequestMapping(value = "logout",method=RequestMethod.POST)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		LoginUserUtil.removeCurrentUser(request);
		if (!AjaxWebUtil.isAjaxRequest(request)) {// 不是ajax请求
			return new ModelAndView("redirect:/login/login");
		} else {
			AjaxWebUtil.sendAjaxResponse(request,response,new ResponseInfo(new ResponseStatus(true, "000000", "登出！"), "ok"));
			return null;
		}
	}
	
	
}
