package com.libronote.mapper;

import com.libronote.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User getUserDetail(Long userSeq);

    List<User> getUserList(@Param("page") int page, @Param("size") int size);

    User findByEmail(String email);

    int insertUser(User user);

    int deleteUser(Long userSeq);

    boolean existsEmail(String email);
    
    boolean existsNickname(String nickname);
}
