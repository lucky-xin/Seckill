package com.xin.seckill.dao;

import com.xin.seckill.pojo.Orders;
import com.xin.seckill.pojo.OrdersCustom;
import com.xin.seckill.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: OrdersCustomDao
 * @date 2018-08-12 12:44
 * @Copyright (C)2018 , Luchaoxin
 */

@Mapper
public interface OrdersCustomDao {

    /**
     * 查询订单关联查询用户
     *
     * @return
     * @throws Exception
     */
    @Select("SELECT user_info.name,user_info.address,user_info.email,user_info.sex from orders,user_info where orders.user_id=user_info.id group by orders.user_id")
    List<OrdersCustom> findOrdersUser() throws Exception;

    @Select("SELECT orders.*,user_info.name,user_info.address,user_info.email,user_info.sex from orders,user_info where orders.user_id=user_info.id;")
    @Results({
            @Result(column="id",property="id"),
            @Result( column="user_id", property="userId"),
            @Result(column="num", property="num"),
            @Result(column="note", property="note"),
            @Result(column="create_time", property="createTime"),
            @Result(column="address", property="address"),
            @Result(column = "user_id", property = "user", many = @Many(select = "com.xin.seckill.dao.UserDao.findUserById"))
    })
    List<Orders> findOrdersUserResultMap() throws Exception;


    /**
     * 查询订单(关联用户)及订单明细
     *
     * @return
     * @throws Exception
     */
    @Select("SELECT orders.*,user_info.name,user_info.address,user_info.email,user_info.sex,orderdetail.items_id," +
            " orderdetail.orders_id,orderdetail.id as orderdetail_id,orderdetail.item_num " +
            " from orders,user_info,orderdetail where orders.user_id=user_info.id and orders.id=orderdetail.orders_id")
    @ResultMap("com.xin.seckill.dao.OrdersCustomDao.OrdersAndOrderdetailResultMap")
    List<Orders> findOrdersAndOrderDetailResultMap() throws Exception;

    /**
     * 查询用户购买商品信息
     *
     * @return
     * @throws Exception
     */
    List<User> findUserAndItemsResultMap() throws Exception;

    /**
     * 延时加载
     *
     * @return
     * @throws Exception
     */

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "num", property = "num"),
            @Result(column = "note", property = "note"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "user_id", property = "user", one = @One(select = "com.xin.seckill.dao.UserDao.findUserById"))
    })
    @Select("select id,user_id,num,create_time,note from orders")
    List<Orders> findOrdersUserLazyLoading() throws Exception;
}
