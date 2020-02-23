package com.github.NervousOrange.springboot.controller;

import com.github.NervousOrange.springboot.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AuthController {
    private Map<String, String> usernameAndPassword = new ConcurrentHashMap<>();
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;

    public AuthController(UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        usernameAndPassword.put("laowang", "693922");
        usernameAndPassword.put("lili", "930615");
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        if (alreadyLogin()) {
            return new LoginResult("ok", "false", null, "false");
        } else {
            return new LoginResult("ok", "true", null, "true");
        }
    }

    private boolean alreadyLogin() {
        return false;
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        UserDetails userDetails;
        try {
            userDetails = this.userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e){
            return new LoginResult("fail", "用户不存在", null, "false");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return new LoginResult("ok", "登录成功", new User(1, username), "true");
        } catch (Exception e) {
            return new LoginResult("fail", "密码不正确", null, "false");
        }

    }

    public static class LoginResult {
        private String status;
        private String msg;
        private User data;
        private String isLogin;

        public LoginResult(String status, String msg, User data, String isLogin) {
            this.status = status;
            this.msg = msg;
            this.data = data;
            this.isLogin = isLogin;
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

        public String getIsLogin() {
            return isLogin;
        }
    }

}
