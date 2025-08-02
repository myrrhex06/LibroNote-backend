package com.libronote.common.filter;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.domain.User;
import com.libronote.mapper.UserMapper;
import com.libronote.service.AccessTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AccessTokenService accessTokenService;
    private final UserMapper userMapper;;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
        }

        if(token != null && accessTokenService.validateAccessToken(token)){

            Claims claims = accessTokenService.parseAccessToken(token);

            List<SimpleGrantedAuthority> auth = accessTokenService.getAuthorities(claims);

            User user = userMapper.findByEmail(claims.getSubject());

            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
