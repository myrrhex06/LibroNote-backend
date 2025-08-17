package com.libronote.mapper;

import com.libronote.domain.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    /**
     * 파일 정보 저장 메서드
     *
     * @param file 파일 정보
     * @return int
     */
    int save(File file);

    /**
     * 파일 정보 조회 메서드
     *
     * @param fileSeq 파일 기본키
     * @return File
     */
    File findByFileSeq(Long fileSeq);

    /**
     * 파일 정보 삭제 메서드
     *
     * @param fileSeq 파일 기본키
     * @return int
     */
    int deleteByFileSeq(Long fileSeq);

    /**
     * 파일 정보 수정 메서드
     *
     * @param file 파일 정보
     * @return int
     */
    int update(File file);
}
