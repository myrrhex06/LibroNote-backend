package com.libronote.mapper;

import com.libronote.domain.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    int save(File file);

    File findByFileSeq(Long fileSeq);
}
