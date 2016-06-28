package com.star.interceptor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebContextInterceptor implements ServletContextListener{
	
	public Logger logger = null;
	
	public static ApplicationContext app = null;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		app = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	}

}
