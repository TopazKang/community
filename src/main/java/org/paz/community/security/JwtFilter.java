package org.paz.community.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.paz.community.member.entity.UserEntity;
import org.paz.community.member.userDetails.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization.split(" ")[1];

        if(jwtUtil.isExpired(token)){
            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        Long id = jwtUtil.getId(token);

        System.out.println("id " + id);

        UserEntity userEntity = new UserEntity(id, "tempemail", "temppassword");


        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);

    }
}