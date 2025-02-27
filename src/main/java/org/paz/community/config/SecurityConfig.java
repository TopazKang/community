package org.paz.community.config;

import lombok.RequiredArgsConstructor;
import org.paz.community.member.repository.MemberJpaRepository;
import org.paz.community.security.JwtFilter;
import org.paz.community.security.JwtUtil;
import org.paz.community.security.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MemberJpaRepository memberJpaRepository) throws Exception {
        http
                .httpBasic((auth) -> auth.disable())

                .csrf((auth) -> auth.disable())

                .formLogin((auth) -> auth.disable())

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeRequests((auth) ->
                        auth
                                .requestMatchers("/users/login","/login").permitAll()
                                .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll() // 스웨거 접근 권한 오픈
                                .requestMatchers("/api/members/","/api/members/nickname","/api/members/email").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/posts/**", "/api/vote-posts/**").permitAll()
                                .requestMatchers("/images/profile/**","/images/post/**").permitAll()
                                .anyRequest().authenticated())

                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)

                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, memberJpaRepository), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Value("${cors.allowed.origin}")
    private String corsAllowedOrigin;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(corsAllowedOrigin);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}