package com.xin.seckill.exception;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 登录异常类
 * @date 2018-08-13 23:59
 * @Copyright (C)2018 , Luchaoxin
 */
public class LoginException extends Exception {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Exception e) {
        super(e);
    }
}
