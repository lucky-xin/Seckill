package com.xin.seckill.interceptor;

import com.xin.seckill.pojo.Token;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: Token拦截器
 * @date 2018-08-12 17:58
 * @Copyright (C)2018 , Luchaoxin
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);

            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                HttpSession session = request.getSession(false);
                if (needSaveSession) {
                    if (null != session) {
                        session.setAttribute("token", UUID.randomUUID().toString());
                    }
                }

                boolean needRemoveSession = annotation.remove();

                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return false;
                    }
                    if (session != null) {
                        session.removeAttribute("token");
                    }
                }
            }
        }

        return true;
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        //取出存储在Session中的token

        HttpSession session = request.getSession(false);

        if (null == session) {
            return false;
        }

        String serverToken = (String) session.getAttribute("token");
        //1、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
        if (serverToken == null) {
            return true;
        }
        //2、如果用户提交的表单数据中没有token，则用户是重复提交了表单
        String clientToken = request.getParameter("token");
        if (clientToken == null) {
            return true;
        }

        //3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
        if (!serverToken.equals(clientToken)) {
            return true;
        }
        return false;
    }

}
