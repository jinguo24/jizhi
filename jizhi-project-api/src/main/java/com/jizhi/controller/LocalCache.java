package com.jizhi.controller;

import java.util.HashMap;
import java.util.Map;

public class LocalCache {

	private static Map<String, String> cacheCode = new HashMap<String, String>();
	
	public static void setCache(String phone,String code) {
		cacheCode.put(phone, code);
	}
	
	public static boolean codeValid(String phone,String code) {
		String ocode = cacheCode.get(phone);
		return code.equals(ocode);
	}
	
}
