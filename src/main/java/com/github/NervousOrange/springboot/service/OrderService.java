package com.github.NervousOrange.springboot.service;

import javax.inject.Inject;

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
