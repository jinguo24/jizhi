package com.jizhi.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.Race;
import com.jizhi.model.RacePersonApply;
import com.jizhi.model.RaceResults;
import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.model.Team;
import com.jizhi.service.RaceResultService;
import com.jizhi.service.RaceScheduleTeamService;
import com.jizhi.service.RaceService;
import com.jizhi.service.TeamApplyService;
import com.jizhi.service.TeamService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;

@Controller
@RequestMapping(value = "/raceResult")
public class RaceResultController {
	
	@Autowired
	private RaceResultService raceResultService;
	@Autowired
	private RaceService raceService;
	@Autowired
	private RaceScheduleTeamService raceScheduleService;
	@Autowired
	private TeamApplyService teamApplyService;
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(int scheduleTeamId,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult races = raceResultService.getRaceResultsPageResult(scheduleTeamId, page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", races);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "add",method=RequestMethod.POST)
	@ResponseBody
	public String add(RaceResults results,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(results.getRaceId());
			results.setType(race.getType());
			raceResultService.addRaceResults(results);;
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	@ResponseBody
	public String update(RaceResults results,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(results.getRaceId());
			results.setType(race.getType());
			raceResultService.updateRaceResults(results);;
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(int scheduleId,int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			raceResultService.deleteById(scheduleId, id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"删除成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"删除失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "persons",method=RequestMethod.GET)
	@ResponseBody
	public String persons(int scheduleId,HttpServletRequest request, HttpServletResponse response) {
		try {
			RaceScheduleTeam rsteam = raceScheduleService.queryById(scheduleId);
			if (null == rsteam) {
				return  AjaxWebUtil.sendAjaxResponse(request, response, false,"赛程不存在", null);
			}
			if (rsteam.getUdefined()==1) {
				return  AjaxWebUtil.sendAjaxResponse(request, response, false,"赛程信息为自定义，不是真实队伍，无法查询", null);
			}
			String teamOne = rsteam.getTeamOne();
			String teamTwo = rsteam.getTeamTwo();
			Team tone = teamService.getById(teamOne);
			if (null == tone) {
				return  AjaxWebUtil.sendAjaxResponse(request, response, false,"球队["+teamOne+"]不存在", null);
			}
			Team ttwo = teamService.getById(teamTwo);
			if (null == ttwo) {
				return  AjaxWebUtil.sendAjaxResponse(request, response, false,"球队["+teamTwo+"]不存在", null);
			}
			List<String> names = new ArrayList<String>();
			names.add(tone.getName());
			names.add(ttwo.getName());
			List<RacePersonApply> persons = teamApplyService.queryPersonApplysByTeamName(rsteam, names);
			
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", persons);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
}
