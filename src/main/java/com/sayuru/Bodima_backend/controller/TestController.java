package com.sayuru.Bodima_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping("/")
    public String test(HttpServletRequest request){
        return "Hello from SpringBoot "+ request.getSession().getId();
    }

    @RequestMapping("/login")
    public String testLogin(HttpServletRequest request){
        return "Hello from SpringBoot you need to log";
    }
}
