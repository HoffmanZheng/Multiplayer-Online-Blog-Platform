package com.github.NervousOrange.springboot.service;

import com.github.NervousOrange.springboot.dao.MysqlDao;
import com.github.NervousOrange.springboot.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserService {
    private MysqlDao mysqlDao;
    private AuthenticationManager authenticationManager;

    @Inject
    public UserService(MysqlDao mysqlDao, AuthenticationManager authenticationManager) {
        this.mysqlDao = mysqlDao;
        this.authenticationManager = authenticationManager;
    }

    public User getUserById(int id) {
        return this.mysqlDao.selectUserById(id);
    }

}
