package com.xin.seckill.controllers;

import com.xin.seckill.pojo.Token;
import com.xin.seckill.util.RandomCodeImgUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/app/verify")
public class VerifyController {
    // 生成校验码
    @Token(save = true)//生成Token
    @RequestMapping("/check_code")
    public void generateCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
            new RandomCodeImgUtil().generatePicture(request, response);
    }

}
