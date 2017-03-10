package com.jizhi.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.Team;
import com.jizhi.model.TeamMembers;
import com.jizhi.model.User;
import com.jizhi.service.TeamService;
import com.jizhi.service.UserService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;
import com.simple.common.util.PrimaryKeyUtil;

@Controller
@RequestMapping(value = "/team")
public class TeamController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(int type,int status,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult teams = teamService.getTeamPageResult(type, status, page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", teams);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(String teamId,HttpServletRequest request, HttpServletResponse response) {
		try {
			Team team = teamService.getById(teamId);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", team); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "auth",method=RequestMethod.POST)
	@ResponseBody
	public String auth(String teamId,int status,String remark,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (status>0) {
				Team team = teamService.getById(teamId);
				team.setStatus(status);
				team.setRemark(remark);
				teamService.updateTeam(team);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"审核成功", null); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"审核失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	@ResponseBody
	public String update(Team team,HttpServletRequest request, HttpServletResponse response) {
		try {
			teamService.updateTeam(team);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"更新失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(String teamId,HttpServletRequest request, HttpServletResponse response) {
		try {
			teamService.delete(teamId);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"删除成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"删除失败", e.getLocalizedMessage());
		}
	}
	
}
