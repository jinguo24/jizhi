package com.jizhi.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.RaceSchedule;
import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.service.RaceScheduleService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;

@Controller
@RequestMapping(value = "/raceSchedule")
public class RaceScheduleController {
	
	@Autowired
	private RaceScheduleService raceScheduleService;
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(int racePeriodsId,String name,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult races = raceScheduleService.getRacePageResult(racePeriodsId,name,page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", races);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "add",method=RequestMethod.POST)
	@ResponseBody
	public String add(RaceSchedule raceSchedule,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceScheduleService.addRaceSchedule(raceSchedule);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String add(int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			RaceSchedule raceSchedule = raceScheduleService.queryById(id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", raceSchedule);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	@ResponseBody
	public String update(RaceSchedule raceSchedule,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceScheduleService.updateRaceSchedule(raceSchedule);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceScheduleService.deleteById(id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "teamlist",method=RequestMethod.GET)
	@ResponseBody
	public String teamlist(int scheduleId,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult races = raceScheduleService.getRaceScheduleTeamPageResult(scheduleId, page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", races);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "teamAdd",method=RequestMethod.POST)
	@ResponseBody
	public String teamAdd(RaceScheduleTeam raceScheduleTeam,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceScheduleService.addRaceScheduleTeam(raceScheduleTeam);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "teamDetail",method=RequestMethod.GET)
	@ResponseBody
	public String teamDetail(int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			RaceScheduleTeam raceSchedule = raceScheduleService.queryTeamById(id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", raceSchedule);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "teamUpdate",method=RequestMethod.POST)
	@ResponseBody
	public String teamUpdate(RaceScheduleTeam raceScheduleTeam,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceScheduleService.updateRaceScheduleTeam(raceScheduleTeam);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"更新失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "teamDelete",method=RequestMethod.POST)
	@ResponseBody
	public String teamDelete(int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceScheduleService.deleteTeamById(id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"删除成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"删除失败:"+e.getLocalizedMessage(), null);
		}
	}
}
