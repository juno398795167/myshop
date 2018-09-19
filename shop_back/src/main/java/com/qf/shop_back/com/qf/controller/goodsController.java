package com.qf.shop_back.com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.goods;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/goods")
public class goodsController {
    @Reference
    private  IGoodsService iGoodsService;
    @RequestMapping(value = "/goodsList")
    public String goodsList(){

        System.out.println("稍微试试哈");

        List<goods> list = iGoodsService.queryAll();

        System.out.println(list.get(0));
        return null;
    }
}
