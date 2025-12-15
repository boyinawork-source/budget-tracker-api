package com.ram.taskflow.config;

import com.ram.taskflow.security.JwtAuthFilter;
import com.ram.taskflow.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain security(HttpSecurity http, JwtService jwt) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(a -> a
            .requestMatchers("/api/auth/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(new JwtAuthFilter(jwt), UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
