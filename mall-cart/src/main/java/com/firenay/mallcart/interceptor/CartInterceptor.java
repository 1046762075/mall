package com.firenay.mallcart.interceptor;

import com.firenay.common.constant.AuthServerConstant;
import com.firenay.common.constant.CartConstant;
import com.firenay.common.vo.MemberRsepVo;
import com.firenay.mallcart.vo.UserInfoTo;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * <p>Title: CartInterceptor</p>
 * Description：在执行目标之前 判断用户是否登录,并封装
 * date：2020/6/27 22:27
 */
public class CartInterceptor implements HandlerInterceptor {

	public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		UserInfoTo userInfoTo = new UserInfoTo();
		HttpSession session = request.getSession();
		MemberRsepVo user = (MemberRsepVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
		if (user != null){
			// 用户登陆了
			userInfoTo.setUsername(user.getUsername());
			userInfoTo.setUserId(user.getId());
		}
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if(name.equals(CartConstant.TEMP_USER_COOKIE_NAME)){
					userInfoTo.setUserKey(cookie.getValue());
					userInfoTo.setTempUser(true);
				}
			}
		}
		// 如果没有临时用户 则分配一个临时用户
		if (StringUtils.isEmpty(userInfoTo.getUserKey())){
			String uuid = UUID.randomUUID().toString().replace("-","");
			userInfoTo.setUserKey("FIRE-" + uuid);
		}
		threadLocal.set(userInfoTo);
		return true;
	}

	/**
	 * 执行完毕之后分配临时用户让浏览器保存
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		UserInfoTo userInfoTo = threadLocal.get();
		if(!userInfoTo.isTempUser()){
			Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
			// 设置这个cookie作用域 过期时间
			cookie.setDomain("glmall.com");
			cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIME_OUT);
			response.addCookie(cookie);
		}
	}
}
