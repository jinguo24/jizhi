package com.jizhi.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.constant.ChouQianCache;
import com.jizhi.constant.RaceEnums;
import com.jizhi.model.Race;
import com.jizhi.model.RacePersonApply;
import com.jizhi.model.Team;
import com.jizhi.model.TeamChouQianDetail;
import com.jizhi.model.TeamRaceApply;
import com.jizhi.model.User;
import com.jizhi.service.RaceService;
import com.jizhi.service.TeamApplyService;
import com.jizhi.service.TeamChouQianService;
import com.jizhi.service.TeamService;
import com.jizhi.service.UserService;
import com.jizhi.service.UserSupportService;
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
	private TeamService teamService;
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private TeamChouQianService teamChouQianService;
	
	@Autowired
	private UserSupportService userSupportService;
	

	@RequestMapping(value = "teamApplyList",method=RequestMethod.GET)
	@ResponseBody
	public String teamApplyList(int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			List<TeamRaceApply> list = teamApplyService.queryTeamRaceApplyList(raceId, null, 0, 0, null, 1, 100);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询通过", list); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败", e.getLocalizedMessage());
		}
	}
	
	
	@RequestMapping(value = "foo/applyTeam",method=RequestMethod.POST)
	@ResponseBody
	public String applyTeam(Integer raceId,String raceName,String phone,String name,String teamname,String image,String studentNo,String className,HttpServletRequest request, HttpServletResponse response) {
		//return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动入口已关闭", "活动入口已关闭");
		return applyTeam(raceId,raceName,RaceEnums.RaceTypes.ZUQIU.getId(), phone, name, teamname, image, studentNo,className,request, response);
	}
	
	private String applyTeam(int raceId,String raceName,int type,String phone,String name,String teamname,String image,String studentNo,String className,HttpServletRequest request, HttpServletResponse response) {
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
				user.setStudentNo(org.apache.commons.lang.StringUtils.trimToEmpty(studentNo));
				userService.addUser(user);
			}
			
			Race race  = raceService.queryById(raceId);
			if (null == race || race.getStatus() == 2) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已过期", "活动已过期");
			}
			
			if (race.getStatus() == 3) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已停止报名", "活动已停止报名");
			}
			
			if (StringUtils.isEmpty(teamname)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"队名不能为空", "队名不能为空");
			}
			teamname = StringUtils.trimToNull(teamname);
			TeamRaceApply teamapply = teamApplyService.queryTeamApply(teamname, raceId);
			if ( null != teamapply) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"球队名称已被占用", "球队名称已被占用");
			}
			
			
			Integer count = teamApplyService.getTeamRaceApplyCount(raceId, null, 0, type, phone);
			if ( null != count && count > 0 ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"不允许重复申请", "不允许重复申请");
			}
			
			RacePersonApply rpapply = teamApplyService.queryPersonApplyByPhone(raceId, phone);
			if ( null != rpapply ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"不允许重复申请", "不允许重复申请");
			}
			
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
			teamapply.setCreateTime(new Date());
			teamapply.setStudentNo(studentNo);
			teamapply.setClassName(className);
			teamApplyService.addTeamApply(teamapply);
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
		//return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动入口已关闭", "活动入口已关闭");
		try {
			String taid = LocalUtil.decryLeader(token);
			if ("_jz_unkownphone".equals(taid)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			TeamRaceApply teamapply = queryTeamApply(taid,request);
			if  (null == teamapply) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"球队审核未通过", null);
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
		initMembers(teamapply,true,request);
		return teamapply;
	}
	
	private void initMembers(TeamRaceApply teamapply,boolean needsLeader,HttpServletRequest request) {
		String dys = request.getParameter("dys");
		if (!StringUtils.isEmpty(dys) && "1".equals(dys)) {
			List<RacePersonApply> tms =  new ArrayList<RacePersonApply>();
			if (needsLeader) {
				RacePersonApply ra = new RacePersonApply();
				ra.setName(teamapply.getLeaderName());
				ra.setPhone(teamapply.getLeaderPhone());
				ra.setRaceId(teamapply.getRaceId());
				ra.setRaceName(teamapply.getRaceName());
				ra.setTeamApplyId(teamapply.getId());
				ra.setTeamName(teamapply.getTeamName());
				ra.setLeader(1);
				tms.add(ra);
			}
			List<RacePersonApply> ts = teamApplyService.queryPersonApplysByTeamApply(teamapply.getRaceId(), teamapply.getId());
			if ( null != ts ) {
				tms.addAll(ts);
			}
			teamapply.setMembers(tms);
		}
	}
	
	@RequestMapping(value = "teamMemberAdd",method=RequestMethod.POST)
	@ResponseBody
	public String teamMemberAdd(String token,String name,String nickName,String phone,String studentNo,
			String className,String positions,String headImage,HttpServletRequest request, HttpServletResponse response) {
		//return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动入口已关闭", "活动入口已关闭");
		try {
			return teamMemberAdd(token, name, nickName, phone, studentNo, className,null,positions,headImage, request, response);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "teamMemberAddWithId",method=RequestMethod.POST)
	@ResponseBody
	public String teamMemberAddWithId(String taId,String name,String nickName,String phone,String phoneCode,String studentNo,
			String className,String positions,String headImage,HttpServletRequest request, HttpServletResponse response) {
		try {
			//if (StringUtils.isEmpty(phoneCode)) {
			//	return AjaxWebUtil.sendAjaxResponse(request, response, false,"请输入验证码", "请输入验证码");
			//}
			return teamMemberAddWithTaId(taId, name, nickName, phone, studentNo, className,phoneCode,positions,headImage, request, response);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "teamMemberAddWithCode",method=RequestMethod.POST)
	@ResponseBody
	public String teamMemberAddWithCode(String token,String name,String nickName,String phone,String phoneCode,
			String studentNo,String className,String positions,String headImage,HttpServletRequest request, HttpServletResponse response) {
		//return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动入口已关闭", "活动入口已关闭");
		try {
			if (StringUtils.isEmpty(phoneCode)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请输入验证码", "请输入验证码");
			}
			return teamMemberAdd(token, name, nickName, phone, studentNo, className,phoneCode,positions,headImage, request, response);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	private String teamMemberAdd(String token,String name,String nickName,String phone,String studentNo,String className,
			String phoneCode,String positions,String headImage,HttpServletRequest request, HttpServletResponse response) {
		//如果有短信验证码，则校验验证码
		if (!StringUtils.isEmpty(phoneCode)) {
			boolean valid = LocalCache.codeValid(phone, phoneCode);
			if (!valid) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"验证码错误", "验证码错误");
			}
		}
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
		return teamMemberAddWithTaId(taid, name, nickName, phone, studentNo, className, phoneCode, positions, headImage, request, response);
	}
	
	private String teamMemberAddWithTaId(String taid,String name,String nickName,String phone,String studentNo,String className,
			String phoneCode,String positions,String headImage,HttpServletRequest request, HttpServletResponse response) {
		//判断用户是否存在，不存在则新增用户
				User user = userService.getUser(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
				if ( null == user ) {
					user = new User();
					user.setCreateTime(new Date());
					user.setName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
					user.setNickName(org.apache.commons.lang.StringUtils.trimToEmpty(nickName));
					user.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
					user.setStudentNo(org.apache.commons.lang.StringUtils.trimToEmpty(studentNo));
					userService.addUser(user);
				}
				
				TeamRaceApply teamapply = teamApplyService.queryTeamApplyById(taid);
				if  (null == teamapply) {
					return AjaxWebUtil.sendAjaxResponse(request, response, false,"参数无效", null);
				}
				
				Race race  = raceService.queryById(teamapply.getRaceId());
				if (null == race || race.getStatus() == 2) {
					return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已过期", "活动已过期");
				}
				
				if (race.getStatus() == 3) {
					return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已停止报名", "活动已停止报名");
				}
				
				if  (teamapply.getLeaderPhone().equals(StringUtils.trimToEmpty(phone))) {
					return AjaxWebUtil.sendAjaxResponse(request, response, false,"不允许重复申请", "不允许重复申请");
				}
				//所有
				Integer count = teamApplyService.getTeamRaceApplyCount(teamapply.getRaceId(), null, 0, teamapply.getType(), phone);
				if ( null != count && count > 0 ) {
					return AjaxWebUtil.sendAjaxResponse(request, response, false,"不允许重复申请", "不允许重复申请");
				}
				
				RacePersonApply rpapply = teamApplyService.queryPersonApplyByPhone(teamapply.getRaceId(), phone);
				if ( null != rpapply ) {
					return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","不允许重复申请", rpapply);
				}
				
				rpapply = new RacePersonApply();
				rpapply.setCreateTime(new Date());
				rpapply.setName(StringUtils.trimToEmpty(name));
				rpapply.setPhone(StringUtils.trimToEmpty(phone));
				rpapply.setRaceId(teamapply.getRaceId());
				rpapply.setRaceName(teamapply.getRaceName());
				rpapply.setTeamApplyId(teamapply.getId());
				rpapply.setTeamName(teamapply.getTeamName());
				rpapply.setStudentNo(studentNo);
				rpapply.setClassName(className);
				rpapply.setPositions(positions);
				rpapply.setHeadImage(headImage);
				teamApplyService.addPersonApply(rpapply);
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"申请成功", LocalUtil.entry(phone));
	}
	
	@RequestMapping(value = "myApplyTeam",method=RequestMethod.GET)
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
			
			List<TeamRaceApply> teams = teamApplyService.queryTeamApplyList(currentPhone);
			if ( null != teams ) {
				for (int i = 0 ; i < teams.size() ; i ++ ) {
					initMembers(teams.get(i),true,request);
					initRaceInfo(teams.get(i));
				}
			}
			Collections.sort(teams);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", teams);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "myRaceApplyTeam",method=RequestMethod.GET)
	@ResponseBody
	public String myRaceApplyTeam(int raceId,HttpServletRequest request, HttpServletResponse response) {
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
			
			TeamRaceApply team = teamApplyService.queryTeamApply(raceId,currentPhone);
			if ( null != team ) {
					initMembers(team,true,request);
					initRaceInfo(team);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", team);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "currentRemoveApply",method=RequestMethod.GET)
	@ResponseBody
	public String currentRemoveApply(int raceId,HttpServletRequest request, HttpServletResponse response) {
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
			
			TeamRaceApply team = teamApplyService.queryTeamApply(raceId,currentPhone);
			if ( null != team && team.getStatus() == 1) {
				teamApplyService.deleteTeamApplyPerson(raceId, dephone);
				//CookieUtils.removeCookie(request, "cp",response);
				//CookieUtils.removeCookie(request, "token",response);
			}else {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已失效，不能操作", null);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", team);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	private void initRaceInfo(TeamRaceApply teamRaceApply) {
		Race race = raceService.queryById(teamRaceApply.getRaceId());
		teamRaceApply.setRace(race);
	}
	
	@RequestMapping(value = "teamMemberList",method=RequestMethod.GET)
	@ResponseBody
	public String teamMemberList(Integer raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			List<TeamRaceApply> teams = teamApplyService.queryTeamRaceApplyList(raceId, null, 2, 1, null, 1, 100);
			if ( null != teams ) {
				for (int i = 0 ; i < teams.size() ; i ++ ) {
					TeamRaceApply tra = teams.get(i);
					initMembers(tra,false,request);
					tra.setToken(LocalUtil.entryLeader(tra.getId()));
					//initRaceInfo(teams.get(i));
				}
			}
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
			if ( null != team) {
				String teamToken = LocalUtil.entryLeader(team.getId());
				team.setToken(teamToken);
				if (dephone.equals(team.getLeaderPhone())) {
					team.setCurrentLeader(1);
				}
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", team);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "getTeamByName",method=RequestMethod.POST)
	@ResponseBody
	public String getTeamByName(String name,HttpServletRequest request, HttpServletResponse response) {
		try {
			Team team = teamService.getByName(name);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", team);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "positionList",method=RequestMethod.GET)
	@ResponseBody
	public String positionList(Integer raceId,String position,HttpServletRequest request, HttpServletResponse response) {
		try {
			List<RacePersonApply> teams = teamApplyService.queryRacePersonApplyListByPosition(raceId, position, 1, 1000);
			if ( null != teams ) {
				for (int i = 0 ; i < teams.size() ; i ++ ) {
					RacePersonApply rpa = teams.get(i);
					Integer count = userSupportService.queryCount(rpa.getPhone(), rpa.getRaceId());
					rpa.setSupportCount(count);
				}
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", teams);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
}
