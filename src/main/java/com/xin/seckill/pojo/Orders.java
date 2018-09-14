package com.xin.seckill.pojo;

import java.util.Date;
import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: Orders
 * @date 2018-08-11 20:52
 * @Copyright (C)2018 , Luchaoxin
 */
public class Orders {

    private String num;

    private int id;

    private int userId;

    private String note;

    private String address;

    private Date createTime;

    private UserInfo user;

    private List<OrderDetail> orderdetails;

    public Orders() {
    }

    public Orders(int id, int userId, String num, String note, String address, Date createTime) {
        this.num = num;
        this.id = id;
        this.userId = userId;
        this.note = note;
        this.address = address;
        this.createTime = createTime;
    }


    public List<OrderDetail> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(List<OrderDetail> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Orders [num=" + num + ", id=" + id + ", userId=" + userId
                + ", note=" + note + ", address=" + address + ", createtime="
                + createTime + ", user=" + user + ", orderdetails="
                + orderdetails + "]";
    }
}
