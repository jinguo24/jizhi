package com.jizhi.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.constant.RaceEnums;
import com.jizhi.model.Team;
import com.jizhi.model.TeamMembers;
import com.jizhi.model.User;
import com.jizhi.service.TeamService;
import com.jizhi.service.UserService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.CookieUtils;
import com.simple.common.util.PrimaryKeyUtil;

@Controller
@RequestMapping(value = "/team")
public class TeamController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value = "foo/applyTeam",method=RequestMethod.POST)
	@ResponseBody
	public String applyTeam(Integer raceId,String phone,String name,String teamname,String image,HttpServletRequest request, HttpServletResponse response) {
		return applyTeam(raceId,RaceEnums.RaceTypes.ZUQIU.getId(), phone, name, teamname, image, request, response);
	}
	
	private String applyTeam(int raceId,int type,String phone,String name,String teamname,String image,HttpServletRequest request, HttpServletResponse response) {
		try {
			//判断用户是否存在，不存在则新增用户
			User user = userService.getUser(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			if ( null == user ) {
				user = new User();
				user.setCreateTime(new Date());
				user.setName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
				user.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
				userService.addUser(user);
			}
			Team team = new Team();
			team.setId(PrimaryKeyUtil.getUUID());
			team.setCreateTime(new Date());
			team.setImage(image);
			team.setLeaderPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			team.setLeaderName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
			team.setStatus(1);
			team.setType(type);
			team.setName(teamname);
			teamService.addTeam(team);
			
			TeamMembers tm = new TeamMembers();
			tm.setMain(1);
			tm.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			tm.setName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
			tm.setTeamId(team.getId());
			tm.setLeader(1);
			teamService.addTeamMember(tm);
			
			
			String token = LocalUtil.entryLeader(team.getId());
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"申请成功", token);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "teamInfoToMemberAdd",method=RequestMethod.GET)
	@ResponseBody
	public String teamInfoToMemberAdd(String token,HttpServletRequest request, HttpServletResponse response) {
		try {
			String tid = LocalUtil.decryLeader(token);
			Team team = queryTeam(tid,request);
			if  (null == team) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"验证通过", team); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	private Team queryTeam(String tid,HttpServletRequest request) {
		Team team = teamService.getById(tid);
		if (null == team) {
			return null;
		}
		String dys = request.getParameter("dys");
		if (!StringUtils.isEmpty(dys) && "1".equals(dys)) {
			List<TeamMembers> tms =  teamService.queryTeamMembers(tid);
			if ( null != tms ) {
				team.setMembers(tms);
			}
		}
		return team;
	}
	
	@RequestMapping(value = "teamMemberAdd",method=RequestMethod.POST)
	@ResponseBody
	public String teamMemberAdd(String token,String name,String nickName,String phone,String remark,HttpServletRequest request, HttpServletResponse response) {
		try {
			String tid = LocalUtil.decryLeader(token);
			if ("_jz_unkownphone".equals(tid)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			//判断用户是否存在，不存在则新增用户
			User user = userService.getUser(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			if ( null == user ) {
				user = new User();
				user.setCreateTime(new Date());
				user.setName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
				user.setNickName(org.apache.commons.lang.StringUtils.trimToEmpty(nickName));
				user.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
				userService.addUser(user);
			}
			Integer c = teamService.queryMembersCountByPhone(tid, phone);
			if ( null != c && c > 0 ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","重复申请", null);
			}
			TeamMembers tm = new TeamMembers();
			tm.setMain(0);
			tm.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			tm.setTeamId(tid);
			tm.setRemark(org.apache.commons.lang.StringUtils.trimToEmpty(remark));
			tm.setName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
			teamService.addTeamMember(tm);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"申请成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "myteam",method=RequestMethod.GET)
	@ResponseBody
	public String myteam(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String currentPhone = CookieUtils.getCookie(request, "cp");
			if ( StringUtils.isEmpty(currentPhone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String token = CookieUtils.getCookie(request, "token");
			if ( StringUtils.isEmpty(token)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			String dephone = LocalUtil.decry(token);
			if (!dephone.equals(currentPhone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			List<Team> teams = teamService.queryTeamsByPhone(currentPhone);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", teams);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(String id,HttpServletRequest request, HttpServletResponse response) {
		try {
//			String currentPhone = CookieUtils.getCookie(request, "cp");
//			if ( StringUtils.isEmpty(currentPhone)) {
//				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
//			}
//			
//			String token = CookieUtils.getCookie(request, "token");
//			if ( StringUtils.isEmpty(token)) {
//				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
//			}
//			
//			String dephone = LocalUtil.decry(token);
//			if (!dephone.equals(currentPhone)) {
//				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
//			}
			
			Team team = queryTeam(id,request);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", team);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
}
