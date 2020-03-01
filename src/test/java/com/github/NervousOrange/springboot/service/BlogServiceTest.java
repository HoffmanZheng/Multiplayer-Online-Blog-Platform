package com.github.NervousOrange.springboot.service;

import com.github.NervousOrange.springboot.dao.BlogDao;
import com.github.NervousOrange.springboot.entity.BlogResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {
    @Mock
    BlogDao blogDao;
    @InjectMocks
    BlogService blogService;

    @Test
    void testSuccessfulGetBlogList() {
        blogService.getBlogList(1, 10, null, true);
        Mockito.verify(blogDao).getBlogList(1, 10, null);
    }

    @Test
    void testFailedGetBlogList() {
        Mockito.when(blogDao.getBlogList(1, 10, null)).thenThrow(new RuntimeException("System Error!"));
        BlogResult blogResult = blogService.getBlogList(1, 10, null, true);
        Assertions.assertTrue(blogResult.getMsg().contains("系统异常"));
    }

    @Test
    void testGetBlogById() {

    }

    @Test
    void testPostBlog() {

    }

    @Test
    void testPatchBlogById() {

    }

    @Test
    void testDeleteBlogById() {

    }
}