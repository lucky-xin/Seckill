package com.xin.seckill.pojo;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description:
 * @date 2018-08-12 12:46
 * @Copyright (C)2018 , Luchaoxin
 */
public class OrdersCustom extends Orders {

    private String name;

    private String sex;

    private String address;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "OrdersCustom [name=" + name + ", sex=" + sex + ", address=" + address + ", email=" + email + "]";
    }

}
