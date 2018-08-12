package com.xin.seckill.pojo;

import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: UserQueryVo
 * @date 2018-08-11 20:57
 * @Copyright (C)2018 , Luchaoxin
 */
public class UserQueryVo {

    private List<Integer> ids;

    private User userCustom;

    public User getUserCustom() {
        return userCustom;
    }

    public void setUserCustom(User userCustom) {
        this.userCustom = userCustom;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
