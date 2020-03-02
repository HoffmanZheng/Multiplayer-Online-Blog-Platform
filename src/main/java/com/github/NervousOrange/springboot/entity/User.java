package com.github.NervousOrange.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.time.Instant;

@SuppressFBWarnings({"UWF_NULL_FIELD", "UWF_UNWRITTEN_FIELD"})
public class User {
    private Integer id;
    private String username;
    @JsonIgnore
    private String encryptedPassword;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;

    public User() {
    }

    public User (String username, String encryptedPassword) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.avatar = "";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public User (String username, String encryptedPassword, String avatar) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.avatar = avatar;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
