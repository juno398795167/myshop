package com.qf.login.shop_login.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.entity.User;
import com.qf.entity.UserStatus;
import com.qf.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.Constant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class loginController {
    @Reference
    IUserService iUserService;

    @Autowired
    RedisTemplate redisTemplate;
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(User user, HttpServletResponse response){
        System.out.println(user);
        UserStatus<User> userUserStatus = iUserService.checkUser(user);
        switch (userUserStatus.getCore()){
            case 1:
                String uuid = UUID.randomUUID().toString();
                redisTemplate.opsForValue().set(uuid,userUserStatus.getData());
                redisTemplate.expire(uuid,7, TimeUnit.DAYS);
                Cookie cookie = new Cookie(Constant.LOGIN_TOKEN,uuid);
                cookie.setPath("/");
                cookie.setMaxAge(7*24*60*60);
                response.addCookie(cookie);
                return "redirect:http://localhost:8082";
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
