package com.firenay.mall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.firenay.common.constant.AuthServerConstant;
import com.firenay.common.exception.BizCodeEnum;
import com.firenay.common.utils.R;
import com.firenay.common.vo.MemberRsepVo;
import com.firenay.mall.auth.feign.MemberFeignService;
import com.firenay.mall.auth.feign.ThirdPartFeignService;
import com.firenay.mall.auth.vo.UserLoginVo;
import com.firenay.mall.auth.vo.UserRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>Title: LoginController</p>
 * Description：登录注册模块
 * date：2020/6/25 13:02
 */
@Slf4j
@Controller
public class LoginController {

	@Autowired
	private ThirdPartFeignService thirdPartFeignService;

	@Autowired
	private MemberFeignService memberFeignService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@GetMapping({"/login.html","/","/index","/index.html"})
	public String loginPage(HttpSession session){
		Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
		if(attribute == null){
			return "login";
		}
		return "redirect:http://glmall.com";
	}

	@PostMapping("/login")
	public String login(UserLoginVo userLoginVo, RedirectAttributes redirectAttributes, HttpSession session){
		// 远程登录
		R r = memberFeignService.login(userLoginVo);
		if(r.getCode() == 0){
			// 登录成功
			MemberRsepVo rsepVo = r.getData("data", new TypeReference<MemberRsepVo>() {});
			session.setAttribute(AuthServerConstant.LOGIN_USER, rsepVo);
			log.info("\n欢迎 [" + rsepVo.getUsername() + "] 登录");
			return "redirect:http://glmall.com";
		}else {
			HashMap<String, String> error = new HashMap<>();
			// 获取错误信息
			error.put("msg", r.getData("msg",new TypeReference<String>(){}));
			redirectAttributes.addFlashAttribute("errors", error);
			return "redirect:http://auth.glmall.com/login.html";
		}
	}

	@ResponseBody
	@GetMapping("/sms/snedcode")
	public R sendCode(@RequestParam("phone") String phone){

		// TODO 接口防刷
		String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
		if(null != redisCode && redisCode.length() > 0){
			long CuuTime = Long.parseLong(redisCode.split("_")[1]);
			if(System.currentTimeMillis() - CuuTime < 60 * 1000){
				return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
			}
		}
		String code = UUID.randomUUID().toString().substring(0, 6);
		String redis_code = code + "_" + System.currentTimeMillis();
		// 缓存验证码
		stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, redis_code , 10, TimeUnit.MINUTES);
		try {
			return thirdPartFeignService.sendCode(phone, code);
		} catch (Exception e) {
			log.warn("远程调用不知名错误 [无需解决]");
		}
		return R.ok();
	}

	/**
	 * TODO 重定向携带数据,利用session原理 将数据放在sessoin中 取一次之后删掉
	 *
	 * TODO 1. 分布式下的session问题
	 * 校验
	 * RedirectAttributes redirectAttributes ： 模拟重定向带上数据
	 */
	@PostMapping("/register")
	public String register(@Valid UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes){

		if(result.hasErrors()){

			// 将错误属性与错误信息一一封装
			Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, fieldError -> fieldError.getDefaultMessage()));
			// addFlashAttribute 这个数据只取一次
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:http://auth.glmall.com/reg.html";
		}
		// 开始注册 调用远程服务
		// 1.校验验证码
		String code = vo.getCode();

		String redis_code = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
		if(!StringUtils.isEmpty(redis_code)){
			// 验证码通过
			if(code.equals(redis_code.split("_")[0])){
				// 删除验证码
				stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
				// 调用远程服务进行注册
				R r = memberFeignService.register(vo);
				if(r.getCode() == 0){
					// 成功
					return "redirect:http://auth.glmall.com/login.html";
				}else{
					Map<String, String> errors = new HashMap<>();
					errors.put("msg",r.getData("msg",new TypeReference<String>(){}));
					redirectAttributes.addFlashAttribute("errors",errors);
					return "redirect:http://auth.glmall.com/reg.html";
				}
			}else{
				Map<String, String> errors = new HashMap<>();
				errors.put("code", "验证码错误");
				// addFlashAttribute 这个数据只取一次
				redirectAttributes.addFlashAttribute("errors", errors);
				return "redirect:http://auth.glmall.com/reg.html";
			}
		}else{
			Map<String, String> errors = new HashMap<>();
			errors.put("code", "验证码错误");
			// addFlashAttribute 这个数据只取一次
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:http://auth.glmall.com/reg.html";
		}
	}
}
