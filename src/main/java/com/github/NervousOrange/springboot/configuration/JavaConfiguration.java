package com.github.NervousOrange.springboot.configuration;

import com.github.NervousOrange.springboot.dao.MysqlDao;
import com.github.NervousOrange.springboot.service.OrderService;
import com.github.NervousOrange.springboot.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {
    @Bean
    public OrderService orderService(SqlSession sqlSession) {
        return new OrderService(new UserService(new MysqlDao(sqlSession)));
    }

    @Bean
    public UserService userService(MysqlDao mysqlDao) {
        return new UserService(mysqlDao);
    }

    @Bean
    public MysqlDao mysqlDao(SqlSession sqlSession) {
        return new MysqlDao(sqlSession);
    }
}
