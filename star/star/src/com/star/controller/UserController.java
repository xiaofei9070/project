package com.star.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.socket.TextMessage;

import com.star.anno.TestMain;
import com.star.model.User;
import com.star.service.UserService;
import com.star.test.RmiTest;
import com.star.websocket.SystemWebSocketHandler;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Resource
	private UserService userService;
	
	@Resource
	private RmiTest rmi;
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	@ResponseBody
	public String test(){
		User user = new User();
		user.setAge(12);
		user.setId(12);
		user.setName("12313");
		userService.test(user);
		/*rmi.delUser();
		
		new TestMain().test();*/
		
		return "中国";
	}
	
	@RequestMapping("/fileLoad")
	public String fileLoad(){
		MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = mhr.getFileMap();
		CommonsMultipartFile mFile = (CommonsMultipartFile) fileMap.get("file");
		if(mFile.getOriginalFilename() != null){
			String path = request.getServletContext().getRealPath("/");
			File file = new File(path + "upload/");
			if (!file.exists()) {
				file.mkdirs();
			}
			System.out.println(path);
			File writeFile = new File(path + "upload/","111." + suffix(mFile.getOriginalFilename()));
			try {
				mFile.getFileItem().write(writeFile);
			} catch (Exception e) {
				
			}
		}
		return "redirect:/index.jsp";
	}
	
	@Bean
	public SystemWebSocketHandler systemWebSocketHandler(){
		return new SystemWebSocketHandler();
	}
	
	
	@RequestMapping("/auditing")
	@ResponseBody
	public String auditing(){
		systemWebSocketHandler().sendMessageToUsers(new TextMessage("xiaoding1"));;
		return "ok";
	}
	
	private String suffix(String fileName) {
		int index = fileName.indexOf(".");
		return fileName.substring(index);
	}
	
}
