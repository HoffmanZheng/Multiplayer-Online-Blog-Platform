package com.github.NervousOrange.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.NervousOrange.springboot.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)  // Spring微型容器，用计算机的方式模拟登录过程
class AuthControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService mockUserService;
    @Mock
    private AuthenticationManager mockAuthenticationManager;
    @InjectMocks
    AuthController authController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(mockUserService, mockAuthenticationManager))
                .build();
    }

    private Map<String, String> createUsernameAndPasswordMap(String username, String password) {
        Map<String, String> usernameAndPassword = new HashMap<>();
        usernameAndPassword.put("username", username);
        usernameAndPassword.put("password", password);
        return usernameAndPassword;
    }

    private MvcResult postVisitWithUsernamePasswordPathAndExpectedResult(String username, String password, String path, String expectedResult) throws Exception {
        Map<String, String> usernameAndPassword = createUsernameAndPasswordMap(username, password);
        MvcResult response = getMvcResultFromPostVisitWithUsernamePasswordAndPath(usernameAndPassword, path);
        String resultString = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(resultString);
        Assertions.assertTrue(resultString.contains(expectedResult));
        return response;
    }

    private MvcResult getMvcResultFromPostVisitWithUsernamePasswordAndPath(Map<String, String> usernameAndPassword, String path) throws Exception {
        return mockMvc.perform(post("/auth/" + path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(usernameAndPassword)))
                .andExpect(status().isOk())
                .andReturn();
    }

    private void getVisitWithPathAndExpectedResult(String path, String expectedResult) throws Exception {
        MvcResult response = getVisitWithPath(path);
        String resultString = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(resultString);
        Assertions.assertTrue(resultString.contains(expectedResult));
    }

    private MvcResult getVisitWithPath(String path) throws Exception {
        return mockMvc.perform(get("/auth/" + path)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void testFailedAuth() throws Exception {
        getVisitWithPathAndExpectedResult("", "\"status\":\"ok\"");
        getVisitWithPathAndExpectedResult("", "\"isLogin\":false");
    }

    @Test
    void testLoginWhenUserNotExist() throws Exception {
        Mockito.when(mockUserService.loadUserByUsername("MyUser"))
                .thenThrow(new UsernameNotFoundException("User does not exist!"));

        postVisitWithUsernamePasswordPathAndExpectedResult("MyUser", "wrongPassword", "login", "用户不存在");
    }

    @Test
    void testLoginWrongPassword() throws Exception {
        // 未登录时，/auth 接口返回未登录状态
        testFailedAuth();

        User user;
        Mockito.when(mockAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user = new User("MyUser", "encodedPassword", Collections.emptyList()),
                        "wrongPassword", user.getAuthorities()
                ))).thenThrow(new BadCredentialsException("Wrong password!"));
        Mockito.when(mockUserService.loadUserByUsername("MyUser"))
                .thenReturn(new User("MyUser", "encodedPassword", Collections.emptyList()));

        postVisitWithUsernamePasswordPathAndExpectedResult("MyUser", "wrongPassword", "login", "密码不正确");

        // 登录失败，/auth 接口继续返回未登录状态
        testFailedAuth();
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        // 未登录时，/auth 接口返回未登录状态
        testFailedAuth();

        Mockito.when(mockUserService.loadUserByUsername("MyUser"))
                .thenReturn(new User("MyUser", "encodedPassword", Collections.emptyList()));

        MvcResult response = postVisitWithUsernamePasswordPathAndExpectedResult("MyUser", "encodedPassword", "login", "登录成功");

        // 成功登录后，再次验证 /auth 接口
        testSuccessfulAuthAfterLogin(response);
    }

    private void testSuccessfulAuthAfterLogin(MvcResult response) throws Exception {
        Mockito.when(mockUserService.getUserByUsername("MyUser"))
                .thenReturn(new com.github.NervousOrange.springboot.entity
                        .User("MyUser", "encodedPassword"));
        HttpSession session = response.getRequest().getSession();
        mockMvc.perform(get("/auth").session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    System.out.println(result.getResponse().getContentAsString());
                    Assertions.assertTrue(result.getResponse()
                            .getContentAsString().contains("MyUser"));
                    Assertions.assertTrue(result.getResponse()
                            .getContentAsString().contains("\"isLogin\":true"));
                });
    }

    @Test
    void testRegisterInvalidUsername() throws Exception {
        postVisitWithUsernamePasswordPathAndExpectedResult("SuperLongUsername", "encodedPassword", "register", "invalid username");
    }

    @Test
    void testRegisterInvalidPassword() throws Exception {
        postVisitWithUsernamePasswordPathAndExpectedResult("MyUser", "superLongEncodedPassword", "register", "invalid password");
    }

    @Test
    void testRegisterUsernameAlreadyExist() throws Exception {
        Mockito.when(mockUserService.insertNewUser("MyUser", "encodedPassword"))
                .thenThrow(new DuplicateKeyException("duplicate Username"));

        postVisitWithUsernamePasswordPathAndExpectedResult("MyUser", "encodedPassword", "register", "username already exist");
    }

    @Test
    void testSuccessfulRegister() throws Exception {
        Mockito.when(mockUserService.insertNewUser("MyUser", "encodedPassword"))
                .thenReturn("MyUser");
        Mockito.when(mockUserService.loadUserByUsername("MyUser"))
                .thenReturn(new User("MyUser", "encodedPassword", Collections.emptyList()));

        postVisitWithUsernamePasswordPathAndExpectedResult("MyUser", "encodedPassword", "register", "注册成功");
    }

    @Test
    void testSuccessfulLogout() throws Exception {
        Mockito.when(mockUserService.getUserByUsername(null))
                .thenReturn(new com.github.NervousOrange.springboot.entity
                        .User("MyUser", "encodedPassword"));

        getVisitWithPathAndExpectedResult("logout", "注销成功");
    }

    @Test
    void testFailedLogout() throws Exception {
        Mockito.when(mockUserService.getUserByUsername(null))
                .thenReturn(null);

        getVisitWithPathAndExpectedResult("logout", "用户尚未登录");
    }
}
