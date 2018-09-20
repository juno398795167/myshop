package com.qf.shop_back.com.qf.controller;

import com.qf.entity.goods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {
    @RequestMapping("/toPage/{pagename}")
    public String toPage(@PathVariable("pagename") String  pagename){
        System.out.println(pagename);
        return pagename;

    }
}
