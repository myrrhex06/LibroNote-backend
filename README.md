# 📖 LibroNote-backend

LibroNote-Backend는 사용자의 책 기록을 관리하는 RESTful API 서버입니다.

- Spring Boot 3.4.7
- Mybatis 3.5.17
- MySQL 8.0

## 설치 및 실행 
1. 프로젝트 클론
```bash
git clone https://github.com/myrrhex06/LibroNote-backend.git
```

2. `application-example.properties` 파일을 `application.properties`로 네이밍을 변경한 후 환경에 맞게 설정
```properties
spring.application.name=libronote

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.datasource.url=YOUR_JDBC_URL

mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.libronote
mybatis.configuration.map-underscore-to-camel-case=true

server.port=9000

jwt.secret.key=YOUR_SECRET_KEY
jwt.refresh.expiredms=604800000
jwt.access.expiredms=86400000

file.directory=YOUR_DIRECTORY_PATH
```

3. 빌드 및 실행
```bash
mvn clean install
mvn spring-boot:run
```

## 구현 기능
**Auth**
- 회원가입
- 로그인
- 토큰 재발급

**사용자(User)**
- 사용자 목록 조회
- 사용자 상세 조회
- 사용자 정보 수정
- 사용자 정보 삭제

**책 기록(Book)**
- 책 기록 등록
- 책 기록 수정
- 책 기록 삭제
- 책 기록 목록 조회
- 책 기록 상세 조회

**책 표지 이미지(Image File)**
- 이미지 미리보기
- 이미지 업로드
- 업로드되어 있는 이미지 수정
- 이미지 삭제

**기타**
- RefreshToken 제거 스케줄러
- 공통 응답 객체 적용

## API 명세

**Auth API**
- `POST /api/v1/auth/register` : 회원가입 API
- `POST /api/v1/auth/login` : 로그인 API
- `POST /api/v1/auth/refresh` : 토큰 재발급 API

**User API**
- `GET /api/v1/users/` : 사용자 목록 조회 API
- `GET /api/v1/users/detail` : 사용자 상세 정보 조회 API
- `PATCH /api/v1/users` : 사용자 정보 수정 API
- `DELETE /api/v1/users` : 사용자 정보 삭제 API

**Image File API**
- `GET /api/v1/files` : 책 표지 이미지 미리보기 API
- `POST /api/v1/files` : 책 표지 이미지 업로드 API
- `PATCH /api/v1/files` : 책 표지 이미지 수정 API
- `DELETE /api/v1/files` : 책 표지 이미지 삭제 API

**Book API**
- `GET /api/v1/books` : 책 기록 목록 조회 API
- `GET /api/v1/books/detail` : 책 기록 상세 조회 API
- `POST /api/v1/books` : 책 기록 등록 API
- `PATCH /api/v1/books` : 책 기록 수정 API
- `DELETE /api/v1/books` : 책 기록 삭제 API

### 주요 API 사용 예시
**회원가입 API**

Request
```json
{
  "email": "test30@gmail.com",
  "password": "test1234!",
  "nickname": "test30"
}
```

Response
```json
{
  "status": 200,
  "success": true,
  "message": "success",
  "result": {
    "userSeq": 9,
    "email": "test30@gmail.com",
    "role": "ROLE_USER",
    "nickname": "test30",
    "provider": "LIBRONOTE",
    "createdAt": "2025-08-16T13:46:07",
    "modifiedAt": "2025-08-16T13:46:07"
  },
  "timestamp": "2025-08-16T22:46:07.23036"
}
```

**로그인 API**

Request
```json
{
  "email": "test30@gmail.com",
  "password": "test1234!"
}
```

Response
```json
{
  "status": 200,
  "success": true,
  "message": "success",
  "result": {
    "userSeq": 9,
    "nickname": "test30",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MzBAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTUzNTIwMTcsImV4cCI6MTc1NTQzODQxN30.73lB5hVyZSCVbYvKSFldB9Iw_3d_65Pxh_yQGfYCLNU",
    "refreshToken": "3b7095a2-a520-4e0b-9d39-d104346aabda"
  },
  "timestamp": "2025-08-16T22:46:57.678914"
}
```

**책 기록 등록 API**

Request
```json
{
  "title": "책제목",
  "content": "책줄거리",
  "isbn": "ISBN번호",
  "feeling": "느낀점"
}
```

Response
```json
{
  "status": 200,
  "success": true,
  "message": "success",
  "result": {
    "bookSeq": 3,
    "title": "책제목",
    "content": "책줄거리",
    "isbn": "ISBN번호",
    "feeling": "느낀점",
    "fileSeq": null,
    "userSeq": 2,
    "createdAt": "2025-08-16T13:44:02",
    "modifiedAt": "2025-08-16T13:44:02"
  },
  "timestamp": "2025-08-16T22:44:02.410706"
}
```

## ERD
```mermaid
erDiagram
    USER_INFO ||--o{ BOOK_INFO : writes
    USER_INFO ||--o{ REFRESH_TOKEN_INFO : owns
    USER_INFO ||--o{ FILE_INFO : uploads
    BOOK_INFO ||--o| FILE_INFO : has

    USER_INFO{
        BIGINT USER_SEQ
        VARCHAR(200) EMAIL
        VARCHAR(100) NICKNAME
        VARCHAR(200) PASSWORD
        CHAR(20) PROVIDER
        CHAR(10) ROLE
        DATETIME CREATED_AT
        DATETIME MODIFIED_AT 
    }
    REFRESH_TOKEN_INFO{
        BIGINT TOKEN_SEQ
        VARCHAR(700) TOKEN_VALUE
        CHAR(1) USE_YN
        BIGINT USER_SEQ
        DATETIME ISSUED_AT
        DATETIME EXPIRED_AT
    }
    FILE_INFO{
        BIGINT FILE_SEQ
        VARCHAR(500) FILE_NAME
        VARCHAR(800) IMAGE_URL
        BIGINT USER_SEQ
        DATETIME CREATED_AT
        DATETIME MODIFIED_AT 
    }
    BOOK_INFO{
        BIGINT BOOK_SEQ
        VARCHAR(500) CONTENT
        CHAR(15) ISBN
        VARCHAR(100) TITLE
        VARCHAR(1000) FEELING
        BIGINT FILE_SEQ
        BIGINT USER_SEQ
        DATETIME CREATED_AT
        DATETIME MODIFIED_AT 
    }
```

## 패키지 구조
```
src/main
├── java
│   └── com
│       └── libronote
│           ├── LibronoteApplication.java
│           ├── common
│           ├── controller
│           ├── domain
│           ├── dto
│           ├── mapper
│           ├── scheduler
│           └── service
└── resources
    ├── mapper
    └── application-example.properties
```

- `common`: 공통
- `controller`: RESTful API 엔드포인트 Controller
- `domain`: Entity
- `dto`: DTO
- `mapper`: Mybatis 매퍼 인터페이스
- `scheduler`: 스케줄러
- `service`: 핵심 비즈니스 로직
- `resources/mapper`: Mybatis 매퍼 xml
- `resources/application-example.properties`: 설정 예시를 제공하는 환경 파일