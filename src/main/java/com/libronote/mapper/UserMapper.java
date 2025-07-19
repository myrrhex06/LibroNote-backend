package com.libronote.mapper;

import com.libronote.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 사용자 정보 조회 메서드
     *
     * @param userSeq 사용자 기본키
     * @return User
     */
    User getUserDetail(Long userSeq);

    /**
     * 사용자 정보 목록 조회 메서드
     *
     * @param page 페이지 번호
     * @param size 데이터 개수
     * @return List
     */
    List<User> getUserList(@Param("page") int page, @Param("size") int size);

    /**
     * 사용자 정보 조회 메서드
     *
     * @param email 이메일
     * @return User
     */
    User findByEmail(String email);

    /**
     * 사용자 정보 등록 메서드
     *
     * @param user 사용자 정보 객체
     * @return int
     */
    int insertUser(User user);

    /**
     * 사용자 정보 제거 메서드
     *
     * @param userSeq 사용자 기본키
     * @return int
     */
    int deleteUser(Long userSeq);

    /**
     * 이메일 존재 여부 확인 메서드
     *
     * @param email 이메일
     * @return boolean
     */
    boolean existsEmail(String email);

    /**
     * 닉네임 존재 여부 확인 메서드
     *
     * @param nickname 닉네임
     * @return boolean
     */
    boolean existsNickname(String nickname);
}
