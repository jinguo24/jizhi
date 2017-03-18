package com.jizhi.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.constant.RaceEnums;
import com.jizhi.constant.RaceEnums.RacePositions;
import com.jizhi.model.Race;
import com.jizhi.model.RaceCollectItem;
import com.jizhi.model.RaceJudgeItem;
import com.jizhi.service.ConstantsService;
import com.jizhi.service.RaceService;
import com.simple.common.util.AjaxWebUtil;

@Controller
@RequestMapping(value = "/constants")
public class ConstantsController {
	
	@Autowired
	ConstantsService constantService;
	@Autowired
	RaceService raceService;
	
	@RequestMapping(value = "person/collectItems",method=RequestMethod.GET)
	@ResponseBody
	public String pcollectItem(int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(raceId);
			List<RaceCollectItem> items = constantService.queryRaceCollectItemList(1, race.getType(), 1);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", items);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "team/collectItems",method=RequestMethod.GET)
	@ResponseBody
	public String tcollectItem(int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(raceId);
			List<RaceCollectItem> items = constantService.queryRaceCollectItemList(2, race.getType(), 1);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", items);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "person/judgeItems",method=RequestMethod.GET)
	@ResponseBody
	public String pjudgeItems(int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(raceId);
			List<RaceJudgeItem> items = constantService.queryRaceJudgeItemList(1, race.getType(), 1);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", items);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "team/judgeItems",method=RequestMethod.GET)
	@ResponseBody
	public String tjudgeItems(int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(raceId);
			List<RaceJudgeItem> items = constantService.queryRaceJudgeItemList(2, race.getType(), 1);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", items);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "positions",method=RequestMethod.GET)
	@ResponseBody
	public String positions(int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			Race race = raceService.queryById(raceId);
			List<RacePositions> ps = RaceEnums.RacePositions.getPositions(race.getType());
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", ps);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	
	
}
