package com.github.NervousOrange.springboot.entity;

import java.util.List;

public class BlogListResult {
    private String status;
    private String msg;
    private Integer total;
    private Integer page;
    private Integer totalPage;
    private List<Blog> data;

    public BlogListResult() {
    }

    private BlogListResult(String status, String msg, Integer total, Integer page, Integer totalPage, List<Blog> data) {
        this.status = status;
        this.msg = msg;
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
        this.data = data;
    }

    public static BlogListResult successfulBlogListResult(String msg, Integer total, Integer page, Integer totalPage, List<Blog> data) {
        return new BlogListResult("ok", msg, total, page, totalPage, data);
    }

    public static BlogListResult failBlogResult(String msg) {
        return new BlogListResult("fail", msg, null, null, null, null);
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public List<Blog> getData() {
        return data;
    }
}
