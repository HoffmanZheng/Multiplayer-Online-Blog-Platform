package com.github.NervousOrange.springboot.entity;

<<<<<<< HEAD
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
=======
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
>>>>>>> solveConflict1
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

<<<<<<< HEAD
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

=======
>>>>>>> solveConflict1
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
