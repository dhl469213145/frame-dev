package com.framework.mybatiesDemo.dao;

import org.apache.ibatis.annotations.Mapper;


public interface OrdersDao {
    String payOrder(int id);
}
