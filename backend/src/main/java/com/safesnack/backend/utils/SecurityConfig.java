package com.safesnack.backend.utils;

import com.safesnack.backend.service.CustomAuthenticationSuccessHandler;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                // Configure request authorization
//                .authorizeHttpRequests(auth -> auth
//                        // Permit static resources
//                        .requestMatchers("/js/**", "/css/**", "/img/**", "/webjars/**").permitAll()
//                        // Restrict access to specific roles/authorities
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        // All other requests require authentication
//                        .anyRequest().authenticated()
//                )
//
//                // Configure HTTP Basic authentication for specific endpoints
//                .httpBasic(Customizer.withDefaults())
//
//                // Configure form-based login
//                .formLogin(form -> form
//                        // Use default login page or customize if needed
//                        .loginPage("/login") // Optional: specify custom login page
//                        .permitAll()
//                        .successHandler(successHandler)
//                )
//
//                // configure logout
//                .logout(LogoutConfigurer::permitAll);
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF so Postman can interact with the application
                .csrf(AbstractHttpConfigurer::disable)
                // start changing endpoint authorization requirements
                .authorizeHttpRequests(auth -> auth
                        // permit all requests to the following four paths
                        // they contain static resources and are required
                        // to present the pages
                        .requestMatchers(
                                "/js/**", "/css/**", "/img/**", "/webjars/**")
                        .permitAll()
                        // admin page can only be accessed with ROLE_ADMIN
                        .requestMatchers("/admin")
                        .hasRole("ADMIN")
                        // all others requests require authentication
                        .anyRequest().authenticated())
                // use HttpBasic authentication for /update-user
                .httpBasic(Customizer.withDefaults())
         //Configure form-based login
                .formLogin(form -> form
                        // Use default login page or customize if needed
                        .loginPage("/login") // Optional: specify custom login page
                        .permitAll()
                        .successHandler(successHandler)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
        configuration.addAllowedOrigin("http://localhost:5173");  // Allow your frontend application
        configuration.addAllowedHeader("*");  // Allow all headers
        configuration.addAllowedMethod("*");  // Allow all HTTP methods

        // Register the CORS configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

