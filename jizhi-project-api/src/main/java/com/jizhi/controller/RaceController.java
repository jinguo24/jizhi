package com.jizhi.controller;

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

import com.jizhi.model.Race;
import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.model.TongjiTRace;
import com.jizhi.service.RaceScheduleTeamService;
import com.jizhi.service.RaceService;
import com.jizhi.service.TeamService;
import com.jizhi.service.TongjiHelper;
import com.jizhi.service.TongjiService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;

@Controller
@RequestMapping(value = "/race")
public class RaceController {
	
	@Autowired
	RaceScheduleTeamService scheduleService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private RaceService raceService;
	@Autowired
	private TongjiService tongjiService;
	
	@RequestMapping(value = "schedules",method=RequestMethod.GET)
	@ResponseBody
	public String get(int raceId, int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(raceId);
			Map re = new HashMap();
			PageResult pr = scheduleService.getRacePageResult(raceId, null, 0, 999, page, pageSize);
			List<RaceScheduleTeam> teams = pr.getDatas();
			Map<String,Map<String,Integer>> teamcoutsmap = new HashMap<String,Map<String,Integer>>();
			if ( null != teams ) {
				for (int i =0; i < teams.size() ; i ++) {
					RaceScheduleTeam rst = teams.get(i);
					Map<String,Integer> jinqiumap = new HashMap<String,Integer>();
					if (rst.getUdefined()!=1) {
						rst.setTeamOneObj(teamService.getById(rst.getTeamOne()));
						rst.setTeamTwoObj(teamService.getById(rst.getTeamTwo()));
					}
					
					//是否只要很简单的信息
					String simple = request.getParameter("simple");
					if ((!StringUtils.isEmpty(simple)) && "1".equals(simple)) {
						if (rst.getStatus()==3 && (null != rst.getCollectItemsMap())) {
							Map<String,String> teamOnemap = rst.getCollectItemsMap().get(rst.getTeamOne());
							if (null != teamOnemap) {
								String jq1 = teamOnemap.get(TongjiHelper.key_c_t_jinqiu);
								try {
									if ( null != jq1) {
										jinqiumap.put(rst.getTeamOne(), Integer.parseInt(jq1));
									}
								}catch (Exception e) {
								}
							}
							Map<String,String> teamTwomap = rst.getCollectItemsMap().get(rst.getTeamTwo());
							if (null != teamTwomap) {
								String jq2 = teamTwomap.get(TongjiHelper.key_c_t_jinqiu);
								try {
									if (null != jq2) {
										jinqiumap.put(rst.getTeamTwo(), Integer.parseInt(jq2));
									}
								}catch (Exception e) {
								}
							}
							teamcoutsmap.put(String.valueOf(rst.getId()), jinqiumap);
						}
						
						rst.setCollectItemsMap(null);
						rst.setCollectItems(null);
						rst.setCollectItemslists(null);
						rst.setJudgeItems(null);
						rst.setJudgeItemslists(null);
					}
				}
			}
			re.put("list", pr);
			re.put("race", race);
			re.put("counts", teamcoutsmap);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", re);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "teamResuts",method=RequestMethod.GET)
	@ResponseBody
	public String teamResuts(int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			List<TongjiTRace> tts = tongjiService.getTongjiTRaces(raceId);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", tts);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "schedultDetail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			RaceScheduleTeam rst = scheduleService.queryById(id);
			if (null != rst && rst.getUdefined()!=1) {
				rst.setTeamOneObj(teamService.getById(rst.getTeamOne()));
				rst.setTeamTwoObj(teamService.getById(rst.getTeamTwo()));
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"获取成功", rst);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
}
