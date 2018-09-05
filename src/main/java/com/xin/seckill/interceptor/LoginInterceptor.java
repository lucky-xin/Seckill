package com.xin.seckill.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 登录拦截器
 * @date 2018-08-12 17:54
 * @Copyright (C)2018 , Luchaoxin
 */
public class LoginInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!super.preHandle(request, response, handler)) {
            return false;
        }
        // 获取请求的url
        String url = request.getRequestURI();
        // 判断url是否是公开 地址（实际使用时将公开 地址配置配置文件中）
        // 这里公开地址是登陆提交的地址
        if (url.indexOf("app") > 0 || url.indexOf("verify") > 0 || url.indexOf("download") > 0 || url.indexOf("login") > 0) {
            // 如果进行登陆提交，放行
            return true;
        }

        // 判断session
        HttpSession session = request.getSession();
        // 从session中取出用户身份信息
        String username = (String) session.getAttribute("username");

        if (username != null) {
            // 身份存在，放行
            return true;
        }

        // 执行这里表示用户身份需要认证，跳转登陆页面
//        request.getRequestDispatcher("/pages/login/login.ftl").forward(request, response);

        return true;
    }
}
