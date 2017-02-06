package com.tmall.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jizhi.model.User;
import com.jizhi.service.UserService;
import com.ruanwei.tool.SmsClient;
import com.ruanwei.tool.SmsResult;
import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.filter.LoginUserUtil;
import com.simple.common.util.AjaxWebUtil;
import com.simple.weixin.auth.OAuthAccessToken;
import com.simple.weixin.auth.OAuthUserInfo;
import com.simple.weixin.auth.WeiXinAuth;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private UserService userService;
	
	private static Map<String, String> cacheValidateCode = new HashMap<String, String>();
	private static final String PRE = "【极指】";
	
	@RequestMapping(value = "sendSms",method=RequestMethod.POST)
	@ResponseBody
	public String sendSms(String phone,HttpServletRequest request, HttpServletResponse response) {
		try {
			String validateCode = getValidateCode();
			cacheValidateCode.put(phone, validateCode);
			SmsResult sr = SmsClient.sendMsg(PRE,phone, "验证码:"+validateCode);
			if (sr.isSuccess()) {
				return  AjaxWebUtil.sendAjaxResponse(request, response, true,"获取验证码成功", sr.getMsg());
			}else {
				return  AjaxWebUtil.sendAjaxResponse(request, response, false,"获取验证码失败:"+validateCode, sr.getMsg());
			}
		}catch(Exception e) {
			return  AjaxWebUtil.sendAjaxResponse(request, response, false,"获取验证码失败:"+e.getLocalizedMessage(), null);
		}
	}
	
	private String getValidateCode(){
		int[] chars = {0,1,2,3,4,5,6,7,8,9};
		String a = new String();
		for (int i = 0; i < 6; i++) {
			a += chars[(int)(Math.random()*10)];
		}
		return a;
	}
	
	@RequestMapping(value = "doLogin",method=RequestMethod.POST)
	@ResponseBody
	public String doLogin(String phone,String code,HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(code)) {
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"验证码不能为空", null);
			}
			if(!code.equals(cacheValidateCode.get(phone))){
				return AjaxWebUtil.sendAjaxResponse(request, response, false,"验证码错误", null);
			}
			User u = userService.getUser(phone);
			if ( null == u) {
				u = new User();
				u.setPhone(phone);
				userService.addUser(u);
			}
			LoginUserUtil.setCurrentUser(request, u);
			return AjaxWebUtil.sendAjaxResponse(request, response, true,"登录成功", null);
		}catch(Exception e) {
			e.printStackTrace();
			return AjaxWebUtil.sendAjaxResponse(request, response, false,"登录失败", e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value = "login",method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		return EnvPropertiesConfiger.getValue("loginPage");
	}
	
	/**
	 * 跳转到微信授权，然后回跳到微信支付页面
	 * @param code
	 * @param token
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toAuthPage",method=RequestMethod.GET)
	public String toAuthPage(int type,HttpServletRequest request, HttpServletResponse response) {
		String url = WeiXinAuth.getAuthUrl(EnvPropertiesConfiger.getValue("redirectAuthUrl")+"?&type="+type, true, "");
		return "redirect:"+url;
	}
	
	@RequestMapping(value = "toAuth",method=RequestMethod.GET)
	public String toAuth(String code,HttpServletRequest request, HttpServletResponse response){
		try {
			OAuthAccessToken authToken = WeiXinAuth.getOAuthAccessToken(code);
			if ( null == authToken ) {
				AjaxWebUtil.sendAjaxResponse(request, response, "授权失败：未获取到token.");
				return null;
			}
			OAuthUserInfo oi = WeiXinAuth.getOAuthUserInfo(authToken.getAccess_token(), authToken.getOpenid());
			if ( null == oi) {
				AjaxWebUtil.sendAjaxResponse(request, response, "授权失败：未获取到用户信息.");
				return null;
			}
			User sessionuser = (User) LoginUserUtil.getCurrentUser(request);
			User user = userService.getUser(sessionuser.getPhone());
			if(user == null){
				AjaxWebUtil.sendAjaxResponse(request, response, "用户不存在，请注册");
				return null;
			}
			String headimgUrl = oi.getHeadimgurl();
			String nickname = oi.getNickname();
			if (!StringUtils.isEmpty(headimgUrl)) {
				user.setWeiChatImage(headimgUrl);
			}
			if (!StringUtils.isEmpty(nickname)) {
				user.setWeiChatNo(nickname);
			}
			if (StringUtils.isEmpty(headimgUrl) && StringUtils.isEmpty(nickname)) {
			}else {
				userService.update(user);
			}
			LoginUserUtil.setCurrentUser(request, user);
			return "redirect:"+EnvPropertiesConfiger.getValue("dlloginPageUrl");
		}catch(Exception e) {
			AjaxWebUtil.sendAjaxResponse(request, response, "登录失败:"+e.getLocalizedMessage());
			return null;
		}
	}
	
}
