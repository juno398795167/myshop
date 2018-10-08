package com.qf.shop_order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Address;
import com.qf.entity.Cart;
import com.qf.entity.Orders;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.isLogin;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/orderController")
public class orderController {
    @Reference
    ICartService iCartService;
    @Reference
    IAddressService iAddressService;
    @Reference
    IOrderService iOrderService;
    @isLogin(tologin = true)
    @RequestMapping("/getArrayId")
    public String getArrayId(Integer[] gid, User user, Model model){
        System.out.println(Arrays.toString(gid));
        System.out.println("用户信息获取："+user);
        List<Cart> carts = iCartService.queryCartBygidanduid(gid, user.getId());
        List<Address> addresses = iAddressService.queryAllByUid(user.getId());
        System.out.println("查询商品的信息:"+carts);
        model.addAttribute("carts",carts);
        model.addAttribute("addresses",addresses);
        return "orderedit";
    }
    @isLogin
    @RequestMapping("/addAddress")
    @ResponseBody
    public Address addAddress(User user,Address address){
        address.setUid(user.getId());
        System.out.println(address);
        Address address1 = iAddressService.addAddress(address);
        return address1;
    }
    @isLogin
    @RequestMapping("/addOrder")
    @ResponseBody
    public String addOrder(Integer[] cids,Integer aid,User user){
        System.out.println("cids_____:"+Arrays.toString(cids));
        System.out.println("aid_____:"+aid);
        String ordersid = null;
        try {
            ordersid = iOrderService.addOrderAndOrderdetils(cids, aid, user.getId());

        }catch (Exception e){
            e.printStackTrace();
        }
        //删除购物车

        return ordersid;
    }
    @isLogin
    @RequestMapping("/queryOrderList")
    public String queryOrderList(User user,Model model){
        List<Orders> orders = iOrderService.queryByUid(user.getId());
        model.addAttribute("orders",orders);
        return "orderList";
    }
}
