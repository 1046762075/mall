package com.sso.sample.controller;

import com.sso.core.conf.Conf;
import com.sso.core.entity.ReturnT;
import com.sso.core.user.SsoUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: IndexController</p>
 * Description：
 * date：2020/6/27 13:42
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {

        SsoUser xxlUser = (SsoUser) request.getAttribute(Conf.SSO_USER);
        model.addAttribute("xxlUser", xxlUser);
        return "index";
    }

    @RequestMapping("/json")
    @ResponseBody
    public ReturnT<SsoUser> json(Model model, HttpServletRequest request) {
        SsoUser xxlUser = (SsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT(xxlUser);
    }

    @ResponseBody
    @RequestMapping("/sso-web-springboot/logout")
    public String logout(){
    	return "退出成功";
	}
}