package com.safesnack.backend.config;

import com.safesnack.backend.service.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF so Postman can interact with the application
                .csrf(AbstractHttpConfigurer::disable)
                // start changing endpoint authorization requirements
                .authorizeHttpRequests(auth -> auth
                        /* permit all requests to the following path they contain static resources and are required
                         to present the pages */
                        //v3 is required for generation of the OpenAPI documentation
                        .requestMatchers(
                                "/js/**", "/css/**", "/img/**", "/webjars/**", "/v3/**")
                        .permitAll()
                        // admin page can only be accessed with ROLE_ADMIN
                        .requestMatchers("/admin")
                        .hasRole("ADMIN")
                        .requestMatchers("/user/changePassword", "/user/resetPassword", "/user/savePassword", "/signup/**")
                        .permitAll()
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

