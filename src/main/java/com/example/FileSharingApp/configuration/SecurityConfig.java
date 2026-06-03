package com.example.FileSharingApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/files",
                            "/files/",
                            "/files/download/{id}",
                            "/files/share/{id}",
                            "/style/**"
                    ).permitAll();

                    auth.anyRequest().authenticated();
                })

                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/files")
                        .successHandler(customSuccessHandler())
                )

                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    public AuthenticationSuccessHandler customSuccessHandler() {

        SimpleUrlAuthenticationSuccessHandler successHandler =
                new SimpleUrlAuthenticationSuccessHandler();

        successHandler.setDefaultTargetUrl("/files/home");

        return successHandler;
    }
}
