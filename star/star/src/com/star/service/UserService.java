package com.star.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.star.dao.UserDao1;
import com.star.model.User;

@Service
public class UserService {
	
	@Resource
	private UserDao1 userDao;
	
	public void test(User user){
		userDao.save(user);
	}
	
}
