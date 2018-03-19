package com.jizhi.controller;

import com.simple.common.util.Base64;
import com.simple.common.util.DesEncrypt;

public class LocalUtil {

	private static final String login_entryKey = "jzcoupky";
	
	public static String entry(String phone) {
		return entry(phone, login_entryKey);
	}
	
	public static String decry(String content) {
		return decry(content,login_entryKey);
	}
	
	private static final String leader_entryKey = "jzleader";
	
	public static String entryLeader(String phone) {
		return entry(phone, leader_entryKey);
	}
	
	public static String decryLeader(String content) {
		return decry(content,leader_entryKey);
	}
	
	private static final String yuyue_entryKey = "jzyuyueo";
	
	public static String entryYuyue(String openId) {
		return entry(openId, yuyue_entryKey);
	}
	
	public static String decryYuyue(String content) {
		return decry(content,yuyue_entryKey);
	}
	
	private static String entry(String content,String key) {
		return Base64.getBase64(DesEncrypt.encrypt(content, key));
	}
	
	private static String decry(String content,String key) {
		try {
			return DesEncrypt.decrypt(Base64.getFromBase64(content),key);
		}catch(Exception e) {
			return "_jz_unkownphone";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(entryLeader("d79b06c9-c1aa-41d3-b4b0-261734a63294"));
	}
	

	
}
