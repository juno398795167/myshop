package com.qf.service;

import com.qf.entity.User;
import com.qf.entity.UserStatus;

public interface IUserService {
    public UserStatus<User> checkUser(User user);
}
