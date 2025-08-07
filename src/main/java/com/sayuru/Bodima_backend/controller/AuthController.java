package com.sayuru.Bodima_backend.controller;

import com.sayuru.Bodima_backend.models.Users;
import com.sayuru.Bodima_backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public RedirectView get_login_page() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/target-page");  // Redirects to "/target-page"
        return redirectView;
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        System.out.println(user.toString());
        return authService.verify(user);
    }

    @PostMapping("/register")
    public String register(@RequestBody Users user){
        System.out.println(user);
        authService.register(user);
        return user.toString();
    }


}
