package com.libronote.common.custom;

import com.libronote.domain.User;
import com.libronote.mapper.UserMapper;
import com.libronote.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("loadUserByUsername : {}", username);

        User user = userService.findUserByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        log.debug("조회된 사용자 nickname : {}", user.getNickname());

        return new CustomUserDetails(user);
    }
}
