package com.tmall.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.admin.util.AjaxWebUtil;
import com.tmall.admin.util.LoginUserUtil;
import com.tmall.model.Dictionary;
import com.tmall.service.DictionaryService;
@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController {
	
	private static final Logger log = LoggerFactory.getLogger(DictionaryController.class);

	@Autowired
	DictionaryService dictionaryService;
	
	@RequestMapping(value = "add",method=RequestMethod.POST)
	public ModelAndView add(Dictionary dictionary,HttpServletRequest request, HttpServletResponse response) {
		try {
			dictionary.setLeaseholderId(LoginUserUtil.getLeaseholderId(request));
			dictionaryService.addDitionary(dictionary);
			AjaxWebUtil.sendAjaxResponse(request, response, true,"创建成功", null);
		}catch(Exception e) {
			log.error("创建字典失败",e);
			AjaxWebUtil.sendAjaxResponse(request, response, false,"创建失败", e.getLocalizedMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "update",method=RequestMethod.POST)
	public ModelAndView update(Dictionary dictionary,HttpServletRequest request, HttpServletResponse response) {
		String leaseholderId = LoginUserUtil.getLeaseholderId(request);
		Dictionary dictionarysource = dictionaryService.queryById(dictionary.getId());
		try {
			if (dictionarysource.getCode().equals(dictionary.getCode())) {
				BeanUtils.copyProperties(dictionarysource, dictionary);
				dictionaryService.updateDictionary(dictionarysource);
			}else {
				List<Dictionary> dictionarys =  dictionaryService.query(leaseholderId, dictionary.getCode(), null,1,10);
				if (null != dictionarys && dictionarys.size() > 0) {
					AjaxWebUtil.sendAjaxResponse(request, response, false,"当前租户["+leaseholderId+"]字典code["+dictionary.getCode()+"]重复", null);
				}else {
					BeanUtils.copyProperties(dictionarysource, dictionary);
					dictionaryService.updateDictionary(dictionarysource);
				}
			}
			AjaxWebUtil.sendAjaxResponse(request, response, true,"修改成功", null);
		}catch(Exception e) {
			log.error("修改字典失败",e);
			AjaxWebUtil.sendAjaxResponse(request, response, false,"修改失败", e.getLocalizedMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "query",method=RequestMethod.GET)
	public ModelAndView query(String code,String parentCode,int pageIndex,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		String leaseholderId = LoginUserUtil.getLeaseholderId(request);
		//if (!AjaxWebUtil.isAjaxRequest(request)) {// 不是ajax请求
		//	return new ModelAndView("redirect:/login/login");
		List<Dictionary> dictionarys =  dictionaryService.query(leaseholderId, code, parentCode, pageIndex, pageSize);
		AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", dictionarys);
		return null;
	}
	
	@RequestMapping(value = "delete/{id}",method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			dictionaryService.deleteById(id);
			AjaxWebUtil.sendAjaxResponse(request, response, true,"删除成功", null);
		}catch(Exception e) {
			log.error("删除字典失败",e);
			AjaxWebUtil.sendAjaxResponse(request, response, false,"删除字典失败", e.getLocalizedMessage());
		}
		return null;
	}
	
	
}
