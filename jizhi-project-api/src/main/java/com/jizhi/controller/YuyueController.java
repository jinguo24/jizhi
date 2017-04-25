package com.jizhi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.jizhi.model.User;
import com.jizhi.model.YuyueActivity;
import com.jizhi.model.YuyueActivityJoin;
import com.jizhi.service.UserService;
import com.jizhi.service.YuyueService;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.CookieUtils;

/**
 * @author zhengfy1
 */
@Controller
@RequestMapping(value = "/yuyue")
public class YuyueController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private YuyueService yuyueService;
	
	/**
	 * 处理时间
	 * @param binder
	 */
	@InitBinder  
	protected  void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}
	
	@RequestMapping(value = "info",method=RequestMethod.GET)
	@ResponseBody
	public String info(String activityId,HttpServletRequest request, HttpServletResponse response) {
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
			
			User u = userService.getUser(currentPhone);
			if ( null == u) {
				u = new User();
				u.setPhone(currentPhone);
				u.setCreateTime(new Date());
				userService.addUser(u);
			}
			
			YuyueActivity ac = yuyueService.queryActivityById(activityId);
			if ( null == ac) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动无效", null);
			}
			if (ac.getStatus()==2 ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已关闭", null);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", ac);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "yuyue",method=RequestMethod.POST)
	@ResponseBody
	public String doYuyue(String activityId,HttpServletRequest request, HttpServletResponse response) {
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
			
			YuyueActivity ac = yuyueService.queryActivityById(activityId);
			if ( null == ac) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动无效", null);
			}
			if (ac.getStatus()==2 ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动已关闭，无法预约", null);
			}
			if (ac.getSurplus() <=0 ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"活动人数已满，无法预约", null);
			}
			YuyueActivityJoin yaj = yuyueService.queryJoinByActivityIdAndPhone(activityId, dephone);
			if ( null != yaj ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"您已经预约成功，不能重复预约", null);
			}
			boolean success = yuyueService.join(activityId, dephone);
			yaj = yuyueService.queryJoinByActivityIdAndPhone(activityId, dephone);
			if (success) {
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"预约成功", yaj);
			}else {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"预约失败：人数已达上线.", yaj);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	
	
	
//	@RequestMapping(value = "page",method=RequestMethod.GET)
//	public String yuyuePage(String activityId,HttpServletRequest request, HttpServletResponse response) {
//		return "redirect:"+WeiXinAuth.getAuthUrl(EnvPropertiesConfiger.getValue("yuyuefirst"), true, activityId);
//	}
//	
//	@RequestMapping(value = "auth",method=RequestMethod.GET)
//	public String auth(String state,String code,HttpServletRequest request, HttpServletResponse response) {
//		try {
//			OAuthUserInfo au = auth(code);
//			String token = LocalUtil.entryYuyue(au.getOpenid());
//			return "redirect:"+String.format(EnvPropertiesConfiger.getValue("yuyuePage"),token,au.getOpenid(),state);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error:"+e.getLocalizedMessage();
//		}
//	}
//	
//	private OAuthUserInfo auth(String code) throws Exception{
//		OAuthUserInfo au  = WeiXinAuth.authInfo(code);
//		if (null == au) {
//			throw new RuntimeException("用户授权失败.");
//		}
//		WxUser wxUser = yuyueService.getWxUserByOpenId(au.getOpenid());
//		if ( null == wxUser) {
//			wxUser = new WxUser();
//			wxUser.setOpenId(au.getOpenid());
//			wxUser.setNickName(au.getNickname());
//			wxUser.setImage(au.getHeadimgurl());
//			yuyueService.addWxUser(wxUser);
//		}else {
//			if ((!StringUtils.isEmpty(au.getHeadimgurl())) && (!au.getHeadimgurl().equals(wxUser.getImage()))) {
//				wxUser.setNickName(au.getNickname());
//				wxUser.setImage(au.getHeadimgurl());
//				yuyueService.updateWxInfo(wxUser);
//			}
//		}
//		return au;
//	}
//	
//	private String loginOpenId(HttpServletRequest request) {
//		String currentOpenId = CookieUtils.getCookie(request, "openId");
//		if ( StringUtils.isEmpty(currentOpenId)) {
//			return null;
//		}
//		
//		String token = CookieUtils.getCookie(request, "openToken");
//		if ( StringUtils.isEmpty(token)) {
//			return null;
//		}
//		
//		String openId = LocalUtil.decryYuyue(token);
//		if ("_jz_unkownphone".equals(openId)) {
//			return null;
//		}
//		
//		if (!currentOpenId.equals(openId)) {
//			return null;
//		}
//		return openId;
//	}
	
	
}
