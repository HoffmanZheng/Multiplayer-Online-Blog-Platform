package com.github.NervousOrange.springboot.controller;

import com.github.NervousOrange.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)  // 用计算机的方式模拟登录过程
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
    @Test
    void testFailedAuth() throws Exception {
        setup();
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName())
                .thenReturn("MyUser");

        mockMvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andExpect(new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                System.out.println(result.getResponse().getContentAsString());
            }
        });
    }

    @Test
    void login() {
    }

    @Test
    void register() {
    }

    @Test
    void logout() {
    }
}