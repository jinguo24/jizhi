package com.jizhi.controller;

import com.simple.common.util.Base64;
import com.simple.common.util.DesEncrypt;

public class LocalUtil {

	private static final String entryKey = "jzcoupky";
	
	public static String entry(String phone) {
		return Base64.getBase64(DesEncrypt.encrypt(phone, entryKey));
	}
	
	public static String decry(String content) {
		try {
			return DesEncrypt.decrypt(Base64.getFromBase64(content),entryKey);
		}catch(Exception e) {
			return "_jz_unkownphone";
		}
	}
}
