package com.qf.service;

import com.qf.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderService {
    String addOrderAndOrderdetils(Integer[] cids,Integer aid,Integer uid);
    List<Orders> queryByUid(Integer uid);
    Orders queryOrdersByOrderId(String orderId);
    Integer updataOrderByOrderidAndStatus(@Param("orderid") String orderid, @Param("status") Integer status);
}
