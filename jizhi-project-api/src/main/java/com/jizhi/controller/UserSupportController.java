package com.jizhi.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.jizhi.model.User;
import com.jizhi.model.UserRaceSupport;
import com.jizhi.service.RaceService;
import com.jizhi.service.UserService;
import com.jizhi.service.UserSupportService;
import com.simple.common.util.AjaxWebUtil;

@Controller
@RequestMapping(value = "/usp")
public class UserSupportController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSupportService userSupportService;
	
	@Autowired
	private RaceService raceService;
	
	@RequestMapping(value = "teamMemberAddWithCode",method=RequestMethod.POST)
	@ResponseBody
	public String teamMemberAddWithCode(int raceId,String ownerPhone,String name,String phone,String phoneCode,HttpServletRequest request, HttpServletResponse response) {
		//return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动入口已关闭", "活动入口已关闭");
		try {
			if (raceId<=0) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动参数不对", "活动参数不对");
			}
			if (StringUtils.isEmpty(phoneCode)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请输入验证码", "请输入验证码");
			}
			return teamMemberAdd(raceId, ownerPhone,name, phone, phoneCode, request, response);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	private String teamMemberAdd(int raceId,String ownerPhone,String name,String phone,String phoneCode,HttpServletRequest request, HttpServletResponse response) {
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
		//判断用户是否存在，不存在则新增用户
		User user = userService.getUser(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
		if ( null == user ) {
			user = new User();
			user.setCreateTime(new Date());
			user.setName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
			user.setNickName(org.apache.commons.lang.StringUtils.trimToEmpty(name));
			user.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(phone));
			userService.addUser(user);
		}
		
		Race race  = raceService.queryById(raceId);
		if (null == race || race.getStatus() == 2) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已过期", "活动已过期");
		}
		
		if (race.getStatus() == 3) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已停止报名", "活动已停止报名");
		}
		
		//所有
		UserRaceSupport urs = userSupportService.getOne(raceId, ownerPhone, phoneCode);
		if ( null != urs) {
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"3","不能重复投票", "不能重复投票");
		}
		
		urs = new UserRaceSupport();
		urs.setCreateTime(new Date());
		urs.setName(StringUtils.trimToEmpty(name));
		urs.setOwnerPhone(ownerPhone);
		urs.setPhone(phone);
		urs.setRaceId(raceId);
		userSupportService.addUserRaceSupport(urs);
		return AjaxWebUtil.sendAjaxResponse(request, response, true,"投票成功", null);
	}
	
	@RequestMapping(value = "userCounts",method=RequestMethod.GET)
	@ResponseBody
	public String userCounts(String phones,int raceId,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (raceId<=0) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动参数不对", "活动参数不对");
			}
			if (StringUtils.isEmpty(phones)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请输入手机号", "请输入手机号");
			}
			String[] ps = phones.split(",");
			if ( null != ps && ps.length > 0 ) {
				Map<String,Integer> pmaps = new HashMap<String,Integer>();
				for (int i = 0;i< ps.length; i ++) {
					String p = ps[i];
					if (!StringUtils.isEmpty(p)) {
						Integer c = userSupportService.queryCount(p, raceId);
						pmaps.put(p, c);
					}
				}
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", pmaps);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
}
