package com.S2T.Share_2_Teach.config;

import com.S2T.Share_2_Teach.service.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Enables Spring Security configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServices userDetailsServices; // Service for loading user details

    @Autowired
    private JWTAuthentFilter jwtAuthentFilter; // JWT authentication filter

    // Configures the security filter chain for HTTP requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection
                .cors(Customizer.withDefaults()) // Enables CORS with default settings
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/search/**").permitAll() // Public access to search API
                        .requestMatchers("/auth/**", "/public/**", "/forgotPassword/**").permitAll() // Allows access to /auth/** and /public/** without authentication
                        .requestMatchers("/auth/downloadFile/**", "/auth/viewFile/**").authenticated()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN") // Requires ADMIN authority for /admin/** paths
                        .requestMatchers("/user/**").hasAnyAuthority("USER") // Requires USER authority for /user/** paths
                        .requestMatchers("/educator/**").hasAnyAuthority("EDUCATOR") // Requires EDUCATOR authority for /educator/** paths
                        .requestMatchers("/moderator/**").hasAnyAuthority("MODERATOR") // Requires MODERATOR authority for /moderator/** paths
                        .requestMatchers("/contributor/**").hasAnyAuthority("EDUCATOR", "MODERATOR", "ADMIN") // Requires certain roles for contributor/** paths
                        .requestMatchers("/adminuser/**").hasAnyAuthority("ADMIN", "USER", "MODERATOR", "EDUCATOR") // Allows multiple roles for /adminuser/** paths
                        .anyRequest().authenticated()) // Requires authentication for all other paths
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configures stateless session management
                .authenticationProvider(authenticationProvider()) // Sets the custom authentication provider
                .addFilterBefore(jwtAuthentFilter, UsernamePasswordAuthenticationFilter.class); // Adds JWT authentication filter before the standard username/password filter
        return httpSecurity.build(); // Builds and returns the configured SecurityFilterChain
    }

    // Configures a custom authentication provider using the user details service and password encoder
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServices); // Sets the user details service
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Sets the password encoder
        return daoAuthenticationProvider; // Returns the configured authentication provider
    }

    // Provides a password encoder for encoding passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Uses BCrypt for password encoding
    }

    // Provides an authentication manager for managing authentication requests
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Retrieves the authentication manager from the configuration
    }
}

