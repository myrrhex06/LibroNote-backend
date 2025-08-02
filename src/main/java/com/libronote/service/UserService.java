package com.libronote.service;

import com.libronote.domain.User;
import com.libronote.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    /**
     * 이메일 존재 여부 확인 처리 메서드
     *
     * @param email 이메일
     * @return boolean
     */
    public boolean existsEmail(String email){
        return userMapper.existsEmail(email);
    }

    /**
     * 닉네임 존재 여부 확인 처리 메서드
     *
     * @param nickname 닉네임
     * @return boolean
     */
    public boolean existsNickname(String nickname){
        return userMapper.existsNickname(nickname);
    }

    /**
     * 사용자 저장 처리 메서드
     *
     * @param user 사용자 객체
     */
    public void saveUser(User user){
        userMapper.insertUser(user);
    }

    /**
     * 이메일을 기준으로 사용자 정보 조회 처리 메서드
     *
     * @param email 이메일
     * @return Optional<User>
     */
    public Optional<User> findUserByEmail(String email){
        return Optional.ofNullable(userMapper.findByEmail(email));
    }

    /**
     * 사용자 기본키를 기준으로 사용자 정보 조회 처리 메서드
     *
     * @param userSeq 사용자 기본키
     * @return Optional<User>
     */
    public Optional<User> findUserByUserSeq(Long userSeq){
        return Optional.ofNullable(userMapper.getUserDetail(userSeq));
    }
}
