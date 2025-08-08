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
        response.sendRedirect("/api/auth/oauth2/authorization/google");
    }



    @GetMapping("/failure")
    public ResponseEntity<Map<String, String>> oauth2Failure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "OAuth2 authentication failed"));
    }


    // Optional: Endpoint to get current user info
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        Users user = authRepo.findByUsername(principal.getUsername());

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", user.getUsername());
        userInfo.put("id", user.getId());

        // Add OAuth2-specific attributes if available
        if (principal instanceof OAuth2User) {
            Map<String, Object> attributes = ((OAuth2User) principal).getAttributes();
            userInfo.put("picture", attributes.get("picture"));
            userInfo.put("name", attributes.get("name"));
        }

        return ResponseEntity.ok(userInfo);
    }
}