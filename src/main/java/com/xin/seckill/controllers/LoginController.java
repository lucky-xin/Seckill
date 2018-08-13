package com.xin.seckill.controllers;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 登录服务类
 * @date 2018-08-12 17:40
 * @Copyright (C)2018 , Luchaoxin
 */

import com.xin.seckill.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@RequestMapping("/app")
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(User user, Model model) {
        String name = user.getName();
        String password = user.getPassword();

        System.out.println(user);
        model.addAttribute("username", name);
        return "/pages/index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(User user) {
        return null;
    }
}
