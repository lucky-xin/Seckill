package com.xin.seckill.pojo;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Luchaoxin
 * @Description: ${TODO}
 * @date 2018-09-13 23:30
 */
public class AgentInfo {
    /**
     * 身份证
     */
    private String idcard;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobile;

    private MultipartFile multipartFile;


    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String toString() {
        return "AgentInfo{" +
                "idcard='" + idcard + '\'' +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", multipartFile=" + multipartFile +
                '}';
    }
}
