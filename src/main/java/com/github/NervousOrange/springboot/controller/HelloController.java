package com.github.NervousOrange.springboot.controller;

import com.github.NervousOrange.springboot.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class HelloController {

    OrderService orderService;

    @Inject
    public HelloController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/user")
    public Object user() {
        return orderService.getUserService().getUserById(1);
    }

}
