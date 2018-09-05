package com.xin.seckill.pojo;

import com.xin.seckill.enums.Sex;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 用户model类
 * @date 2018-08-11 20:48
 * @Copyright (C)2018 , Luchaoxin
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String email;

    private String name;

    private Sex sex;

    private Date birth;

    private String address;

    private byte[] image;

    private String password;

    private List<Orders> ordersList;

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }

    public String getPassword() {
        return password;
    }
//
//    @Override
//    public String getUsername() {
//        return name;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String email, String name, Sex sex, Date birth,
                String address, String password) {
        super();
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.address = address;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password
                + ", email=" + email + ", sex=" + sex + ", birth=" + birth
                + ", address=" + address + "]";
    }


}
