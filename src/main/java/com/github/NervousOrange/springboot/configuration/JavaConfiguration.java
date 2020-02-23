package com.github.NervousOrange.springboot.configuration;

import com.github.NervousOrange.springboot.dao.MysqlDao;
import com.github.NervousOrange.springboot.service.OrderService;
import com.github.NervousOrange.springboot.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
public class JavaConfiguration {
/*    @Bean
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
    }*/

    @Bean
    public AuthenticationManager authenticationManager() {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
    }
}
