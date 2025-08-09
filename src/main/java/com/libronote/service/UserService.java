package com.libronote.service;

import com.libronote.controller.request.UserUpdateRequest;
import com.libronote.controller.response.UserResponse;
import com.libronote.domain.User;
import com.libronote.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<UserResponse> list(Long page, Long size, String nickname){
        List<User> list = userMapper.getUserList(page, size, nickname);

        return list.stream().map(user -> {
            return UserResponse.builder()
                    .userSeq(user.getUserSeq())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .role(user.getRole())
                    .provider(user.getProvider())
                    .createdAt(user.getCreatedAt())
                    .modifiedAt(user.getModifiedAt())
                    .build();
        }).toList();
    }

    /**
     * 사용자 정보 수정 처리 메서드
     *
     * @param request 사용자 정보 수정 요청 객체
     */
    public void update(UserUpdateRequest request){

        User user = findUserByUserSeq(request.getUserSeq())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());

        try{
            userMapper.updateUser(user);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 사용자 정보 삭제 처리 메서드
     *
     * @param userSeq 사용자 기본키
     */
    public void delete(Long userSeq) {
        userMapper.deleteUser(userSeq);
    }

    /**
     * 사용자 상세 정보 조회 처리 메서드
     *
     * @param userSeq 사용자 기본키
     * @return UserResponse
     */
    public UserResponse detail(Long userSeq) {
        User user = findUserByUserSeq(userSeq)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return UserResponse.builder()
                .userSeq(user.getUserSeq())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .provider(user.getProvider())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
