package com.xin.seckill.pojo;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 订单详情
 * @date 2018-08-11 20:53
 * @Copyright (C)2018 , Luchaoxin
 */
public class OrderDetail {

    private int ordersId;

    private int itemsId;

    private int id;

    private int itemsNum;

    public OrderDetail() {
    }

    public OrderDetail(int id, int ordersId, int itemsId, int itemsNum) {
        this.ordersId = ordersId;
        this.itemsId = itemsId;
        this.id = id;
        this.itemsNum = itemsNum;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public int getItemsId() {
        return itemsId;
    }

    public void setItemsId(int itemsId) {
        this.itemsId = itemsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemsNum() {
        return itemsNum;
    }

    public void setItemsNum(int itemsNum) {
        this.itemsNum = itemsNum;
    }
}
