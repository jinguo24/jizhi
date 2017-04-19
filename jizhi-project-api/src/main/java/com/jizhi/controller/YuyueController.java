package com.jizhi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.jizhi.model.WxUser;
import com.jizhi.model.YuyueActivity;
import com.jizhi.model.YuyueActivityJoin;
import com.jizhi.model.YuyueActivityUser;
import com.jizhi.service.UserService;
import com.jizhi.service.YuyueService;
import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.CookieUtils;
import com.simple.common.util.PrimaryKeyUtil;
import com.simple.weixin.auth.OAuthUserInfo;
import com.simple.weixin.auth.WeiXinAuth;

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
	
	@RequestMapping(value = "page",method=RequestMethod.GET)
	public String yuyuePage(String activityId,HttpServletRequest request, HttpServletResponse response) {
		return "redirect:"+WeiXinAuth.getAuthUrl(EnvPropertiesConfiger.getValue("yuyuefirst"), true, activityId);
	}
	
	@RequestMapping(value = "auth",method=RequestMethod.GET)
	public String auth(String state,String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			OAuthUserInfo au = auth(code);
			String token = LocalUtil.entryYuyue(au.getOpenid());
			return "redirect:"+String.format(EnvPropertiesConfiger.getValue("yuyuePage"),token,au.getOpenid(),state);
		} catch (Exception e) {
			e.printStackTrace();
			return "error:"+e.getLocalizedMessage();
		}
	}
	
	private OAuthUserInfo auth(String code) throws Exception{
		OAuthUserInfo au  = WeiXinAuth.authInfo(code);
		if (null == au) {
			throw new RuntimeException("用户授权失败.");
		}
		WxUser wxUser = yuyueService.getWxUserByOpenId(au.getOpenid());
		if ( null == wxUser) {
			wxUser = new WxUser();
			wxUser.setOpenId(au.getOpenid());
			wxUser.setNickName(au.getNickname());
			wxUser.setImage(au.getHeadimgurl());
			yuyueService.addWxUser(wxUser);
		}else {
			if ((!StringUtils.isEmpty(au.getHeadimgurl())) && (!au.getHeadimgurl().equals(wxUser.getImage()))) {
				wxUser.setNickName(au.getNickname());
				wxUser.setImage(au.getHeadimgurl());
				yuyueService.updateWxInfo(wxUser);
			}
		}
		return au;
	}
	
	private String loginOpenId(HttpServletRequest request) {
		String currentOpenId = CookieUtils.getCookie(request, "openId");
		if ( StringUtils.isEmpty(currentOpenId)) {
			return null;
		}
		
		String token = CookieUtils.getCookie(request, "openToken");
		if ( StringUtils.isEmpty(token)) {
			return null;
		}
		
		String openId = LocalUtil.decryYuyue(token);
		if ("_jz_unkownphone".equals(openId)) {
			return null;
		}
		
		if (!currentOpenId.equals(openId)) {
			return null;
		}
		return openId;
	}
	
	@RequestMapping(value = "info",method=RequestMethod.GET)
	@ResponseBody
	public String info(String activityId,HttpServletRequest request, HttpServletResponse response) {
		try {
			String openId = loginOpenId(request);
			if (StringUtils.isEmpty(openId)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			Map result = new HashMap();
			YuyueActivity activity = yuyueService.queryActivityById(activityId);
			result.put("activity", activity);
			
			YuyueActivityUser yau = yuyueService.queryYuyueActivityUserByOpenId(activityId, openId);
			if ( null != yau ) {
				result.put("currentHas", 1);
			}else {
				result.put("currentHas", 2);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", result);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "tops",method=RequestMethod.GET)
	@ResponseBody
	public String tops(String activityId,int pageIndex,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			List<YuyueActivityUser> yaus = yuyueService.queryYuyueActivityUserList(activityId, pageIndex, pageSize);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", yaus);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "currentJoins",method=RequestMethod.GET)
	@ResponseBody
	public String currentJoins(String activityId,int pageIndex,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			String openId = loginOpenId(request);
			if (StringUtils.isEmpty(openId)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			YuyueActivityUser yau = yuyueService.queryYuyueActivityUserByOpenId(activityId, openId);
			if ( null != yau ) {
				List<YuyueActivityJoin> yajs = yuyueService.queryYuyueActivityJoins(yau.getId(), pageIndex, pageSize);
				return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", yajs);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "apply",method=RequestMethod.POST)
	@ResponseBody
	public String apply(String activityId,HttpServletRequest request, HttpServletResponse response) {
		try {
			String openId = loginOpenId(request);
			if (StringUtils.isEmpty(openId)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			
			YuyueActivityUser yau = yuyueService.queryYuyueActivityUserByOpenId(activityId, openId);
			if ( null != yau ) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"不能重复发起", null);
			}
			yau = new YuyueActivityUser();
			yau.setId(PrimaryKeyUtil.getUUID());
			yau.setActivityId(activityId);
			yau.setOpenId(openId);
			WxUser wxUser = yuyueService.getWxUserByOpenId(openId);
			if ( null != wxUser) {
				yau.setNickName(wxUser.getNickName());
				yau.setImage(wxUser.getImage());
			}
			yuyueService.addYuyueActivityUser(yau);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"发布成功", yau.getId());
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"发布失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "join",method=RequestMethod.POST)
	@ResponseBody
	public String join(String activityUserId,String activityId,HttpServletRequest request, HttpServletResponse response) {
		try {
			String openId = loginOpenId(request);
			if (StringUtils.isEmpty(openId)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			if (!StringUtils.isEmpty(activityUserId)&& !StringUtils.isEmpty(activityId)) {
					Integer count = yuyueService.queryYuyueActivityJoinCounts(activityId, openId);
					if ( null != count && count > 0 ) {
						return AjaxWebUtil.sendAjaxResponse(request, response, false,"已经点赞", null);
					}else {
						yuyueService.queryYuyueActivityUserByOpenId(activityId, openId);
						YuyueActivityJoin yaj = new YuyueActivityJoin();
						yaj.setActivityUserId(activityUserId);
						yaj.setCreateTime(new Date());
						yaj.setOpenId(openId);
						WxUser wxUser = yuyueService.getWxUserByOpenId(openId);
						if ( null != wxUser) {
							yaj.setImage(wxUser.getImage());
							yaj.setNickName(wxUser.getNickName());
						}
						yuyueService.addYuyueActivityJoin(yaj);
						yuyueService.increaseActivityJoinCount(activityId, activityUserId);
					}
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"操作成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"操作失败:"+e.getLocalizedMessage(), null);
		}
	}
}
