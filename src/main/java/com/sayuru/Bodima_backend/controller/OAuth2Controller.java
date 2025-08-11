package com.sayuru.Bodima_backend.controller;

import com.sayuru.Bodima_backend.models.Users;
import com.sayuru.Bodima_backend.repository.AuthRepo;
import com.sayuru.Bodima_backend.services.auth.JWTService;
import com.sayuru.Bodima_backend.services.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/oauth2")
public class OAuth2Controller {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthRepo authRepo;

    @GetMapping("/authorization/google")
    public void initiateGoogleOAuth2(HttpServletResponse response) throws IOException {
        // This will be handled by Spring Security's OAuth2 support
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/failure")
    public ResponseEntity<Map<String, String>> oauth2Failure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "OAuth2 authentication failed"));
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        Users user = authRepo.findByUsername(principal.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("isUser", user.isUser());

        // Add OAuth2-specific attributes if available
        if (principal instanceof OAuth2User) {
            Map<String, Object> attributes = ((OAuth2User) principal).getAttributes();
            response.put("picture", attributes.get("picture"));
            response.put("name", attributes.get("name"));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> oauth2Callback(@AuthenticationPrincipal UserPrincipal principal) {
        Users user = authRepo.findByUsername(principal.getUsername());

        String token = jwtService.generateToken(user.getUsername());

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("username", user.getUsername());
        userData.put("isUser", user.isUser());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userData);

        return ResponseEntity.ok(response);
    }

}