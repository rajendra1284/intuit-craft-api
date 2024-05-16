package com.intuit.craft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.intuit.craft.exception.CustomAccessDeniedHandler;

@Configuration
public class OAuth2SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
            jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter());
        })).exceptionHandling(customizer -> {
            CustomAccessDeniedHandler handler = new CustomAccessDeniedHandler();
            customizer.accessDeniedHandler(handler);
        });
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/health", "/v3/api-docs/**", "/swagger-ui/**");
    }

    @Bean
    public CustomJwtAuthenticationConverter customJwtAuthenticationConverter() {
        return new CustomJwtAuthenticationConverter();
    }

}
