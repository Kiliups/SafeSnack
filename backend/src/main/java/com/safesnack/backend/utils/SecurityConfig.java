package com.safesnack.backend.utils;

import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // Configure request authorization
                .authorizeHttpRequests(auth -> auth
                        // Permit static resources
                        .requestMatchers("/js/**", "/css/**", "/img/**", "/webjars/**").permitAll()
                        // Restrict access to specific roles/authorities
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/superu").hasRole("SUPERU")
                        .requestMatchers("/update-user").hasAuthority("UPDATER")
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )

                // Configure HTTP Basic authentication for specific endpoints
                .httpBasic(Customizer.withDefaults())

                // Configure form-based login
                .formLogin(form -> form
                        // Use default login page or customize if needed
                        .loginPage("/login") // Optional: specify custom login page
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

