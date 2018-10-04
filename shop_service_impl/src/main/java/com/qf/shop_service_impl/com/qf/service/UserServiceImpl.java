package com.qf.shop_service_impl.com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.IUserDao;
import com.qf.entity.User;
import com.qf.entity.UserStatus;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserDao iUserDao;
    @Override
    public UserStatus<User> checkUser(User user) {
        User user1 = iUserDao.checkUser(user);
        Integer core;
        String status;
        if(user1!=null){

            if(user1.getPassword().equals(user.getPassword())){
                core=1;
                status="succ";
            }else {
                //密码不正确
                core=2;
                status="psdfail";
                user1=new User();
            }
        }else{
                core=3;
                status="notUser";
                user1=new User();
        }
        UserStatus<User> userUserStatus = new UserStatus<>(core,status,user1);
        return userUserStatus;
    }
}
