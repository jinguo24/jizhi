/**
 * 
 */
package com.tmall.admin.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.tmall.model.ResponseInfo;
import com.tmall.model.ResponseStatus;

/**
 * @author hesq1
 * @date 2016年2月18日
 * @desc 
 */
public class AjaxWebUtil extends WebUtils{
	
	
	public static void sendAjaxResponse(ServletRequest request, ServletResponse response, Object data,int status){
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setStatus(status);
		sendAjaxResponse(request, response, data);
	}
	
	public static void sendAjaxResponse(ServletRequest request, ServletResponse response, Object data){
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
			String dataStr = JSON.toJSON(data).toString();
			out.println(dataStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
	
	public static void sendAjaxResponse(ServletRequest request, ServletResponse response,boolean successed,String msg,Object o) {
		sendAjaxResponse(request,response,new ResponseInfo(new ResponseStatus(successed,msg), o));
	}
	
	public static boolean isAjaxRequest(ServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest)request).getHeader("X-Requested-With"))?true:false;
	}
	
	public static String getUri(String url){
//		String pattern = "^((http[s]?|ftp):\\/)?\\/?([^:\\/\\s]+)((\\/\\w+)*\\/)([\\w\\-\\.]+[^#?\\s]+)(.*)?(#[\\w\\-]+)?$";
//		String pattern = "^(.*:)\\/\\/([A-Za-z0-9\\-\\.]+)(:[0-9]+)?(.*)$";
		String pattern = "^((http[s]?|ftp):\\/)?\\/?([^:\\/\\s]+)(:([^\\/]*))?((\\/\\w+)*\\/)([\\w\\-\\.]+[^#?\\s]+)(\\?([^#]*))?(#(.*))?$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(url);
	      if (m.find( )) {
	         String path = m.group(6);
	         String name = m.group(8);
	         return path + name;
	      } else {
	         System.out.println("NO MATCH");
	      }
	      return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getUri(""));
	}
}
