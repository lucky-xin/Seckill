package com.xin.seckill.service.login;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 登录结果类型
 * @date 2018-08-13 23:54
 * @Copyright (C)2018 , Luchaoxin
 */
public enum LoginResult {
    /**
     * 登录成功
     */
    SUCCESS(1, "登录成功"),
    NAME_OR_PASSWORD_ERROR(-1, "用户名或者密码错误"),
    DECODE_ERROR(-2, "解密出现错误"),
    PAGE_IS_OUTDATED(-3, "页面已经过期，请重新刷新页面登录");

    private int status;

    private String message;

    LoginResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static LoginResult getInstance(int status) {
        for (LoginResult value : values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        return null;
    }

    public static String getMessage(int status) {
        for (LoginResult value : values()) {
            if (value.getStatus() == status) {
                return value.getMessage();
            }
        }
        return "";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
