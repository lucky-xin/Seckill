package com.xin.seckill.interceptor;



import com.xin.utils.log.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 自定义拦截器
 * @date 2018-08-11 00:02
 * @Copyright (C)2018 , Luchaoxin
 */
public class BaseInterceptor implements HandlerInterceptor {

    private Logger logger = LogFactory.getLogger("BaseInterceptor");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        logger.info("UserAgent:" + request.getHeader("User-Agent"));
        logger.info("用户方位地址："+uri);
        String user = request.getRemoteUser();
        if (uri.startsWith("/js")) {
            return true;
        }
        return true;
    }
}
