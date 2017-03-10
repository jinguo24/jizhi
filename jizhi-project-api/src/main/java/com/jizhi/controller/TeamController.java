package com.jizhi.controller;

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
	public String applyTeam(String phone,String name,String nickname,String image,HttpServletRequest request, HttpServletResponse response) {
		return applyTeam(1, phone, name, nickname, image, request, response);
	}
	
	private String applyTeam(int type,String phone,String name,String nickname,String image,HttpServletRequest request, HttpServletResponse response) {
		try {
			//判断用户是否存在，不存在则新增用户
			User user = userService.getUser(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			if ( null == user ) {
				user = new User();
				user.setCreateTime(new Date());
				user.setName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
				user.setNickName(org.apache.commons.lang.StringUtils.trimToEmpty(nickname));
				user.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
				userService.addUser(user);
			}
			Team team = new Team();
			team.setId(PrimaryKeyUtil.getUUID());
			team.setCreateTime(new Date());
			team.setImage(image);
			team.setLeaderPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			team.setStatus(1);
			team.setType(type);
			teamService.addTeam(team);
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
			Team team = teamService.getById(tid);
			if  (null == team) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"今天已经领取券", team); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "teamMemberAdd",method=RequestMethod.POST)
	@ResponseBody
	public String detail(String token,String name,String nickName,String phone,HttpServletRequest request, HttpServletResponse response) {
		try {
			String tid = LocalUtil.decryLeader(token);
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
			TeamMembers tm = new TeamMembers();
			tm.setMain(0);
			tm.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			tm.setTeamId(tid);
			teamService.addTeamMember(tm);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"申请成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
}
