package com.jizhi.controller;

import java.util.HashMap;
import java.util.Map;

public class LocalCache {

	private static Map<String, String> cacheCode = new HashMap<String, String>();
	private static Map<String,String> validateCode = new HashMap<String, String>();
	
	public static void setCache(String phone,String code) {
		cacheCode.put(phone, code);
	}
	
	public static boolean codeValid(String phone,String code) {
		String ocode = cacheCode.get(phone);
		return code.equals(ocode);
	}
	
	public static void setValidateCode(String phone,String code) {
		validateCode.put(phone, code);
	}
	
	public static boolean validateCodeValid(String phone,String code) {
		String ocode = validateCode.get(phone);
		return code.equals(ocode);
	}
}
