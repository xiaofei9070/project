package com.star.rmi;

public class UserServiceImpl implements UserService{

	@Override
	public void insertUser(String username) {
		System.out.println("UserService.insertUser() inser user sucess..." 
				+ username); 
		
	}

	@Override
	public void delUser(String username) {
		System.out.println("UserService.delUser() inser user sucess..." 
				+ username); 
		
	}

}
