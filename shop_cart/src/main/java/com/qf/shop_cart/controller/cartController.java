package com.qf.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qf.entity.Cart;
import com.qf.entity.User;
import com.qf.entity.goods;
import com.qf.service.ICartService;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.Constant;
import utils.isLogin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/cartController")
public class cartController {
    @Reference
    private ICartService iCartService;
    @Reference
    private IGoodsService iGoodsService;

    @isLogin()
    @RequestMapping("/addCart")
    public String addCart(Cart cart,
                          User user,
                          HttpServletResponse response,
                          @CookieValue(value = "cart_token", required = false) String cartToken) {
        System.out.println("用户是否登录:" + user);
        System.out.println("------gid:" + cart.getGid());
        System.out.println("------gcount:" + cart.getGcount());
        System.out.println("cartToken___:" + cartToken);
        if (user != null && user.getId() != null) {
            //添加到数据库
            cart.setUid(user.getId());
            iCartService.addCart(cart);
        } else {
            List<Cart> list = null;
            if (cartToken != null) {
                TypeToken<List<Cart>> typeToken = new TypeToken<List<Cart>>() {
                };
                list = new Gson().fromJson(cartToken, typeToken.getType());
                list.add(cart);


            } else {
                list = Collections.singletonList(cart);
                //添加到cookie

            }
            System.out.println("追加后得购物车列表：" + list);
            String json = new Gson().toJson(list);
            try {
                json = URLEncoder.encode(json, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Cookie cookie = new Cookie(Constant.CART_TOKEN, json);
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

        }

        return "addcuss";
    }

    @ResponseBody
    @RequestMapping("/combine")
    public String combine(Integer uid, @CookieValue(value = "cart_token", required = false) String cartToken) {
        System.out.println("合并购物车:" + uid + "___" + cartToken);
        TypeToken<List<Cart>> typeToken = new TypeToken<List<Cart>>() {
        };
        List<Cart> carts = new Gson().fromJson(cartToken, typeToken.getType());
        for (Cart cart :
                carts) {
            cart.setUid(uid);
            iCartService.addCart(cart);
        }
        return "succ";
    }

    @isLogin
    @RequestMapping("/hasCarts")
    @ResponseBody
    public String hasCarts(User user, @CookieValue(value = "cart_token", required = false) String cartToken) {
        List<Cart> cartList = iCartService.getCartLists(user, cartToken);
        System.out.println("返回一个购物车列表到前台："+cartList);
        return "getCart(" + new Gson().toJson(cartList) + ")";
    }
    @isLogin
    @RequestMapping("/getCartList")
    public String getCartList(User user, @CookieValue(value = "cart_token", required = false) String cartToken, Model model){
        List<Cart> cartList = iCartService.getCartLists(user, cartToken);
        model.addAttribute("cartList",cartList);

        return "cartList";
    }
}