package com.jizhi.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jizhi.service.RaceService;
import com.simple.common.util.AjaxWebUtil;

@Controller
@RequestMapping(value = "/raceResult")
public class RaceResultController {
	
	@Autowired
	private RaceService raceService;
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(String name,int type,int page,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			//PageResult races = raceService.getRacePageResult(name,type, page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", null);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
}
