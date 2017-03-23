package com.jizhi.admin.controller;

import java.text.SimpleDateFormat;
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

import com.jizhi.model.Race;
import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.service.RaceScheduleTeamService;
import com.jizhi.service.RaceService;
import com.jizhi.service.TeamService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;

@Controller
@RequestMapping(value = "/raceSchedule")
public class RaceScheduleController {
	
	@Autowired
	private RaceScheduleTeamService raceScheduleTeamService;
	@Autowired
	private RaceService raceService;
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(Integer raceId,int status,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			int ira = 0;
			if ( null != raceId) {
				ira = raceId;
			}
			PageResult races = raceScheduleTeamService.getRacePageResult(ira,null,0,status,page, pageSize);
			List<RaceScheduleTeam> teams = races.getDatas();
			if ( null != teams ) {
				for (int i =0; i < teams.size() ; i ++) {
					RaceScheduleTeam rst = teams.get(i);
					rst.setTeamOneObj(teamService.getById(rst.getTeamOne()));
					rst.setTeamTwoObj(teamService.getById(rst.getTeamTwo()));
				}
			}
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
	public String add(RaceScheduleTeam raceSchedule,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(raceSchedule.getRaceId());
			raceSchedule.setType(race.getType());
			raceSchedule.setStatus(1);
			raceScheduleTeamService.addRacePageResult(raceSchedule);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "updateResutls",method=RequestMethod.POST)
	@ResponseBody
	public String updateResutls(int id,String successTeamId,String collectItemslists,String judgeItemslists,HttpServletRequest request, HttpServletResponse response) {
		try {
			RaceScheduleTeam raceSchedule = raceScheduleTeamService.queryById(id);
			if (StringUtils.isEmpty(successTeamId)) {
				raceSchedule.setSuccessTeamId("0");
			}
			raceSchedule.setCollectItemslists(collectItemslists);
			raceSchedule.setJudgeItemslists(judgeItemslists);
			raceScheduleTeamService.updateRaceScheduleTeamResults(raceSchedule);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String add(int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			RaceScheduleTeam raceSchedule = raceScheduleTeamService.queryById(id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", raceSchedule);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	@ResponseBody
	public String update(RaceScheduleTeam raceSchedule,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(raceSchedule.getRaceId());
			raceSchedule.setType(race.getType());
			raceScheduleTeamService.updateRaceScheduleTeam(raceSchedule);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"更新失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public String updateStatus(int raceId,int status,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceScheduleTeamService.updateRaceScheduleTeamStatus(raceId, status);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"更新失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	
	@RequestMapping(value = "delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceScheduleTeamService.delete(id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"删除成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"删除失败:"+e.getLocalizedMessage(), null);
		}
	}
	
}
