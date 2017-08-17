package com.jizhi.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.jizhi.model.RacePersonApply;
import com.jizhi.model.RaceResults;
import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.model.Team;
import com.jizhi.model.TeamMembers;
import com.jizhi.model.TeamRaceApply;
import com.jizhi.service.RaceResultService;
import com.jizhi.service.RaceScheduleTeamService;
import com.jizhi.service.RaceService;
import com.jizhi.service.TeamApplyService;
import com.jizhi.service.TeamService;
import com.jizhi.service.TongjiService;
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
	@Autowired
	private TeamApplyService teamApplyService;
	@Autowired
	private RaceResultService raceResultService;
	@Autowired
	private TongjiService tongjiService;
	
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
					if (rst.getUdefined()!=1) {
						Team toneObj = teamService.getById(rst.getTeamOne());
						rst.setTeamOneObj(toneObj);
						Team ttwoObj = teamService.getById(rst.getTeamTwo());
						rst.setTeamTwoObj(ttwoObj);
						String dys = request.getParameter("dys");
						if ("1".equals(dys)) {
							List<TeamMembers> t1list = new ArrayList<TeamMembers>();
							TeamMembers t1leader = new TeamMembers();
							t1leader.setLeader(1);
							TeamRaceApply ta1 = teamApplyService.queryTeamApply(toneObj.getName(), raceId);
							t1leader.setName(ta1.getLeaderName());
							t1leader.setPhone(ta1.getLeaderPhone());
							t1leader.setTeamId(toneObj.getId());
							//查询是否配置了数据
							RaceResults rr = raceResultService.queryPersonResult(rst.getId(), ta1.getLeaderPhone());
							if ( null == rr) {
								t1leader.setFlag("0");
							}else {
								t1leader.setFlag("1");
							}
							t1list.add(t1leader);
							List<String> t1names = new ArrayList<String>();
							t1names.add(toneObj.getName());
							List<RacePersonApply> persons = teamApplyService.queryPersonApplysByTeamName(raceId, t1names);
							if ( null != persons) {
								for (int j = 0 ; j < persons.size(); j ++) {
									RacePersonApply pp = persons.get(j);
									TeamMembers tm = new TeamMembers();
									tm.setLeader(pp.getLeader());
									tm.setName(pp.getName());
									tm.setPhone(pp.getPhone());
									tm.setTeamId(toneObj.getId());
									RaceResults rrp = raceResultService.queryPersonResult(rst.getId(), pp.getPhone());
									if ( null == rrp) {
										tm.setFlag("0");
									}else {
										tm.setFlag("1");
									}
									t1list.add(tm);
								}
								toneObj.setMembers(t1list);
							}
							
							List<TeamMembers> t2list = new ArrayList<TeamMembers>();
							TeamMembers t2leader = new TeamMembers();
							t2leader.setLeader(1);
							TeamRaceApply ta2 = teamApplyService.queryTeamApply(ttwoObj.getName(), raceId);
							t2leader.setName(ta2.getLeaderName());
							t2leader.setPhone(ta2.getLeaderPhone());
							t2leader.setTeamId(ttwoObj.getId());
							RaceResults rr2 = raceResultService.queryPersonResult(rst.getId(), ta2.getLeaderPhone());
							if ( null == rr2) {
								t2leader.setFlag("0");
							}else {
								t2leader.setFlag("1");
							}
							t2list.add(t2leader);
							List<String> t2names = new ArrayList<String>();
							t2names.add(ttwoObj.getName());
							List<RacePersonApply> t2persons = teamApplyService.queryPersonApplysByTeamName(raceId, t2names);
							if ( null != t2persons) {
								for (int j = 0 ; j < t2persons.size(); j ++) {
									RacePersonApply pp = t2persons.get(j);
									TeamMembers tm = new TeamMembers();
									tm.setLeader(pp.getLeader());
									tm.setName(pp.getName());
									tm.setPhone(pp.getPhone());
									tm.setTeamId(ttwoObj.getId());
									RaceResults rrp = raceResultService.queryPersonResult(rst.getId(), pp.getPhone());
									if ( null == rrp) {
										tm.setFlag("0");
									}else {
										tm.setFlag("1");
									}
									t2list.add(tm);
								}
								ttwoObj.setMembers(t2list);
							}
						}
					}
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
			if (null == raceSchedule || raceSchedule.getUdefined()==1) {
				return  AjaxWebUtil.sendAjaxResponse(request, response, false,"该赛程无效，不能录入数据", null);
			}
			//if (raceSchedule.getStatus()==3) {
			//	return  AjaxWebUtil.sendAjaxResponse(request, response, false,"该赛程已经完成，不能修改结果", null);
			//}
			if (StringUtils.isEmpty(successTeamId)) {
				raceSchedule.setSuccessTeamId("0");
			}else {
				raceSchedule.setSuccessTeamId(successTeamId);
			}
			raceSchedule.setCollectItemslists(collectItemslists);
			raceSchedule.setJudgeItemslists(judgeItemslists);
			raceSchedule.setStatus(3);
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
	
	@RequestMapping(value = "freshScheduleTeamResults",method=RequestMethod.GET)
	@ResponseBody
	public String freshScheduleTeamResults(int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			tongjiService.updateTeamRaceTongji(raceId);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"更新失败:"+e.getLocalizedMessage(), null);
		}
	}
	
}
