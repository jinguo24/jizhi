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
import com.jizhi.model.YuyueUserActivity;
import com.jizhi.model.YuyueActivityJoin;
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
	
	@RequestMapping(value = "page",method=RequestMethod.GET)
	public String yuyuePage(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:"+WeiXinAuth.getAuthUrl(EnvPropertiesConfiger.getValue("yuyuefirst"), true, null);
	}
	
	@RequestMapping(value = "auth",method=RequestMethod.GET)
	public String auth(String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			OAuthUserInfo au = auth(code);
			String token = LocalUtil.entryYuyue(au.getOpenid());
			return "redirect:"+String.format(EnvPropertiesConfiger.getValue("yuyuePage"),token,au.getOpenid());
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
		return openId;
	}
	
	@RequestMapping(value = "apply/ymq",method=RequestMethod.POST)
	@ResponseBody
	public String apply(YuyueUserActivity ya,String token,HttpServletRequest request, HttpServletResponse response) {
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
			List<YuyueUserActivity> yuyuelist = yuyueService.queryYuyueActivityList(openId, 1, pageIndex, pageSize);
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
			YuyueUserActivity ya = yuyueService.queryYuyueActivity(openId, id);
			if (null != ya) {
				ya.setJoins(yuyueService.queryYuyueActivityJoins(ya.getId(), 1, 10000));
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"查询成功", ya);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "currenJoin",method=RequestMethod.GET)
	@ResponseBody
	public String currenJoin(String activityId,HttpServletRequest request, HttpServletResponse response) {
		try {
			String openId = loginOpenId(request);
			if (StringUtils.isEmpty(openId)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			Integer count = yuyueService.queryYuyueActivityJoinCounts(activityId, openId);
			if (null == count || count <=0) {
				AjaxWebUtil.sendAjaxResponse(request, response, true,"已经点赞", 1);
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"还未点赞", 2);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"查询失败:"+e.getLocalizedMessage(), e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "joinpage",method=RequestMethod.GET)
	public String joinpage(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:"+WeiXinAuth.getAuthUrl(EnvPropertiesConfiger.getValue("yuyuejoin"), true,null);
	}
	
	@RequestMapping(value = "joinAuth",method=RequestMethod.GET)
	public String joinAuth(String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			OAuthUserInfo au  = auth(code);
			return "redirect:"+EnvPropertiesConfiger.getValue("yuyuejoinPage");
		}catch(Exception e) {
			e.printStackTrace();
			return "error."+e.getLocalizedMessage();
		}
	}
	
	@RequestMapping(value = "join",method=RequestMethod.POST)
	@ResponseBody
	public String join(String activityId,String ownerOpenId,HttpServletRequest request, HttpServletResponse response) {
		try {
			String openId = loginOpenId(request);
			if (StringUtils.isEmpty(openId)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"4","登录失效", null);
			}
			if (!StringUtils.isEmpty(activityId)&!StringUtils.isEmpty(ownerOpenId)) {
					Integer count = yuyueService.queryYuyueActivityJoinCounts(activityId, openId);
					if ( null != count && count > 0 ) {
						return AjaxWebUtil.sendAjaxResponse(request, response, false,"已经点赞", null);
					}else {
						YuyueActivityJoin yaj = new YuyueActivityJoin();
						yaj.setActivityId(activityId);
						yaj.setActivityOpenId(ownerOpenId);
						yaj.setOpenId(openId);
						WxUser wxUser = yuyueService.getWxUserByOpenId(openId);
						if ( null != wxUser) {
							yaj.setImage(wxUser.getImage());
							yaj.setNickName(wxUser.getNickName());
						}
						yuyueService.addYuyueActivityJoin(yaj);
						yuyueService.increaseActivityJoinCount(activityId);
					}
			}
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"操作成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"操作失败:"+e.getLocalizedMessage(), null);
		}
	}
}
