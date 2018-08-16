package com.xin.seckill.service.impl;

import com.xin.seckill.dao.UserDao;
import com.xin.seckill.pojo.User;
import com.xin.seckill.service.PagingQueryService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 用户服务实现类
 * @date 2018-08-13 12:17
 * @Copyright (C)2018 , Luchaoxin
 */
public class UserServiceImpl implements PagingQueryService<User> {

    @Autowired
    private UserDao userDao;

    /**
     * 用户分页查询服务
     *
     * @param rowBounds
     * @return
     * @throws Exception
     */
    @Override
    public List<User> doPagingQuery(RowBounds rowBounds) throws Exception {
        return userDao.pagingQueryUserList(rowBounds);
    }


}
