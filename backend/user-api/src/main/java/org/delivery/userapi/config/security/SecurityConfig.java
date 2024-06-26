package org.delivery.userapi.config.security;

import lombok.RequiredArgsConstructor;
import org.delivery.userapi.domain.token.service.TokenService;
import org.delivery.userapi.filter.token.JwtExceptionFilter;
import org.delivery.userapi.filter.token.JwtFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenService tokenService;

    private final List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                // resource 에 대해서는 모든 요청 허용
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                // swagger 는 인증 없이 통과
                .mvcMatchers(SWAGGER.toArray(new String[0]))
                .permitAll()
                // open-api/** 하위 모든 주소는 인증 없이 통과
                .mvcMatchers("/open-api/**")
                .permitAll()
                // 그 외 모든 요청은 인증 사용
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new JwtFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtFilter.class);
        ;

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // hash 방식으로 암호화
        return new BCryptPasswordEncoder();
    }
}
