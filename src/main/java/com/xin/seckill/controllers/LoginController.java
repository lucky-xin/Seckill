package com.xin.seckill.controllers;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 登录服务类
 * @date 2018-08-12 17:40
 * @Copyright (C)2018 , Luchaoxin
 */

import biz.datainsights.utils.StringUtil;
import com.xin.seckill.pojo.Token;
import com.xin.seckill.pojo.User;
import com.xin.seckill.service.login.LoginResult;
import com.xin.seckill.service.login.LoginService;
import com.xin.seckill.util.AesEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestMapping("/app")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Token
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, Model model, HttpServletRequest request) throws Exception {

        LoginResult loginResult = loginService.login(user, request);
        if (LoginResult.SUCCESS == loginResult) {
            request.getSession().setAttribute("username", user.getName());
            return "/pages/index";
        }
        String url = request.getHeader("referer");

        model.addAttribute("status", loginResult.getStatus());
        model.addAttribute("message", loginResult.getMessage());
        return "/pages/index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(User user) {
        return null;
    }

    @GetMapping(value = "/login/init")
    public String loginPage(Model model, HttpServletRequest request) {
        String base64key = StringUtil.getRandomString(16);
        String base64iv = StringUtil.getRandomString(16);
        String token = StringUtil.getUUID();
        request.getSession().setAttribute("encrypt_key", base64key);
        request.getSession().setAttribute("encrypt_iv", base64iv);
        request.getSession().setAttribute("token", token);

        model.addAttribute("token", token);
        model.addAttribute("base64key", AesEncryptUtil.getJsBase64String(base64key));
        model.addAttribute("base64iv", AesEncryptUtil.getJsBase64String(base64iv));
        return "/pages/login/login";
    }

}
