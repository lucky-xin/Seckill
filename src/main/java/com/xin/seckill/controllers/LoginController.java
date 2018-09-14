package com.xin.seckill.controllers;


import com.alibaba.fastjson.JSONObject;
import com.xin.seckill.pojo.AgentInfo;
import com.xin.seckill.pojo.ReservationInfo;
import com.xin.seckill.pojo.Token;
import com.xin.seckill.pojo.UserInfo;
import com.xin.seckill.service.login.LoginResult;
import com.xin.seckill.service.login.LoginService;
import com.xin.seckill.util.AesEncryptUtil;
import com.xin.utils.StringUtil;
import com.xin.utils.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 登录服务类
 * @date 2018-08-12 17:40
 * @Copyright (C)2018 , Luchaoxin
 */

@Component
@RequestMapping("/app")
public class LoginController {

    String key = "reservation:system:user:image";

    @Autowired
    private LoginService loginService;

    @Token
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid UserInfo user, Model model, HttpServletRequest request, Date date) throws Exception {

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
    public String logout(UserInfo user) {
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

    @ResponseBody
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public String jsonTest(ReservationInfo reservationInfo) {
        // 直接将json信息打印出来
//        System.out.println(jsonParam.toJSONString());

        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("method", "json");
//        result.put("data", jsonParam);

        return result.toJSONString();
    }

    /**
     * 只能有一个MultipartFile参数
     *
     * @param agentInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadImg(AgentInfo agentInfo) throws Exception {
        byte[] imgByte = agentInfo.getMultipartFile().getBytes();
        Jedis jedis = RedisClient.getJedisPool().getResource();
        jedis.hset(key.getBytes(), StringUtil.md5(new String(imgByte)).getBytes(), imgByte);
        return "上传成功";
    }

    @RequestMapping("/img/upload/page")
    public String uploadImgPage() {

        return "/pages/uploadImage";
    }

}
