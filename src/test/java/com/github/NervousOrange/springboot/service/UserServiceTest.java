package com.github.NervousOrange.springboot.service;

import com.github.NervousOrange.springboot.dao.UserDao;
import com.github.NervousOrange.springboot.entity.AvatarGenerator;
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
    private UserDao mockUserDao;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;
    @Mock
    AvatarGenerator mockAvatarGenerator;
    @InjectMocks
    UserService userService;

    @Test
    void testsInsertNewUser() {
        Map<String, String> param = new HashMap<>();
        param.put("username", "MyUser");
        param.put("encryptedPassword", "myEncodedPassword");
        param.put("avatar", "https://s2.ax1x.com/2020/03/02/3f1FDe.jpg");

        Mockito.when(mockAvatarGenerator.getRandomAvatar()).thenReturn("https://s2.ax1x.com/2020/03/02/3f1FDe.jpg");
        Mockito.when(mockBCryptPasswordEncoder.encode("password"))
                .thenReturn("myEncodedPassword");
        userService.insertNewUser("MyUser", "password");
        Mockito.verify(mockUserDao).insertNewUser(param);
    }

    @Test
    void testGetUserByUsername() {
        userService.getUserByUsername("MyUser");
        Mockito.verify(mockUserDao).getUserByUsername("MyUser");
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
        Mockito.when(mockUserDao.getUserByUsername("MyUser"))
                .thenReturn(new User("MyUser", "encodedPassword"));
        UserDetails userDetails = userService.loadUserByUsername("MyUser");
        Assertions.assertEquals("MyUser", userDetails.getUsername());
        Assertions.assertEquals("encodedPassword", userDetails.getPassword());
    }
}
