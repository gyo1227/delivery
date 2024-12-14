package kr.co.delivery.api.common.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] READ_ONLY_PUBLIC_ENDPOINTS = {"/favicon.ico"};
    private static final String[] ANONYMOUS_ENDPOINTS = {"/v1/auth/**"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.OPTIONS, "*").permitAll()
                                .requestMatchers(HttpMethod.GET, READ_ONLY_PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(ANONYMOUS_ENDPOINTS).anonymous()
                                .anyRequest().authenticated()
                )
                .build();
    }
}
