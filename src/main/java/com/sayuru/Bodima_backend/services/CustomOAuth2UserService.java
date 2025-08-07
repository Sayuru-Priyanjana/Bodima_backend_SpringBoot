package com.sayuru.Bodima_backend.services;

import com.sayuru.Bodima_backend.models.Users;
import com.sayuru.Bodima_backend.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private AuthRepo authRepo;



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract user details from Google response
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Check if user exists in DB
        Users user = authRepo.findByUsername(email);

        if (user == null) {
            // Create new user with random password (users will authenticate via OAuth)
            user = new Users();
            user.setUsername(email);
            user.setPassword("1234");
            user.setUser(true);
            authRepo.save(user);
        }

        return new UserPrincipal(user,oAuth2User.getAttributes());
    }
}