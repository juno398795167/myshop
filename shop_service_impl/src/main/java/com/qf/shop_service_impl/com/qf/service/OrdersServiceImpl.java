package com.qf.shop_service_impl.com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.IAddressDao;
import com.qf.ICartDao;
import com.qf.IOrdersDao;
import com.qf.entity.Address;
import com.qf.entity.Cart;
import com.qf.entity.Orderdetils;
import com.qf.entity.Orders;
import com.qf.service.IAddressService;
import com.qf.service.IOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class OrdersServiceImpl implements IOrderService {
    @Autowired
    IOrdersDao iOrdersDao;
    @Autowired
    ICartDao iCartDao;
    @Autowired
    IAddressDao iAddressDao;
    @Transactional
    @Override
    public String addOrderAndOrderdetils(Integer[] cids, Integer aid, Integer uid) {
        List<Cart> carts = iCartDao.queryByCid(cids);
        Address address = iAddressDao.queryByid(aid);
        double allprice=0;
        for (int i = 0; i <carts.size(); i++) {
            allprice += carts.get(i).getGcount()*carts.get(i).getGoods().getPrice();
        }
        System.out.println("总价格:"+allprice);
        Orders orders = new Orders();
        orders.setOrderid(UUID.randomUUID().toString());
        orders.setUid(uid);
        orders.setPerson(address.getPerson());
        orders.setAddress(address.getAddress());
        orders.setPhone(address.getPhone());
        orders.setCode(address.getCode());
        orders.setOprice(allprice);
        orders.setStatus(0);
        orders.setOrdertime(new Date());
        iOrdersDao.addOrder(orders);

        List<Orderdetils> orderdetilsList = new ArrayList<>();
        for(Cart cart:carts){
            Orderdetils orderdetils = new Orderdetils();
            orderdetils.setOid(orders.getId());
            orderdetils.setGid(cart.getGid());
            orderdetils.setGcount(cart.getGcount());
            orderdetils.setGimage(cart.getGoods().getGimage());
            orderdetils.setGinfo(cart.getGoods().getGinfo());
            orderdetils.setGname(cart.getGoods().getTitle());
            orderdetils.setPrice(cart.getGoods().getPrice());
            orderdetilsList.add(orderdetils);
        }
        iOrdersDao.addOrderdetils(orderdetilsList);
        for (int i = 0; i <carts.size(); i++) {
            iCartDao.deleteCartById(carts.get(i).getId());
        }
        return orders.getOrderid();
    }

    @Override
    public List<Orders> queryByUid(Integer uid) {
        return iOrdersDao.queryByUid(uid);
    }

    @Override
    public Orders queryOrdersByOrderId(String orderId) {
        return iOrdersDao.queryOrdersByOrderId(orderId);
    }

    @Override
    public Integer updataOrderByOrderidAndStatus( String orderid,Integer status) {
        return iOrdersDao.updataOrderByOrderidAndStatus(orderid,status);
    }
}
