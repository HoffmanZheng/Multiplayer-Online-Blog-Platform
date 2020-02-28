package com.github.NervousOrange.springboot.service;

import com.github.NervousOrange.springboot.dao.MysqlDao;
import com.github.NervousOrange.springboot.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private MysqlDao mockMysqlDao;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;
    @InjectMocks
    UserService userService;

    @Test
    void testsInsertNewUser() {
        Map<String, String> param = new HashMap<>();
        param.put("username", "MyUser");
        param.put("encryptedPassword", "myEncodedPassword");

        Mockito.when(mockBCryptPasswordEncoder.encode("password"))
                .thenReturn("myEncodedPassword");
        userService.insertNewUser("MyUser", "password");
        Mockito.verify(mockMysqlDao).insertNewUser(param);
    }

    @Test
    void testGetUserByUsername() {
        userService.getUserByUsername("MyUser");
        Mockito.verify(mockMysqlDao).getUserByUsername("MyUser");
    }

    @Test
    public void testLoadUserByUsernameWhenUserNotExist() {
        Mockito.when(userService.getUserByUsername("MyUser"))
                .thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("MyUser"));
    }

    @Test
    public void testLoadUserByUsernameWhenUserExist() {
        Mockito.when(mockMysqlDao.getUserByUsername("MyUser"))
                .thenReturn(new User("MyUser", "encodedPassword"));
        UserDetails userDetails = userService.loadUserByUsername("MyUser");
        Assertions.assertEquals("MyUser", userDetails.getUsername());
        Assertions.assertEquals("encodedPassword", userDetails.getPassword());
    }
}
