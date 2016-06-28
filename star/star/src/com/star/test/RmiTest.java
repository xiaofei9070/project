package com.star.test;


import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.star.rmi.UserService;
import com.text.utils.NumberFormateUtil;
import com.text.utils.NumberFormateUtil.NumberClass;

@Component
public class RmiTest {
	
	@Autowired
	private UserService userService;
	
	
	public void delUser(){
		userService.delUser("111");
	}
	
	public static void main(String[] args) {
		/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring_rmi_service.xml");
		UserService us = (UserService) context.getBean("userService");*/
		/*Long s = System.currentTimeMillis();
		Long free = Runtime.getRuntime().freeMemory();
		for(int i=0;i<1000000;i++){
			Long freeGc = Runtime.getRuntime().totalMemory();
			System.out.println("gc-----" + freeGc);
		}
		Long freeAfter = Runtime.getRuntime().freeMemory();
		
		Long e = System.currentTimeMillis();
		Long t = Runtime.getRuntime().totalMemory();
		System.out.println(e - s);
		System.out.println(t - freeAfter);
		Long freeGc = Runtime.getRuntime().totalMemory();
		System.out.println("gc-----" + freeGc);*/
		
	}
	

}
