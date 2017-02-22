package com.jizhi.task;

import org.springframework.stereotype.Component;
import com.simple.weixin.auth.WeiXinAuth;
@Component
public class WeixinTask {

	public void initGlobalAccessToken() {
		WeiXinAuth.installAcessToken();
		System.out.println("init accesstoken");
	}
	
	public void initJsTicket() {
		WeiXinAuth.installJsTicket();
		System.out.println("init jsticket");
	}
}
