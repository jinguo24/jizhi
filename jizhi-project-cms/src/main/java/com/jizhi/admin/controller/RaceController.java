package com.jizhi.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.Race;
import com.jizhi.service.CouponService;
import com.jizhi.service.RaceService;
import com.jizhi.service.UserService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;

@Controller
@RequestMapping(value = "/race")
public class RaceController {
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RaceService raceService;
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(String name,int type,int status,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult races = raceService.getRacePageResult(name,type,status, page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", races);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	/**
	 * 处理时间
	 * @param binder
	 */
	@InitBinder  
	protected  void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}
	
	@RequestMapping(value = "add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Race race,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceService.addRace(race);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String add(int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", race);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	@ResponseBody
	public String update(Race race,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceService.updateRace(race);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public String updateStatus(int raceId,int status,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceService.updateStatus(raceId, status);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
}
