package com.github.NervousOrange.springboot.dao;

import com.github.NervousOrange.springboot.entity.User;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;

public class MysqlDao {
    private final SqlSession sqlSession;

    @Inject
    public MysqlDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public User selectUserById(int id) {
        return this.sqlSession.selectOne("myMapper.selectUserByID", id);
    }
}
