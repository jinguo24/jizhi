package com.jizhi.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.RacePersonApply;
import com.jizhi.model.TeamRaceApply;
import com.jizhi.model.YuyueActivity;
import com.jizhi.service.UserService;
import com.jizhi.service.YuyueService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;
import com.simple.common.util.PrimaryKeyUtil;

@Controller
@RequestMapping(value = "/yuyue")
public class YuyueController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private YuyueService yuyueService;
	
	/**
	 * 处理时间
	 * @param binder
	 */
	@InitBinder  
	protected  void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(int status,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult teams = yuyueService.getActivityPageResult(status, page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", teams);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(String id,HttpServletRequest request, HttpServletResponse response) {
		try {
			YuyueActivity ya = yuyueService.queryActivityById(id);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", ya); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "add",method=RequestMethod.POST)
	@ResponseBody
	public String add(YuyueActivity ya,HttpServletRequest request, HttpServletResponse response) {
		try {
			ya.setId(PrimaryKeyUtil.getUUID());
			ya.setSurplus(ya.getMaxAllowed());
			yuyueService.addYuyueActivity(ya);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	@ResponseBody
	public String update(YuyueActivity ya,HttpServletRequest request, HttpServletResponse response) {
		try {
			yuyueService.updateYuyueActivity(ya);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"更新失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public String deletePerson(String id,int status,HttpServletRequest request, HttpServletResponse response) {
		try {
			yuyueService.updateYuyueActivityStatus(id, status);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"更新失败:"+e.getLocalizedMessage(), null);
		}
	}
}
