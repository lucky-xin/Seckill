package com.xin.seckill.pojo;

import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: ItemsQueryVo
 * @date 2018-08-11 20:50
 * @Copyright (C)2018 , Luchaoxin
 */
public class ItemsQueryVo {
    private Items items;

    private UserInfo user;

    private List<Items> itemsList;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public Items getItemsCustom() {
        return items;
    }

    public void setItemsCustom(Items items) {
        this.items = items;
    }

    public List<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Items> itemsList) {
        this.itemsList = itemsList;
    }
}
