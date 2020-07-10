package com.firenay.mall.malltestssoclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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

	@GetMapping("/employee")
	public String employees(@RequestParam(value = "username") String username , @RequestParam(value = "token",required = false) String token, HttpSession session){

		if(!StringUtils.isEmpty(token)){
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> entity = restTemplate.getForEntity("http://ssoserver.com:8080/userInfo?token=" + token, String.class);
			String body = entity.getBody();
			System.out.println(body);
			// 没登录
			return "redirect:" + this.ssoServer + "?url=http://clienta.com/employee&username=" + username;
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
