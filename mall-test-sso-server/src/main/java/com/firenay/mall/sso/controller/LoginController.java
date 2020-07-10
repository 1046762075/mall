package com.firenay.mall.sso.controller;

import com.firenay.mall.sso.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: LoginController</p>
 * Description：
 * date：2020/6/27 15:26
 */
@Controller
public class LoginController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@ResponseBody
	@GetMapping("/userInfo")
	public String userInfo(@RequestParam("token") String token){
		String s = stringRedisTemplate.opsForValue().get(token);
		return s;
	}

	@GetMapping("/login.html")
	public String loginPage(User user, Model model,@CookieValue(value = "sso_token",required = false) String sso_token){
		if(!StringUtils.isEmpty(sso_token)){
			// 有人登录过
			return "redirect:" + user.getUrl() + "?username=" + user.getUsername() + "&token=" + sso_token;
		}
		model.addAttribute("url", user.getUrl());
		model.addAttribute("username", user.getUsername());
		return "login";
	}

	@PostMapping("/doLogin")
	public String doLogin(User user, HttpServletResponse response){
		if(!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword()) && "fire".equals(user.getUsername()) && "fire".equals(user.getPassword())){
			// 登录成功跳转 跳回之前的页面
			String uuid = UUID.randomUUID().toString().replace("-", "");
			Cookie cookie = new Cookie("sso_token", uuid);
			response.addCookie(cookie);
			stringRedisTemplate.opsForValue().set(user.getUser(), uuid, 30, TimeUnit.MINUTES);
			return "redirect:" + user.getUrl() + "?username=" + user.getUser() + "&token=" + uuid;
		}
		// 登录失败
		return "login";
	}
}
