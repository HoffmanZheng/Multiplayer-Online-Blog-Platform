package com.github.NervousOrange.springboot.controller;

import com.github.NervousOrange.springboot.entity.User;
import com.github.NervousOrange.springboot.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@RestController
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userService.getUserByUsername(username);
        if (user == null) {
            return new LoginResult("ok", "false", null, "false");
        } else {
            return new LoginResult("ok", "true", user, "true");
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        UserDetails userDetails;
        try {
            userDetails = this.userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e){
            return new LoginResult("fail", "用户不存在", null, "false");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return new LoginResult("ok", "登录成功", userService.getUserByUsername(username), "true");
        } catch (BadCredentialsException e) {
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
