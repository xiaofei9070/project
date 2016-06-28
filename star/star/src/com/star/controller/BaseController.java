package com.star.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	@ModelAttribute
	public void execPro(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		this.request = request;
		this.response = response;
		this.session = session;
	}
}
