package com.github.NervousOrange.springboot.controller;

<<<<<<< HEAD
import com.github.NervousOrange.springboot.entity.AuthResult;
import com.github.NervousOrange.springboot.entity.User;
import com.github.NervousOrange.springboot.service.UserService;
import org.springframework.dao.DuplicateKeyException;
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
=======
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
>>>>>>> solveConflict1
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
<<<<<<< HEAD
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userService.getUserByUsername(username);
        if (user == null) {
            return AuthResult.notLoggedAuthResult();
        } else {
            return AuthResult.loggedAuthResult(user);
        }
    }

=======
        if (alreadyLogin()) {
            return new LoginResult("ok", "false", null, "false");
        } else {
            return new LoginResult("ok", "true", null, "true");
        }
    }

    private boolean alreadyLogin() {
        return false;
    }

>>>>>>> solveConflict1
    @PostMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        UserDetails userDetails;
        try {
<<<<<<< HEAD
            userDetails = this.userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e){
            return AuthResult.failedResult("用户不存在");
=======
            userDetails = this.userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e){
            return new LoginResult("fail", "用户不存在", null, "false");
>>>>>>> solveConflict1
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
<<<<<<< HEAD
            return AuthResult.successfulResult("登录成功", userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return AuthResult.failedResult("密码不正确");
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Object register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        if (username.length() < 1 || username.length() > 15) {
            return AuthResult.failedResult("invalid username");
        }
        if (password.length() < 6 || password.length() > 16) {
            return AuthResult.failedResult("invalid password");
        }
        try {
            User user = userService.insertNewUser(username, password);
            return AuthResult.successfulResult("注册成功", user);
        } catch (DuplicateKeyException e) {
            return AuthResult.failedResult("username already exits");
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.getUserByUsername(username);
        if (user == null) {
            return AuthResult.failedResult("用户尚未登录");
        } else {
            SecurityContextHolder.clearContext();
            return AuthResult.failedResult("注销成功");
        }
    }
=======
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

>>>>>>> solveConflict1
}
