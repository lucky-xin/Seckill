package com.xin.seckill;

import com.github.pagehelper.PageHelper;
import com.xin.seckill.dao.OrdersCustomDao;
import com.xin.seckill.pojo.Orders;
import com.xin.seckill.pojo.OrdersCustom;
import com.xin.seckill.pojo.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 测试
 * @date 2018-08-12 10:36
 * @Copyright (C)2018 , Luchaoxin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class OrdersCustomMapperTests {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private PageHelper pageHelper;

    @Autowired
    private OrdersCustomDao ordersCustomDao;

    @Test
    public void testFindOrdersUser() throws Exception {
        List<OrdersCustom> customs = ordersCustomDao.findOrdersUser();
        System.out.println(customs.size() + "---------" + customs);
    }

    @Test
    public void testFindOrdersUserResultMap() throws Exception {
        List<Orders> customs = ordersCustomDao.findOrdersUserResultMap();
        UserInfo user = customs.get(0).getUser();
        System.out.println(user);
    }

    @Test
    public void testFindOrdersAndOrderDetailResultMap() throws Exception {
        List<Orders> customs = ordersCustomDao.findOrdersAndOrderDetailResultMap();
        System.out.println(customs.size());
    }

    @Test
    public void testFindUserAndItemsResultMap() throws Exception {
        List<UserInfo> customs = ordersCustomDao.findUserAndItemsResultMap();
        System.out.println(customs);
    }

    @Test
    public void testFindOrdersUserLazyLoading() throws Exception {
        // 执行select *from user_tbl where id=?
        List<Orders> list = ordersCustomDao.findOrdersUserLazyLoading();
        System.out.println(list.size());
        for (Orders orders : list) {
            // 执行select *from user_tbl where id=?
            UserInfo user = orders.getUser();// 延时加载
            System.out.println(user);
        }
    }


}
