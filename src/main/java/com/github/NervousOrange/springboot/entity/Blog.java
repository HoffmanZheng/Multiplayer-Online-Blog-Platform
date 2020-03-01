package com.github.NervousOrange.springboot.entity;

import java.time.Instant;

public class Blog {
    private int id;
    private String title;
    private String description;
    private String content;
    private User user;
    private Instant createdAt;
    private Instant updatedAt;

    public Blog(int id, String title, String description, String content, User user, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
