package com.jizhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizhi.dao.SysConfigDao;
@Service
public class SysConfigService {

	@Autowired
	private SysConfigDao sysConfigDao;
	
	public String getConfigValue(String type) {
		return sysConfigDao.getConfigValue(type);
	}
	
}
