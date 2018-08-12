package com.xin.seckill.exception.handler;

import com.google.common.collect.Maps;
import com.xin.seckill.exception.SeckillException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 异常处理类
 * @date 2018-08-11 15:43
 * @Copyright (C)2018 , Luchaoxin
 */
@ControllerAdvice
public class SeckillExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest request, Exception e) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("isSuccess", false);
        model.put("errMsg", e.getMessage());
        return model;
    }

    @ExceptionHandler(value = SeckillException.class)
    public ModelAndView myErrorHandler(SeckillException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("isSuccess", false);
        modelAndView.addObject("msg", ex.getMessage());
        return modelAndView;
    }
}
