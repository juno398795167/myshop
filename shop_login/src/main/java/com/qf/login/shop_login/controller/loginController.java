package com.qf.login.shop_login.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.entity.User;
import com.qf.entity.UserStatus;
import com.qf.service.IUserService;

import com.qf.utils.httpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.www.http.HttpClient;
import utils.Constant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class loginController {
    @Reference
    IUserService iUserService;

    @Autowired
    RedisTemplate redisTemplate;
    @RequestMapping("/tologin")
    public String tologin(String resultUrl, Model model){
        model.addAttribute("resultUrl",resultUrl);
        System.out.println("登录页面是否传到resultUrl："+resultUrl);
        return "login";
    }

    @RequestMapping("/login")
    public String login(User user,
                        HttpServletResponse response,
                        String resultUrl,
                        @CookieValue(value = "cart_token",required = false ) String cartToken){
        System.out.println("user_____:"+user);
        UserStatus<User> userUserStatus = iUserService.checkUser(user);
        System.out.println(userUserStatus);
        switch (userUserStatus.getCore()){
            case 1:
                if(resultUrl==null||resultUrl.equals("")){
                    resultUrl = "http://localhost:8082";
                }else {
                    resultUrl = resultUrl.replace("*","&");
                }
                String uuid = UUID.randomUUID().toString();
                redisTemplate.opsForValue().set(uuid,userUserStatus.getData());
                redisTemplate.expire(uuid,7, TimeUnit.DAYS);
                Cookie cookie = new Cookie(Constant.LOGIN_TOKEN,uuid);
                cookie.setPath("/");
                cookie.setMaxAge(7*24*60*60);
                response.addCookie(cookie);
                //登录的时候,合并购物车
                if(cartToken!=null){
                    Map<String,String> params = new HashMap<>();
                    params.put("uid",userUserStatus.getData().getId()+"");
                    Map<String,String> header = new HashMap<>();
                    try {
                        header.put("Cookie","cart_token="+ URLEncoder.encode(cartToken,"utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String result = httpClientUtils.sendPostParamAndHeader("http://localhost:8086/cartController/combine", params, header);
                    System.out.println("-----result:"+result);
                    if (result.equals("succ")){
                        Cookie cookie1 = new Cookie(Constant.CART_TOKEN,null);
                        cookie1.setPath("/");
                        cookie1.setMaxAge(0);
                        response.addCookie(cookie1);
                    }
                }

                return "redirect:"+resultUrl;
            default:
                return "login";

        }

    }
    @RequestMapping("/iflogin")
    @CrossOrigin
    @ResponseBody
    public String iflogin(@CookieValue(value = "login_token",required = false) String login_token){
        System.out.println("------>"+login_token);
        User user =null;
        try {
            user= (User)redisTemplate.opsForValue().get(login_token);
        }catch (Exception e){
            return "callback("+null+")";
        }

        return "callback("+new Gson().toJson(user)+")";

    }
    //注销
    @CrossOrigin
    @RequestMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie(Constant.LOGIN_TOKEN,"");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:http://localhost:8084/tologin";
    }
}
