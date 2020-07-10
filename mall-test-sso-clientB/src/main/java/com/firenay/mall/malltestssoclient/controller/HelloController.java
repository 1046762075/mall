package com.firenay.mall.malltestssoclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>Title: HelloController</p>
 * Description：
 * date：2020/6/27 15:00
 */
@Controller
public class HelloController {

	@Value("${sso.server.url}")
	private String ssoServer;
	/**
	 * 无需登录
	 */
	@ResponseBody
	@GetMapping({"/hello"})
	public String hello(){
		return "hello";
	}

	@GetMapping("/boss")
	public String employees(@RequestParam(value = "username") String username , @RequestParam(value = "token",required = false) String token, HttpSession session){

		if(!StringUtils.isEmpty(token)){
			// 没登录
			return "redirect:" + this.ssoServer + "?url=http://clientb.com:81/boss&username=" + username;
		}
		ArrayList<String> list = new ArrayList<>();
		list.add("fire");
		list.add("zjl");
		list.add("xjs");
		list.add("nay");
		list.add("mqs");
		session.setAttribute("user", username);
		session.setAttribute("emps", list);
		return "list";
	}
}
