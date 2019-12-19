package com.framework.mybatiesDemo.dao;

import com.framework.mybatiesDemo.model.User;

public interface UserDao {

    //根据用户id查询用户信息
    public User findUserById(int id) throws Exception;
}
