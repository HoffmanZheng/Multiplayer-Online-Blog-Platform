package com.github.NervousOrange.springboot.entity;

public class AuthResult {
    private String status;
    private String msg;
    private User data;
    private boolean isLogin;

    public AuthResult(String status, String msg, User data, boolean isLogin) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.isLogin = isLogin;
    }

    public static AuthResult failedResult(String message) {
        return new AuthResult("fail", message, null, false);
    }

    public static AuthResult successfulResult(String message, User user) {
        return new AuthResult("ok", message, user, false);
    }

    public static AuthResult loggedAuthResult(User user) {
        return new AuthResult("ok", "", user, true);
    }

    public static AuthResult notLoggedAuthResult() {
        return new AuthResult("ok", "", null, false);
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public User getData() {
        return data;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
