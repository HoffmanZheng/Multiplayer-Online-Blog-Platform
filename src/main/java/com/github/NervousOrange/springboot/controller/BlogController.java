package com.github.NervousOrange.springboot.controller;

import com.github.NervousOrange.springboot.entity.BlogResult;
import com.github.NervousOrange.springboot.entity.User;
import com.github.NervousOrange.springboot.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@RestController
public class BlogController {
    private BlogService blogService;
    private AuthController authController;

    @Inject
    public BlogController(BlogService blogService, AuthController authController) {
        this.blogService = blogService;
        this.authController = authController;
    }

    @GetMapping("/blog")
    @ResponseBody
    public Object getBlog(@RequestParam(value = "page") Integer page,
              @RequestParam(value="userId", required = false) Integer userId,
              @RequestParam(value = "atIndex", required = false) Boolean atIndex) {
        return blogService.getBlogList(page, 10, userId, atIndex);
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public Object getBlogId(@PathVariable String blogId) {
        int intBlogId = Integer.parseInt(blogId);
        return blogService.getBlogById(intBlogId);
    }

    @PostMapping("/blog")
    @ResponseBody
    public Object postBlog(@RequestBody Map<String, String> blogContent) {
        User user;
        if ((user = authController.auth().getData()) == null) {
            return BlogResult.failBlogResult("登录后才能操作");
        }
        int userId = user.getId();
        String title = blogContent.get("title");
        String content = blogContent.get("title");
        String description = blogContent.get("description");
        return blogService.createBlog(title, content, description, userId);
    }

    @PatchMapping("/blog/{blogId}")
    @ResponseBody
    public Object patchBlogId(@PathVariable String blogId, @RequestBody Map<String, String> blogContent) {
        User user;
        if ((user = authController.auth().getData()) == null) {
            return BlogResult.failBlogResult("登录后才能操作");
        }
        int userId = user.getId();
        int intBlogId = Integer.parseInt(blogId);
        String title = blogContent.get("title");
        String content = blogContent.get("title");
        String description = blogContent.get("description");
        return blogService.patchBlogId(title, content, description, intBlogId, userId);
    }

    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public Object deleteBlogId(@PathVariable String blogId) {
        User user;
        if ((user = authController.auth().getData()) == null) {
            return BlogResult.failBlogResult("登录后才能操作");
        }
        int userId = user.getId();
        int intBlogId = Integer.parseInt(blogId);
        return blogService.deleteBlogById(intBlogId, userId);
    }
}
