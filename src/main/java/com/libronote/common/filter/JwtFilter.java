package com.libronote.common.filter;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.jwt.AccessTokenProvider;
import com.libronote.domain.User;
import com.libronote.mapper.UserMapper;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AccessTokenProvider accessTokenProvider;
    private final UserMapper userMapper;;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
        }

        if(token != null && accessTokenProvider.validateToken(token)){
            Claims claims = accessTokenProvider.parseToken(token);

            String authorities = claims.get("authorities", String.class);

            List<SimpleGrantedAuthority> auth = Arrays.stream(authorities.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            User user = userMapper.findByEmail(claims.getSubject());

            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
