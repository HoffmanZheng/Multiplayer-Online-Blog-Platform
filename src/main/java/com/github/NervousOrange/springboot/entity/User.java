package com.github.NervousOrange.springboot.entity;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.time.Instant;

@SuppressFBWarnings("UWF_NULL_FIELD")
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
