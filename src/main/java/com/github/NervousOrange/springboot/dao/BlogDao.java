package com.github.NervousOrange.springboot.dao;

import com.github.NervousOrange.springboot.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BlogDao {
    private final SqlSession sqlSession;

    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogList(Integer page, Integer pageSize, Integer userId) {
       /* int offset = (page - 1) * pageSize;
        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("limit", pageSize);
        param.put("userId", userId);*/
        return sqlSession.selectOne("blogMapper.getBlogList");
    }

    public int getToTalBlogNum() {
        return sqlSession.selectOne("blogMapper.getTotalBlogNum");
    }

    public List<Blog> getBlogById(Integer blogId) {
        return sqlSession.selectList("blogMapper.getBlogById", blogId);
    }

    public int insertNewBlog(String title, String content, String description, Integer userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("title", title);
        param.put("content", content);
        param.put("description", description);
        param.put("userId", userId);
        return sqlSession.insert("blogMapper.insertNewBlog", param);
    }

    public int updateBlogById(String title, String content, String description, Integer blogId) {
        Map<String, Object> param = new HashMap<>();
        param.put("title", title);
        param.put("content", content);
        param.put("description", description);
        param.put("blogId", blogId);
        return sqlSession.update("blogMapper.updateBlogById", param);
    }

    public void deleteBlogById(Integer blogId) {
        sqlSession.delete("blogMapper.deleteBlogById");
    }
}
