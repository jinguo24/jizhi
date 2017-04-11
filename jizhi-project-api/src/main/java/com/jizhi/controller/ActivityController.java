package com.jizhi.controller;

import java.text.SimpleDateFormat;
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

import com.jizhi.model.Activity;
import com.jizhi.model.ActivityPerson;
import com.jizhi.model.User;
import com.jizhi.model.UserActivity;
import com.jizhi.service.ActivityService;
import com.jizhi.service.UserService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.CookieUtils;
import com.simple.common.util.PrimaryKeyUtil;

@Controller
@RequestMapping(value = "/activity")
public class ActivityController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping(value = "add",method=RequestMethod.POST)
	@ResponseBody
	public String applyTeam(Activity activity,HttpServletRequest request, HttpServletResponse response) {
		try {
			if ( null == activity) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请填写相应信息", "请填写相应信息");
			}
			if (StringUtils.isEmpty(activity.getOwnerPhone())) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"电话不能为空", "电话不能为空");
			}
			if (StringUtils.isEmpty(activity.getOwnerName())) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"姓名不能为空", "姓名不能为空");
			}
			if (StringUtils.isEmpty(activity.getName())) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动名称不能为空", "活动名称不能为空");
			}
			//判断用户是否存在，不存在则新增用户
			User user = userService.getUser(org.apache.commons.lang.StringUtils.trimToEmpty(activity.getOwnerPhone()));
			if ( null == user ) {
				user = new User();
				user.setCreateTime(new Date());
				user.setName(org.apache.commons.lang.StringUtils.trimToEmpty(activity.getOwnerName()));
				user.setPhone(org.apache.commons.lang.StringUtils.trimToEmpty(activity.getOwnerPhone()));
				userService.addUser(user);
			}
			activity.setId(PrimaryKeyUtil.getUUID());
			activityService.addActivity(activity);
			String token = LocalUtil.entryLeader(activity.getId()+"&"+activity.getOwnerPhone());
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"发布成功", token);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"发布失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	/**
	 * 处理时间
	 * @param binder
	 */
	@InitBinder  
	protected  void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}
	
	@RequestMapping(value = "infoToMemberAdd",method=RequestMethod.GET)
	@ResponseBody
	public String infoToMemberAdd(String token,HttpServletRequest request, HttpServletResponse response) {
		try {
			String taid = LocalUtil.decryLeader(token);
			if ("_jz_unkownphone".equals(taid)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			if (!taid.contains("&")) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			String[] tids = taid.split("&");
			if (tids.length != 2) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			String id = tids[0];
			String ownerphone = tids[1];
			if (StringUtils.isEmpty(id) || StringUtils.isEmpty(ownerphone)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			Activity activity = activityService.getActivityByPhoneAndId(ownerphone, id);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", activity); 
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"获取失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "teamMemberAdd",method=RequestMethod.POST)
	@ResponseBody
	public String teamMemberAdd(String token,String name,String nickName,String phone,HttpServletRequest request, HttpServletResponse response) {
		try {
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
			if (!taid.contains("&")) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			String[] tids = taid.split("&");
			if (tids.length != 2) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			String id = tids[0];
			String ownerphone = tids[1];
			if (StringUtils.isEmpty(id) || StringUtils.isEmpty(ownerphone)) {
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
				userService.addUser(user);
			}
			
			Activity activity = activityService.getActivityByPhoneAndId(ownerphone, id);
			if  (null == activity) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"token无效", null);
			}
			
			if (activity.getDeadLineStatus()==1) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"已过活动报名截至时间，不能报名", null);
			}
			
			if (activity.getEndStatus()==1) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已过期.", null);
			}
			
			UserActivity ua = activityService.getUserActivity(id, ownerphone);
			if (null != ua) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"您已经报名该活动,不能重复报名", null);
			}
			
			activityService.addMember(id, ownerphone, phone, name);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"申请成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "myActivitys",method=RequestMethod.GET)
	@ResponseBody
	public String myActivitys(int pageIndex,int pageSize,HttpServletRequest request, HttpServletResponse response) {
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
			List<UserActivity> as = activityService.getActivites(dephone, pageIndex, pageSize);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", as);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(LocalUtil.entry("18600671341"));
	}
	
	@RequestMapping(value = "detail",method=RequestMethod.GET)
	@ResponseBody
	public String detail(String id,String phone,HttpServletRequest request, HttpServletResponse response) {
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
			Activity activity = activityService.getActivityByPhoneAndId(phone, id);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", activity);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"申请成功", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "activityMembers",method=RequestMethod.GET)
	@ResponseBody
	public String activityMembers(String id,int pageIndex,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			List<ActivityPerson> persons = activityService.getPersons(id, pageIndex, pageSize);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", persons);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
}
