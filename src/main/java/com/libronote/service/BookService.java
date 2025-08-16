package com.libronote.service;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.exception.BookNotFoundException;
import com.libronote.common.exception.OtherUserBookHandleException;
import com.libronote.controller.request.BookInsertRequest;
import com.libronote.controller.request.BookUpdateRequest;
import com.libronote.controller.response.BookListResponse;
import com.libronote.controller.response.BookResponse;
import com.libronote.domain.Book;
import com.libronote.domain.User;
import com.libronote.domain.dto.BookListDto;
import com.libronote.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookMapper bookMapper;
    private final UserService userService;

    /**
     * 책 기록 조회 메서드
     *
     * @param bookSeq 책 기록 기본키
     * @return Optional<Book>
     */
    public Optional<Book> findBookByBookSeq(Long bookSeq) {
        return Optional.ofNullable(bookMapper.findBookByBookSeq(bookSeq));
    }

    /**
     * 책 기록 저장 메서드
     *
     * @param book 책 기록 정보
     */
    public void insertBookInfo(Book book) {
        bookMapper.save(book);
    }

    /**
     * 책 기록 삭제 메서드
     *
     * @param bookSeq 책 기록 기본키
     */
    public void deleteBookInfo(Long bookSeq){
        bookMapper.delete(bookSeq);
    }

    /**
     * 책 기록 수정 메서드
     *
     * @param book 책 기록
     */
    public void updateBookInfo(Book book) {
        bookMapper.update(book);
    }

    /**
     * 책 기록 등록 메서드
     *
     * @param request 책 기록 등록 요청 객체
     * @param customUserDetails 인증된 사용자 객체
     * @return BookResponse
     */
    public BookResponse insert(BookInsertRequest request, CustomUserDetails customUserDetails) {
        User user = userService.findUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));

        Book book = Book.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .isbn(request.getIsbn())
                .feeling(request.getFeeling())
                .userSeq(user.getUserSeq())
                .fileSeq(request.getFileSeq())
                .build();

        insertBookInfo(book);

        Book result = findBookByBookSeq(book.getBookSeq())
                .orElseThrow(() -> new BookNotFoundException("책 기록 저장 도중 문제가 발생하였습니다."));

        return BookResponse.builder()
                .bookSeq(result.getBookSeq())
                .title(result.getTitle())
                .content(result.getContent())
                .isbn(result.getIsbn())
                .feeling(result.getFeeling())
                .fileSeq(result.getFileSeq())
                .userSeq(result.getUserSeq())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .build();
    }

    /**
     * 책 기록 목록 조회 메서드
     *
     * @param title    책 제목
     * @param nickname 사용자 닉네임
     * @param userSeq 사용자 기본키
     * @param page     페이지
     * @param size     페이지당 보여질 데이터 개수
     * @return List<BookListResponse>
     */
    public List<BookListResponse> list(String title, String nickname, Long userSeq, Long page, Long size) {
        List<BookListDto> books = bookMapper.findAllBooks(title, nickname, userSeq, page, size);

        return books.stream().map(book -> {
            return BookListResponse.builder()
                    .bookSeq(book.getBookSeq())
                    .title(book.getTitle())
                    .nickname(book.getNickname())
                    .isbn(book.getIsbn())
                    .fileSeq(book.getFileSeq())
                    .createdAt(book.getCreatedAt())
                    .modifiedAt(book.getModifiedAt())
                    .build();
        }).toList();
    }

    /**
     * 책 기록 상세 조회 메서드
     *
     * @param bookSeq 책 기록 기본키
     * @return BookResponse
     */
    public BookResponse detail(Long bookSeq) {
        Book book = findBookByBookSeq(bookSeq)
                .orElseThrow(() -> new BookNotFoundException("책 기록을 찾을 수 없습니다."));

        return BookResponse.builder()
                .bookSeq(book.getBookSeq())
                .title(book.getTitle())
                .content(book.getContent())
                .isbn(book.getIsbn())
                .feeling(book.getFeeling())
                .fileSeq(book.getFileSeq())
                .userSeq(book.getUserSeq())
                .createdAt(book.getCreatedAt())
                .modifiedAt(book.getModifiedAt())
                .build();
    }

    /**
     * 책 기록 수정 메서드
     *
     * @param request 책 기록 수정 요청 객체
     * @param customUserDetails 인증된 사용자 객체
     * @return BookResponse
     */
    public BookResponse update(BookUpdateRequest request, CustomUserDetails customUserDetails) {
        Book book = findBookByBookSeq(request.getBookSeq())
                .orElseThrow(() -> new BookNotFoundException("책 기록을 찾을 수 없습니다."));

        User user = userService.findUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        validateOwner(book, user);

        if(StringUtils.hasText(request.getTitle())){
            book.setTitle(request.getTitle());
        }
        if(StringUtils.hasText(request.getContent())){
            book.setContent(request.getContent());
        }
        if(StringUtils.hasText(request.getIsbn())){
            book.setIsbn(request.getIsbn());
        }
        if(StringUtils.hasText(request.getFeeling())){
            book.setFeeling(request.getFeeling());
        }
        if(request.getFileSeq() != null){
            book.setFileSeq(request.getFileSeq());
        }

        updateBookInfo(book);

        return BookResponse.builder()
                .bookSeq(book.getBookSeq())
                .title(book.getTitle())
                .content(book.getContent())
                .isbn(book.getIsbn())
                .feeling(book.getFeeling())
                .fileSeq(book.getFileSeq())
                .userSeq(book.getUserSeq())
                .createdAt(book.getCreatedAt())
                .modifiedAt(book.getModifiedAt())
                .build();
    }

    /**
     * 책 기록 제거 메서드
     *
     * @param bookSeq 책 기본키
     * @param customUserDetails 인증된 사용자 객체
     */
    public void delete(Long bookSeq, CustomUserDetails customUserDetails) {

        Book book = findBookByBookSeq(bookSeq)
                .orElseThrow(() -> new BookNotFoundException("책 기록을 찾을 수 없습니다."));

        User user = userService.findUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        validateOwner(book, user);

        deleteBookInfo(bookSeq);
    }

    /**
     * 책 기록 소유자 검증 메서드
     *
     * @param book 책 정보
     * @param user 사용자 정보
     */
    public void validateOwner(Book book, User user) {
        if(!book.getUserSeq().equals(user.getUserSeq())){
            throw new OtherUserBookHandleException("다른 사용자가 작성한 기록은 수정 또는 삭제할 수 없습니다.");
        }
    }
}
