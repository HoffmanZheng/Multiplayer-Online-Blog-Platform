package com.github.NervousOrange.springboot.entity;

import java.time.Instant;

public class User {
    Integer id;
    String username;
    String avatar;
    Instant createdAt;
    Instant updatedAt;

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
        this.avatar = null;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }


    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
