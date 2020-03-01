package com.github.NervousOrange.springboot.entity;

public class BlogResult {
    private String status;
    private String msg;
    private Integer total;
    private Integer page;
    private Integer totalPage;
    private Blog data;

    public BlogResult() {
    }

    private BlogResult(String status, String msg, Integer total, Integer page, Integer totalPage, Blog data) {
        this.status = status;
        this.msg = msg;
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
        this.data = data;
    }

    public static BlogResult successfulBlogIdResult(String msg, Blog data) {
        return new BlogResult("ok", msg, null, null, null, data);
    }

    public static BlogResult failBlogResult(String msg) {
        return new BlogResult("fail", msg, null, null, null, null);
    }

    public static BlogResult successfulDeleteBlogResult() {
        return new BlogResult("ok", "删除成功", null, null, null, null);
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

    public Blog getData() {
        return data;
    }
}
