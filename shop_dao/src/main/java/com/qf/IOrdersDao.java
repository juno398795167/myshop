package com.qf;

import com.qf.entity.Orderdetils;
import com.qf.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrdersDao  {

    Integer addOrder(Orders orders);

    Integer addOrderdetils(@Param("orderdetils") List<Orderdetils> orderdetils);

    List<Orders> queryByUid(Integer uid);

    Orders queryOrdersByOrderId(String orderId);

    Integer updataOrderByOrderidAndStatus(@Param("orderid") String orderid,@Param("status") Integer status);

}
