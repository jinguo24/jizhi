package com.jizhi.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.RacePersonApply;
import com.jizhi.model.TeamRaceApply;
import com.jizhi.service.TeamApplyService;
import com.jizhi.service.UserService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;

@Controller
@RequestMapping(value = "/teamApply")
public class TeamApplyController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeamApplyService teamApplyService;
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(Integer raceId,String raceName,int status,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult teams = teamApplyService.getTeamRaceApplyPageResult(raceId,raceName, status, 0, null, page, pageSize);
			List<TeamRaceApply> tras = teams.getDatas();
			if ( null != tras ) {
				for (int i = 0 ; i < tras.size() ; i ++) {
					initMembers(tras.get(i),request);
				}
			}
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", teams);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(String id,HttpServletRequest request, HttpServletResponse response) {
		try {
			TeamRaceApply team =  queryTeamApply(id,request);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", team); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
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
	
	
	@RequestMapping(value = "auth",method=RequestMethod.POST)
	@ResponseBody
	public String auth(String id,int status,HttpServletRequest request, HttpServletResponse response) {
		try {
			TeamRaceApply teamapply = teamApplyService.queryTeamApplyById(id);
			if (null == teamapply) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"审核失败:记录不存在", "审核失败:记录不存在");
			}
			//审核通过，则添加球队,添加成员到球队
			if (status == 2) {
				teamApplyService.updateSuccess(teamapply);
			//审核拒绝,删除记录
			}else if (status == 3 ) {
				teamApplyService.updateReject(teamapply);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"审核成功", null); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"审核失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "rejectlist",method=RequestMethod.GET)
	@ResponseBody
	public String rejectlist(Integer raceId,String raceName,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult teams = teamApplyService.getTeamRaceApplyRejectPageResult(raceId,raceName, 0, 0, null, page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", teams);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "deletePerson",method=RequestMethod.POST)
	@ResponseBody
	public String deletePerson(Integer raceId,int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			teamApplyService.deleteTeamApplyPerson(raceId, id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"删除成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"删除失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "persons",method=RequestMethod.GET)
	@ResponseBody
	public String persons(Integer raceId,int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			teamApplyService.deleteTeamApplyPerson(raceId, id);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"删除成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"删除失败:"+e.getLocalizedMessage(), null);
		}
	}
}
