package com.jizhi.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jizhi.model.Yard;
import com.jizhi.service.YardService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.PageResult;

@Controller
@RequestMapping(value = "/yard")
public class YardController {

	@Autowired
	private YardService yardService;
	
	@RequestMapping(value = "list",method=RequestMethod.GET)
	@ResponseBody
	public String list(int page,int pageSize,String name,HttpServletRequest request, HttpServletResponse response) {
		try {
			PageResult yards = yardService.getYardPageResult(name, page, pageSize);
			return  AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", yards);
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value = "add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Yard yard,HttpServletRequest request, HttpServletResponse response) {
		try {
			yard.setStatus(1);
			yardService.addYard(yard);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"添加成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"添加失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(int id,HttpServletRequest request, HttpServletResponse response) {
		Yard yard = yardService.getYard(id);
		return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", yard);
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	@ResponseBody
	public String update(Yard yard,HttpServletRequest request, HttpServletResponse response) {
		yardService.updateYard(yard);
		return AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功", yard);
	}
	
	@RequestMapping(value = "updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public String toAuth(int id,int status,HttpServletRequest request, HttpServletResponse response){
		try {
			yardService.updateStatus(status, id);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"更新成功",null);
		}catch(Exception e) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"更新失败",null);
		}
	}
	
}
