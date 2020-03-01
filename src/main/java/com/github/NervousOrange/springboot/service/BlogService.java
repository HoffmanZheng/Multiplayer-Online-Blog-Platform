package com.github.NervousOrange.springboot.service;

import com.github.NervousOrange.springboot.controller.AuthController;
import com.github.NervousOrange.springboot.dao.BlogDao;
import com.github.NervousOrange.springboot.entity.Blog;
import com.github.NervousOrange.springboot.entity.BlogListResult;
import com.github.NervousOrange.springboot.entity.BlogResult;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {

    private BlogDao blogDao;

    @Inject
    public BlogService(BlogDao blogDao, AuthController authController) {
        this.blogDao = blogDao;
    }

    public BlogListResult getBlogList(Integer page, Integer pageSize, Integer userId, Boolean atIndex) {
        try {
            int totalBlogNum = blogDao.getToTalBlogNum();
            int totalPage = totalBlogNum % pageSize == 0 ? totalBlogNum / pageSize : totalBlogNum / pageSize + 1;
            List<Blog> blogList = blogDao.getBlogList(page, pageSize, userId);
            return BlogListResult.successfulBlogListResult("获取成功", totalBlogNum, page, totalPage, blogList);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            return BlogListResult.failBlogResult("系统异常");
        }
    }

    public BlogResult getBlogById(Integer blogId) {
        try {
            Blog blog = blogDao.getBlogById(blogId);
            return BlogResult.successfulBlogIdResult("获取成功", blog);
        } catch (Exception e) {
            return BlogResult.failBlogResult("系统异常");
        }
    }

    public BlogResult createBlog(String title, String content, String description, int userId) {
        int blogId = blogDao.insertNewBlog(title, content, description, userId);
        return BlogResult.successfulBlogIdResult("创建成功", blogDao.getBlogById(blogId));
    }

    public BlogResult patchBlogId(String title, String content, String description, int blogId, int userId) {
        Blog blogById = blogDao.getBlogById(blogId);
        if (userId != blogById.getUser().getId()) {
            return BlogResult.failBlogResult("无法修改别人的博客");
        }
        blogDao.updateBlogById(title, content, description, blogId);
        return BlogResult.successfulBlogIdResult("修改成功", blogDao.getBlogById(blogId));
    }

    public BlogResult deleteBlogById(int blogId, int userId) {
        Blog blogById = blogDao.getBlogById(blogId);
        if (userId != blogById.getUser().getId()) {
            return BlogResult.failBlogResult("无法删除别人的博客");
        }
        blogDao.deleteBlogById(blogId);
        return BlogResult.successfulDeleteBlogResult();
    }
 }
