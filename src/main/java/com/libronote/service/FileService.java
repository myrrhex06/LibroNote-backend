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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    @Value("${file.directory}")
    private String saveDirectory;

    /**
     * 파일 기본키를 기준으로 파일 정보 조회 처리 메서드
     *
     * @param fileSeq 파일 기본키
     * @return Optional<File>
     */
    public Optional<File> findByFileSeq(Long fileSeq){
        return Optional.ofNullable(fileMapper.findByFileSeq(fileSeq));
    }

    /**
     * 파일 저장 처리 메서드
     *
     * @param file 파일 정보
     */
    public void saveFile(File file){
        fileMapper.save(file);
    }

    /**
     * 파일 업로드 처리 메서드
     *
     * @param file 업로드 파일
     * @param customUserDetails 인증된 사용자 객체
     * @return FileResponse
     */
    public FileResponse upload(MultipartFile file, CustomUserDetails customUserDetails) {
        try{
            User user = userService.findUserByEmail(customUserDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            String saveFileName = createSaveFileName(file);

            Path saveFilePath = getSaveFilePath(saveFileName);

            Files.copy(file.getInputStream(), saveFilePath);

            File fileEntity = File.builder()
                    .userSeq(user.getUserSeq())
                    .fileName(saveFileName)
                    .imageUrl(saveFilePath.toString())
                    .build();

            saveFile(fileEntity);

            File result = findByFileSeq(fileEntity.getFileSeq())
                    .orElseThrow(() -> new FileUploadFailException("파일 업로드 중 오류가 발생하였습니다."));

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

    /**
     * 업로드할 파일의 파일명에서 확장자 추출을 위한 . 인덱스 번호 조회 처리 메서드
     *
     * @param originalFilename 업로드할 파일의 파일명
     * @return int
     */
    public int getLastDotIndex(String originalFilename){

        int lastDotIndex = originalFilename.lastIndexOf(".");
        if(lastDotIndex == -1){
            throw new InvalidFileNameException("확장자를 확인해주세요.");
        }

        return lastDotIndex;
    }

    /**
     * 업로드 파일 검증 처리 메서드
     *
     * @param extension 확장자명
     */
    public void validateImageExtension(String extension) {
        String lowerCase = extension.toLowerCase();

        if(!lowerCase.equals("jpg") && !lowerCase.equals("png") && !lowerCase.equals("jpeg") && !lowerCase.equals("gif")){
            throw new InvalidExtensionException("이미지 파일만 업로드 가능합니다.");
        }
    }

    /**
     * 저장될 파일의 파일명 처리 생성
     *
     * @param file 업로드 파일
     * @return String
     */
    public String createSaveFileName(MultipartFile file){
        String uuid = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();

        int lastDotIndex = getLastDotIndex(originalFilename);

        String extension = originalFilename.substring(lastDotIndex + 1);

        validateImageExtension(extension);

        String fileName = originalFilename.substring(0, lastDotIndex);
        return fileName + "_" + uuid + "." + extension;
    }

    /**
     * 업로드 파일 저장 경로 생성 처리 메서드
     *
     * @param saveFileName 저장될 파일명
     * @return Path
     * @throws IOException IOException
     */
    public Path getSaveFilePath(String saveFileName) throws IOException {

        Path directoryPath = Paths.get(saveDirectory);
        if(!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        Path saveFilePath = directoryPath.resolve(saveFileName).normalize();

        if(!saveFilePath.startsWith(directoryPath)) {
            throw new InvalidDirectoryPathException("저장 경로가 올바르지 않습니다.");
        }

        return saveFilePath;
    }

    /**
     * 파일 다운로드 처리 메서드
     *
     * @param fileSeq 파일 기본키
     * @return Resource
     */
    public Resource download(Long fileSeq) {

        File file = findByFileSeq(fileSeq)
                .orElseThrow(() -> new FileNotFoundException("이미지를 찾을 수 없습니다."));

        try{
            String imageUrl = file.getImageUrl();
            Path path = Paths.get(imageUrl);

            Resource resource = new UrlResource(path.toUri());

            validateResource(resource);

            return resource;

        } catch (MalformedURLException e) {
            throw new FileDownloadFailException(e.getMessage());
        }
    }

    /**
     * Resource 검증 처리 메서드
     *
     * @param resource Resource
     */
    public void validateResource(Resource resource) {
        if(!resource.exists() || !resource.isReadable() || !resource.isFile()) {
            throw new FileDownloadFailException("파일 다운로드 예외 발생");
        }
    }
}
