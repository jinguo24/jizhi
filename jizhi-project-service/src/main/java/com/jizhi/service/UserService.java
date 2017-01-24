package com.jizhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jizhi.dao.UserDao;
import com.jizhi.model.User;
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public void addUser(User user) {
		userDao.addUser(user);
	}
	
	public void update(User user) {
		userDao.update(user);
	}
	
	public User getUser(String phone) {
		return userDao.getUserByPhone(phone);
	}
	
}
