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
import com.jizhi.model.TeamChouQianDetail;
import com.jizhi.model.TeamRaceApply;
import com.jizhi.model.User;
import com.jizhi.service.RaceService;
import com.jizhi.service.TeamApplyService;
import com.jizhi.service.TeamChouQianService;
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
	
	@Autowired
	private TeamChouQianService teamChouQianService;
	
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
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"只允许注册一个队伍", "只允许注册一个队伍");
			}
			
			RacePersonApply rpapply = teamApplyService.queryPersonApplyByPhone(raceId, phone);
			if ( null != rpapply ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"只允许注册一个队伍", "只允许注册一个队伍");
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
	public String teamMemberAdd(String token,String name,String nickName,String phone,String studentNo,String className,HttpServletRequest request, HttpServletResponse response) {
		//return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动入口已关闭", "活动入口已关闭");
		try {
			return teamMemberAdd(token, name, nickName, phone, studentNo, className,null, request, response);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "teamMemberAddWithCode",method=RequestMethod.POST)
	@ResponseBody
	public String teamMemberAddWithCode(String token,String name,String nickName,String phone,String phoneCode,String studentNo,String className,HttpServletRequest request, HttpServletResponse response) {
		//return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动入口已关闭", "活动入口已关闭");
		try {
			if (StringUtils.isEmpty(phoneCode)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请输入验证码", "请输入验证码");
			}
			return teamMemberAdd(token, name, nickName, phone, studentNo, className,phoneCode, request, response);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	private String teamMemberAdd(String token,String name,String nickName,String phone,String studentNo,String className,String phoneCode,HttpServletRequest request, HttpServletResponse response) {
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
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
		}
		
		Race race  = raceService.queryById(teamapply.getRaceId());
		if (null == race || race.getStatus() == 2) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已过期", "活动已过期");
		}
		
		if (race.getStatus() == 3) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已停止报名", "活动已停止报名");
		}
		
		if  (teamapply.getLeaderPhone().equals(StringUtils.trimToEmpty(phone))) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"只允许注册一个队伍", "只允许注册一个队伍");
		}
		//所有
		Integer count = teamApplyService.getTeamRaceApplyCount(teamapply.getRaceId(), null, 0, teamapply.getType(), phone);
		if ( null != count && count > 0 ) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"只允许注册一个队伍", "只允许注册一个队伍");
		}
		
		RacePersonApply rpapply = teamApplyService.queryPersonApplyByPhone(teamapply.getRaceId(), phone);
		if ( null != rpapply ) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","只允许注册一个队伍", rpapply);
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
		teamApplyService.addPersonApply(rpapply);
		return AjaxWebUtil.sendAjaxResponse(request, response, true,"申请成功", null);
	}
	
	
	
	@RequestMapping(value = "myApplyTeam",method=RequestMethod.GET)
	@ResponseBody
	public String myteam(String phone,HttpServletRequest request, HttpServletResponse response) {
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
	
	@RequestMapping(value = "chouqian",method=RequestMethod.GET)
	@ResponseBody
	public String chouqian(int raceId,String phone,HttpServletRequest request, HttpServletResponse response) {
		try {
			String label = teamChouQianService.chouqian(raceId, phone);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"抽签成功", label);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "initChouqian",method=RequestMethod.GET)
	@ResponseBody
	public String initChouqian(int raceId,int lc,int tc,HttpServletRequest request, HttpServletResponse response) {
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
//			if (!dephone.equals("18600671341")) {
//				return AjaxWebUtil.sendAjaxResponse(request, response, false,"非法操作", null);
//			}
			List<TeamChouQianDetail> dlist = teamChouQianService.queryDetails(raceId);
			Map<String,Integer> counts = new HashMap<String,Integer>();
			if ( null != dlist) {
				for (int i = 0 ; i < dlist.size() ; i ++) {
					TeamChouQianDetail tcqd = dlist.get(i);
					if (counts.containsKey(tcqd.getLabel())) {
						counts.put(tcqd.getLabel(), counts.get(tcqd.getLabel())+1);
					}else {
						counts.put(tcqd.getLabel(), 1);
					}
				}
			}
			ChouQianCache.getInstance().init(lc, tc,counts);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"初始化成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"初始化失败", e.getLocalizedMessage());
		}
	}
}
