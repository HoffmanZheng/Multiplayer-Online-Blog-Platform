package com.github.NervousOrange.springboot.service;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class OrderService {

    UserService userService;

    @Inject
    public OrderService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }
}
