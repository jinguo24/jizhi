package com.tmall.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tmall.admin.util.AjaxWebUtil;
import com.tmall.admin.util.LoginUserUtil;
import com.tmall.model.Template;
import com.tmall.model.TemplateForm;
import com.tmall.service.TemplateService;
@Controller
@RequestMapping(value = "/template")
public class TemplateController {
	
	private static final Logger log = LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	TemplateService templateService;
	
	@RequestMapping(value = "add",method=RequestMethod.POST)
	public ModelAndView add(String content,HttpServletRequest request, HttpServletResponse response) {
		try {
			templateService.addTemplate(JSON.parseObject(content, TemplateForm.class),LoginUserUtil.getLeaseholderId(request),LoginUserUtil.getCurrentUser(request).getName());
			AjaxWebUtil.sendAjaxResponse(request, response, true,"创建成功", null);
		}catch(Exception e) {
			log.error("创建模版失败",e);
			AjaxWebUtil.sendAjaxResponse(request, response, false,"创建失败", e.getLocalizedMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "queryByCode",method=RequestMethod.GET)
	public ModelAndView queryByCode(String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			TemplateForm form = templateService.queryByCode(LoginUserUtil.getLeaseholderId(request), code, false);
			AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", form);
		}catch(Exception e) {
			log.error("创建模版失败",e);
			AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败", e.getLocalizedMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	public ModelAndView update(String code,String content,HttpServletRequest request, HttpServletResponse response) {
		String leaseholderId = LoginUserUtil.getLeaseholderId(request);
		try {
			templateService.update(code, JSON.parseObject(content, TemplateForm.class), leaseholderId, LoginUserUtil.getCurrentUser(request).getName());
			AjaxWebUtil.sendAjaxResponse(request, response, true,"修改成功", null);
		}catch(Exception e) {
			log.error("修改模版失败",e);
			AjaxWebUtil.sendAjaxResponse(request, response, false,"修改失败", e.getLocalizedMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "query",method=RequestMethod.GET)
	public ModelAndView query(String code,String type,String name,String category,int pageIndex,
			int pageSize,HttpServletRequest request, HttpServletResponse response) {
		String leaseholderId = LoginUserUtil.getLeaseholderId(request);
		//if (!AjaxWebUtil.isAjaxRequest(request)) {// 不是ajax请求
		//	return new ModelAndView("redirect:/login/login");
		List<Template> dictionarys =  templateService.query(leaseholderId, code, type,name,category, pageIndex, pageSize);
		AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", dictionarys);
		return null;
	}
	
	@RequestMapping(value = "delete/{id}",method=RequestMethod.GET)
	public ModelAndView logout(@PathVariable int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			templateService.deleteById(id);
			AjaxWebUtil.sendAjaxResponse(request, response, true,"删除成功", null);
		}catch(Exception e) {
			log.error("删除字典失败",e);
			AjaxWebUtil.sendAjaxResponse(request, response, false,"删除字典失败", e.getLocalizedMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "statistics",method=RequestMethod.GET)
	public ModelAndView statistics(String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			TemplateForm form = templateService.queryByCode(null, code, true);
			AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", form);
		}catch(Exception e) {
			log.error("创建模版失败",e);
			AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败", e.getLocalizedMessage());
		}
		return null;
	}
	
	
}
