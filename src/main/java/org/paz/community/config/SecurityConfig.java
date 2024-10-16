package org.paz.community.config;

import lombok.RequiredArgsConstructor;
import org.paz.community.utils.JwtAuthenticationFilter;
import org.paz.community.utils.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(httpSecurityCustomizer -> httpSecurityCustomizer.disable())
                .csrf(csrfCustomizer -> csrfCustomizer.disable())
                .sessionManagement(sessionManagementCustomizer ->
                        sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorizeRequestsCustomizer ->
                        authorizeRequestsCustomizer
                                .requestMatchers("/users/login").permitAll()
                                .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll() // 스웨거 접근 권한 오픈
                                .requestMatchers("/users/","/users/nickname","/users/email").permitAll()
                                .requestMatchers("/images/profile/**","/images/post/**").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}


//https://gksdudrb922.tistory.com/217