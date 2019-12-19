package com.framework.mybatiesDemo;

import com.framework.mybatiesDemo.dao.OrdersDao;
import com.framework.mybatiesDemo.dao.UserDao;
import com.framework.mybatiesDemo.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class OrdersTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception{
        String resource = "sqlMapConfig.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }


    @Test
    public void testPayOrders() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrdersDao ordersDao = sqlSession.getMapper(OrdersDao.class);
        String rs = ordersDao.payOrder(3);

        System.out.println(rs);
        sqlSession.close();
    }

}
