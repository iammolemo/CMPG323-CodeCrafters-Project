package com.S2T.Share_2_Teach.config;

import com.S2T.Share_2_Teach.service.JWTUtils;
import com.S2T.Share_2_Teach.service.UserDetailsServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthentFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils; // Utility for JWT operations

    @Autowired
    private UserDetailsServices userDetailsServices; // Service for loading user details

    // Filters incoming requests to authenticate JWT tokens
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authentHeader = request.getHeader("Authorization"); // Retrieves the 'Authorization' header
        final String jwtToken; // JWT token extracted from the header
        final String userEmail; // Extracted email from JWT token

        // If no 'Authorization' header is present, proceed with the next filter in the chain
        if (authentHeader == null || authentHeader.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token part from the 'Authorization' header (assuming it starts with "Bearer ")
        jwtToken = authentHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwtToken); // Extracts the username from the JWT token

        // If the token is valid and there is no existing authentication in the security context
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsServices.loadUserByUsername(userEmail); // Load user details by email

            // Validate the JWT token
            if (jwtUtils.isTokenValid(jwtToken, userDetails)){
                // Create a new security context with the authenticated user details
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities() // Set user details and authorities
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Set additional details
                securityContext.setAuthentication(token); // Set the authentication in the security context
                SecurityContextHolder.setContext(securityContext); // Update the security context holder
            }
        }
        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
