package com.github.NervousOrange.springboot.service;

import com.github.NervousOrange.springboot.dao.MysqlDao;
import com.github.NervousOrange.springboot.entity.User;

import javax.inject.Inject;

public class UserService {
    private MysqlDao mysqlDao;

    @Inject
    public UserService(MysqlDao mysqlDao) {
        this.mysqlDao = mysqlDao;
    }

    public User getUserById(int id) {
        return this.mysqlDao.selectUserById(id);
    }
}
