package com.libronote.service;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.exception.*;
import com.libronote.controller.response.FileResponse;
import com.libronote.domain.File;
import com.libronote.domain.User;
import com.libronote.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    @Value("${file.directory}")
    private String saveDirectory;

    public FileResponse upload(MultipartFile file, CustomUserDetails customUserDetails) {
        try{
            User user = userService.findUserByEmail(customUserDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            String uuid = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();

            int lastDotIndex = originalFilename.lastIndexOf(".");
            if(lastDotIndex == -1){
                throw new InvalidFileNameException("확장자를 확인해주세요.");
            }

            String extension = originalFilename.substring(lastDotIndex + 1);

            if(!extension.equals("jpg") && !extension.equals("png") && !extension.equals("jpeg") && !extension.equals("gif")){
                throw new InvalidExtensionException("이미지 파일만 업로드 가능합니다.");
            }

            String fileName = originalFilename.substring(0, lastDotIndex);

            String saveFileName = fileName + "_" + uuid + "." + extension;

            Path directoryPath = Paths.get(saveDirectory);
            if(!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path saveFilePath = directoryPath.resolve(saveFileName);
            Files.copy(file.getInputStream(), saveFilePath);

            File fileEntity = File.builder()
                    .userSeq(user.getUserSeq())
                    .fileName(saveFileName)
                    .imageUrl(saveFilePath.toString())
                    .build();

            fileMapper.save(fileEntity);

            File result = fileMapper.findByFileSeq(fileEntity.getFileSeq());

            return FileResponse.builder()
                    .fileSeq(result.getFileSeq())
                    .userSeq(result.getUserSeq())
                    .imageUrl(result.getImageUrl())
                    .fileName(result.getFileName())
                    .createdAt(result.getCreatedAt())
                    .modifiedAt(result.getModifiedAt())
                    .build();

        } catch (IOException e) {
            throw new FileUploadFailException(e.getMessage());
        }
    }

    public Resource download(Long fileSeq) {

        File file = fileMapper.findByFileSeq(fileSeq);
        if(file == null) {
            throw new FileNotFoundException("이미지를 찾을 수 없습니다.");
        }

        try{
            String imageUrl = file.getImageUrl();
            Path path = Paths.get(imageUrl);

            Resource resource = new UrlResource(path.toUri());

            if(!resource.exists() || !resource.isReadable() || !resource.isFile()) {
                throw new FileDownloadFailException("파일 다운로드 예외 발생");
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new FileDownloadFailException(e.getMessage());
        }
    }
}
