package com.jizhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jizhi.dao.SysPhoneDao;
@Service
public class SysPhoneService {

	@Autowired
	private SysPhoneDao sysPhoneDao;
	
	public boolean isExists(String phone) {
		Integer count = sysPhoneDao.getCount(phone);
		if ( null != count && count >=1 ) {
			return true;
		}
		return false;
	}
	
}
