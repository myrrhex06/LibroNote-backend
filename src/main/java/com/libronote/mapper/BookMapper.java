package com.libronote.mapper;

import com.libronote.domain.Book;
import com.libronote.dto.BookListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    /**
     * 책 기록 저장 메서드
     *
     * @param book 책 기록 정보
     * @return int
     */
    int save(Book book);

    /**
     * 책 기록 조회 메서드
     *
     * @param bookSeq 책 기록 기본키
     * @return Book
     */
    Book findBookByBookSeq(Long bookSeq);

    /**
     * 책 기록 목록 조회 메서드
     *
     * @param title 책 제목
     * @param nickname 닉네임
     * @param userSeq 사용자 기본키
     * @param page 페이지 번호
     * @param size 페이지당 보여질 데이터 개수
     * @return List<BookListDto>
     */
    List<BookListDto> findAllBooks(String title, String nickname, Long userSeq, Long page, Long size);

    /**
     * 책 기록 수정 메서드
     *
     * @param book 책 기록 정보
     * @return int
     */
    int update(Book book);

    /**
     * 책 기록 삭제 메서드
     *
     * @param bookSeq 책 기록 기본키
     * @return int
     */
    int delete(Long bookSeq);
}
