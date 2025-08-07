package com.sayuru.Bodima_backend.config;

import com.sayuru.Bodima_backend.services.JWTService;
import com.sayuru.Bodima_backend.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    @Autowired
    private JWTService jwtService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.debug("Processing request for: {}", request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            logger.debug("JWT Token found: {}", token);

            try {
                username = jwtService.extractUsername(token);
                logger.debug("Extracted username: {}", username);
            } catch (Exception e) {
                logger.error("Error extracting username from token", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            logger.debug("No JWT Token found in Authorization header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("Authenticating user: {}", username);

            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                logger.debug("User details loaded: {}", userDetails.getUsername());

                if (jwtService.validateToken(token, userDetails)) {
                    logger.debug("Token validated successfully");

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("Security context set with authenticated user");
                } else {
                    logger.debug("Token validation failed");
                }
            } catch (Exception e) {
                logger.error("Error during authentication", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}