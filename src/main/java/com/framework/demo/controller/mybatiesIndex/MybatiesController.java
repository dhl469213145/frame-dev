package com.framework.demo.controller.mybatiesIndex;

import com.framework.mybatiesDemo.dao.OrdersDao;
import com.framework.mybatiesDemo.dao.UserDao;
import com.framework.mybatiesDemo.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.InputStream;

@RestController
//@EnableAutoConfiguration
public class MybatiesController {

    private SqlSessionFactory sessionFactory;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private OrdersDao ordersDao;


    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public String findById() throws Exception {
        String path = "sqlMapConfig.xml";
        InputStream resource = Resources.getResourceAsStream(path);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resource);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user = dao.findUserById(1);

        sqlSession.close();
        return user == null ? "find null" : user.toString();
    }

    @RequestMapping(value="/payOrder", method = RequestMethod.GET)
    public String payOrder(String id) throws Exception {
//        String path = "sqlMapConfig.xml";
//        InputStream resource = Resources.getResourceAsStream(path);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resource);
//
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        OrdersDao dao = sqlSession.getMapper(OrdersDao.class);
        String result = ordersDao.payOrder(Integer.parseInt(id));


//        sqlSession.close();
        return result;
    }
}
