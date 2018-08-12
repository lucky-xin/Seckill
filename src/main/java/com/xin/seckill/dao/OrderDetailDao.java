package com.xin.seckill.dao;

import com.xin.seckill.pojo.OrderDetail;
import com.xin.seckill.pojo.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 订单明细dao
 * @date 2018-08-12 13:40
 * @Copyright (C)2018 , Luchaoxin
 */
@Mapper
public interface OrderDetailDao {

    @Insert("insert orderdetail(id,orders_id,items_id, item_num) values (#{id},#{ordersId},#{itemsId},#{itemsNum})")
    int insertOrderDetail(OrderDetail orderDetail) throws Exception;

    @Insert("insert into orders(id,user_id,num,create_time,note) values (#{id},#{userId},#{num},#{createTime},#{note})")
    int insertOrder(Orders orders) throws Exception;


}
