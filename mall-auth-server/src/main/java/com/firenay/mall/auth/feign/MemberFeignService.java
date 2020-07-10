package com.firenay.mall.auth.feign;

import com.firenay.common.utils.R;
import com.firenay.mall.auth.vo.SocialUser;
import com.firenay.mall.auth.vo.UserLoginVo;
import com.firenay.mall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>Title: MemberFeignService</p>
 * Description：
 * date：2020/6/25 20:31
 */
@FeignClient("mall-member")
public interface MemberFeignService {

	@PostMapping("/member/member/register")
	R register(@RequestBody UserRegisterVo userRegisterVo);

	@PostMapping("/member/member/login")
	R login(@RequestBody UserLoginVo vo);

	@PostMapping("/member/member/oauth2/login")
	R login(@RequestBody SocialUser socialUser);
}
