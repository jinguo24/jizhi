package com.jizhi.controller;

import java.util.ArrayList;
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
import com.jizhi.model.Race;
import com.jizhi.model.RacePersonApply;
import com.jizhi.model.TeamRaceApply;
import com.jizhi.model.User;
import com.jizhi.service.RaceService;
import com.jizhi.service.TeamApplyService;
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
	private TeamApplyService teamApplyService;
	
	@Autowired
	private RaceService raceService;
	
	@RequestMapping(value = "foo/applyTeam",method=RequestMethod.POST)
	@ResponseBody
	public String applyTeam(Integer raceId,String raceName,String phone,String name,String teamname,String image,HttpServletRequest request, HttpServletResponse response) {
		return applyTeam(raceId,raceName,RaceEnums.RaceTypes.ZUQIU.getId(), phone, name, teamname, image, request, response);
	}
	
	private String applyTeam(int raceId,String raceName,int type,String phone,String name,String teamname,String image,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(phone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"电话不能为空", "电话不能为空");
			}
			
			if (StringUtils.isEmpty(name)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"姓名不能为空", "姓名不能为空");
			}
			//判断用户是否存在，不存在则新增用户
			User user = userService.getUser(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			if ( null == user ) {
				user = new User();
				user.setCreateTime(new Date());
				user.setName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
				user.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
				userService.addUser(user);
			}
			
			if (StringUtils.isEmpty(teamname)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"队名不能为空", "队名不能为空");
			}
			teamname = StringUtils.trimToNull(teamname);
			TeamRaceApply teamapply = teamApplyService.queryTeamApply(teamname, raceId);
			if (null == teamapply) {
				teamapply = new TeamRaceApply();
				teamapply.setId(PrimaryKeyUtil.getUUID());
				teamapply.setRaceId(raceId);
				teamapply.setType(type);
				teamapply.setRaceName(raceName);
				teamapply.setStatus(1);
				teamapply.setTeamName(teamname);
				teamapply.setTeamImage(image);
				teamapply.setLeaderPhone(StringUtils.trimToEmpty(phone));
				teamapply.setLeaderName(StringUtils.trimToEmpty(name));
				teamApplyService.addTeamApply(teamapply);
			}
			String token = LocalUtil.entryLeader(teamapply.getId());
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
			String taid = LocalUtil.decryLeader(token);
			if ("_jz_unkownphone".equals(taid)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			TeamRaceApply teamapply = queryTeamApply(taid,request);
			if  (null == teamapply) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"验证通过", teamapply); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	private TeamRaceApply queryTeamApply(String taid,HttpServletRequest request) {
		TeamRaceApply teamapply = teamApplyService.queryTeamApplyById(taid);
		if (null == teamapply) {
			return null;
		}
		initMembers(teamapply,request);
		return teamapply;
	}
	
	private void initMembers(TeamRaceApply teamapply,HttpServletRequest request) {
		String dys = request.getParameter("dys");
		if (!StringUtils.isEmpty(dys) && "1".equals(dys)) {
			List<RacePersonApply> tms =  new ArrayList<RacePersonApply>();
			RacePersonApply ra = new RacePersonApply();
			ra.setName(teamapply.getLeaderName());
			ra.setPhone(teamapply.getLeaderPhone());
			ra.setRaceId(teamapply.getRaceId());
			ra.setRaceName(teamapply.getRaceName());
			ra.setTeamApplyId(teamapply.getId());
			ra.setTeamName(teamapply.getTeamName());
			ra.setLeader(1);
			tms.add(ra);
			List<RacePersonApply> ts = teamApplyService.queryPersonApplysByTeamApply(teamapply.getRaceId(), teamapply.getId());
			if ( null != ts ) {
				tms.addAll(ts);
			}
			teamapply.setMembers(tms);
		}
	}
	
	@RequestMapping(value = "teamMemberAdd",method=RequestMethod.POST)
	@ResponseBody
	public String teamMemberAdd(String token,String name,String nickName,String phone,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(phone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"电话不能为空", "电话不能为空");
			}
			
			if (StringUtils.isEmpty(name)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"姓名不能为空", "姓名不能为空");
			}
			
			String taid = LocalUtil.decryLeader(token);
			if ("_jz_unkownphone".equals(taid)) {
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
			
			TeamRaceApply teamapply = teamApplyService.queryTeamApplyById(taid);
			if  (null == teamapply) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			
			RacePersonApply rpapply = teamApplyService.queryPersonApplyByPhone(teamapply.getRaceId(), phone);
			if ( null != rpapply ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","重复申请", rpapply);
			}
			
			rpapply = new RacePersonApply();
			rpapply.setCreateTime(new Date());
			rpapply.setName(StringUtils.trimToEmpty(name));
			rpapply.setPhone(StringUtils.trimToEmpty(phone));
			rpapply.setRaceId(teamapply.getRaceId());
			rpapply.setRaceName(teamapply.getRaceName());
			rpapply.setTeamApplyId(teamapply.getId());
			rpapply.setTeamName(teamapply.getTeamName());
			teamApplyService.addPersonApply(rpapply);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"申请成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "myApplyTeam",method=RequestMethod.GET)
	@ResponseBody
	public String myteam(String phone,HttpServletRequest request, HttpServletResponse response) {
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
			
			List<TeamRaceApply> teams = teamApplyService.queryTeamApplyList(phone);
			if ( null != teams ) {
				for (int i = 0 ; i < teams.size() ; i ++ ) {
					initMembers(teams.get(i),request);
					initRaceInfo(teams.get(i));
				}
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", teams);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	private void initRaceInfo(TeamRaceApply teamRaceApply) {
		Race race = raceService.queryById(teamRaceApply.getRaceId());
		teamRaceApply.setRace(race);
	}
	
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(String id,HttpServletRequest request, HttpServletResponse response) {
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
			
			TeamRaceApply team = queryTeamApply(id,request);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", team);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
}
