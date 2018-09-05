package com.xin.seckill;

import com.github.pagehelper.PageHelper;
import com.xin.seckill.dao.OrderDetailDao;
import com.xin.seckill.pojo.OrderDetail;
import com.xin.seckill.pojo.Orders;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 测试
 * @date 2018-08-12 10:36
 * @Copyright (C)2018 , Luchaoxin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class OrdersDetailMapperTests {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private PageHelper pageHelper;

    @Autowired
    private OrderDetailDao orderDetailDao;


    @Test
    public void testAdd() throws Exception {
        int count = 10;
        for (int i = 0; i < count; i++) {
            OrderDetail orderDetail = new OrderDetail(10 + i, i + 1, i + 1, 100);
            int result = orderDetailDao.insertOrderDetail(orderDetail);
        }
    }

    @Test
    public void testAddOrders() throws Exception {
        int count = 200;
        Orders orders = null;
        for (int i = 0; i < count; i++) {
            orders = new Orders(i + 30, i, "2", "剁手", "广州", new Date());
            int result = orderDetailDao.insertOrder(orders);
            System.out.println(result);
        }
//        orders = new Orders(22222, 1, null, null, null, new Date());
    }


}
