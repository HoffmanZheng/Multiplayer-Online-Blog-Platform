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

    public User (String username, String encryptedPassword) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
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
}
