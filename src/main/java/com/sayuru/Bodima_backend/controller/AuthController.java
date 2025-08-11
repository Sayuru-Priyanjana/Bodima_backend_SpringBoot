package com.sayuru.Bodima_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayuru.Bodima_backend.models.Users;
import com.sayuru.Bodima_backend.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;


    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/login")
    public RedirectView get_login_page() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/target-page");  // Redirects to "/target-page"
        return redirectView;
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Users user) {
        System.out.println(user.toString());
        String token = authService.verify(user);

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String,Object> userData = new HashMap<>();
        userData.put("id", authService.getUser(user.getUsername()).getId());
        userData.put("username",user.getUsername());
        userData.put("isUser", authService.getUser(user.getUsername()).isUser());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userData);
        return ResponseEntity.ok(response);
    }

    // AuthController.java
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Users user) {
        System.out.println(user);
        Users registeredUser = authService.register(user);

        if (registeredUser != null) {
            // Generate token for the newly registered user
            String token = authService.generateTokenForUser(user.getUsername());

            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            Map<String,Object> userData = new HashMap<>();
            userData.put("id", registeredUser.getId());
            userData.put("username", registeredUser.getUsername());
            userData.put("isUser", registeredUser.isUser());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", userData);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
