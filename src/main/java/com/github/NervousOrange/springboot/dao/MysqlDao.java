package com.github.NervousOrange.springboot.dao;

import com.github.NervousOrange.springboot.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class MysqlDao {
    private final SqlSession sqlSession;

    @Inject
    public MysqlDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public User getUserByUsername(String username) {
        return this.sqlSession.selectOne("myMapper.getUserByUsername", username);
    }

    public void insertNewUser(User user) {
        this.sqlSession.insert("myMapper.insertNewUser", user);
    }
}
