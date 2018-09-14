package com.xin.seckill.service.login;


import com.xin.seckill.dao.UserDao;
import com.xin.seckill.pojo.UserInfo;
import com.xin.seckill.util.AesEncryptUtil;

import com.xin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 登录服务
 * @date 2018-08-13 23:38
 * @Copyright (C)2018 , Luchaoxin
 */
@Service(value = "loginService")
public class LoginService {

    @Autowired
    private UserDao userDao;

    public LoginResult login(UserInfo user, HttpServletRequest request) {
        HttpSession session = request.getSession();

        String encryptKey = StringUtil.toString(session.getAttribute("encrypt_key"));
        String encryptIv = StringUtil.toString(session.getAttribute("encrypt_iv"));

        if (StringUtil.isEmpty(encryptIv) || StringUtil.isEmpty(encryptKey)) {
            return LoginResult.PAGE_IS_OUTDATED;
        }

        String decodePassword;
        String cipherPassword = user.getPassword();
        try {
            decodePassword = new String(AesEncryptUtil.aesDecrypt(cipherPassword, encryptKey, encryptIv));
        } catch (Exception e) {
            return LoginResult.DECODE_ERROR;
        }
        user.setPassword(decodePassword);
        int count = userDao.findUserByNameAndPassword(user);
        user = null;
        session = null;
        return count > 1 ? LoginResult.SUCCESS : LoginResult.NAME_OR_PASSWORD_ERROR;
    }
}
