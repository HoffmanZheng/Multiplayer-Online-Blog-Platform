package com.github.NervousOrange.springboot.entity;

public class AuthResult {
    private String status;
    private String msg;
    private User data;
    private Boolean isLogin;
    private String avatar;

    public AuthResult(String status, String msg, User data, Boolean isLogin, String avatar) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.isLogin = isLogin;
        this.avatar = avatar;
    }

    public static AuthResult failedResult(String message) {
        return new AuthResult("fail", message, null, null, null);
    }


    public static AuthResult successfulResult(String message, User user) {
        return new AuthResult("ok", message, user, null, null);
    }

    public static AuthResult loggedAuthResult(User user) {
        return new AuthResult("ok", null, user, true, user.getAvatar());
    }

    public static AuthResult notLoggedAuthResult() {
        return new AuthResult("ok", null, null, false, null);
    }

    public static AuthResult logoutResult(String message) {
        return new AuthResult("ok", message, null, null, null);
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

    public Boolean getIsLogin() {
        return isLogin;
    }

    public String getAvatar() {
        return avatar;
    }
}
