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

import com.jizhi.model.User;
import com.jizhi.model.WxUser;
import com.jizhi.model.YuyueActivity;
import com.jizhi.service.UserService;
import com.jizhi.service.YuyueService;
import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.AjaxWebUtil;
import com.simple.common.util.CookieUtils;
import com.simple.common.util.PrimaryKeyUtil;
import com.simple.weixin.auth.OAuthUserInfo;
import com.simple.weixin.auth.WeiXinAuth;

/**
 * 活动工具
 * @author zhengfy1
 */
@Controller
@RequestMapping(value = "/yuyue")
public class YuyueController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private YuyueService yuyueService;
	
	

	
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
		return openId;
	}
	
	@RequestMapping(value = "apply/ymq",method=RequestMethod.POST)
	@ResponseBody
	public String apply(YuyueActivity ya,String token,HttpServletRequest request, HttpServletResponse response) {
		try {
			if ( null == ya) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请填写相应信息", "请填写相应信息");
			}
			if (StringUtils.isEmpty(ya.getName())) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"名称不能为空", "名称不能为空");
			}
//			if (0 == ya.getYeadId()) {
//				return AjaxWebUtil.sendAjaxResponse(request, response, false,"请指定场地", "请指定场地");
//			}
			String openId = loginOpenId(request);
			if (StringUtils.isEmpty(openId)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			ya.setOpenId(openId);
			ya.setName(StringUtils.trimToEmpty(ya.getName()));
			
			if (!StringUtils.isEmpty(ya.getPhone())) {
				ya.setPhone(StringUtils.trimToEmpty(ya.getPhone()));
				yuyueService.updateWxUserPhone(ya.getPhone(), openId);
				//判断用户是否存在，不存在则新增用户
				User user = userService.getUser(ya.getPhone());
				if ( null == user ) {
					user = new User();
					user.setCreateTime(new Date());
					//user.setName(org.apache.commons.lang.StringUtils.trimToEmpty(activity.getOwnerName()));
					user.setPhone(ya.getPhone());
					userService.addUser(user);
				}
			}
			ya.setId(PrimaryKeyUtil.getUUID());
			yuyueService.addYuyueActivity(ya);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"发布成功", null);
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
	
	@RequestMapping(value = "my",method=RequestMethod.GET)
	@ResponseBody
	public String my(int pageIndex,int pageSize,HttpServletRequest request, HttpServletResponse response) {
		try {
			String openId = loginOpenId(request);
			if (StringUtils.isEmpty(openId)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			List<YuyueActivity> yuyuelist = yuyueService.queryYuyueActivityList(openId, 1, pageIndex, pageSize);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", yuyuelist);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "info",method=RequestMethod.GET)
	@ResponseBody
	public String info(String openId,String id,HttpServletRequest request, HttpServletResponse response) {
		try {
			YuyueActivity ya = yuyueService.queryYuyueActivity(openId, id);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", ya);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "join",method=RequestMethod.GET)
	@ResponseBody
	public String join(String openId,int id,HttpServletRequest request, HttpServletResponse response) {
		try {
			YuyueActivity ya = yuyueService.queryYuyueActivity(openId, id);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", ya);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	
}
