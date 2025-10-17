# API 명세서 (API Specification)

**프로젝트명:** SimpleBlog 시스템
**작성일:** 2025-10-17
**버전:** v1.0
**작성자:** PM
**검토자:** 개발팀장
**승인자:** 프로젝트 관리자

---

## 1. 문서 개요

### 1.1 목적
본 문서는 SimpleBlog 시스템의 RESTful API 인터페이스를 정의합니다.

### 1.2 범위
- 게시글 관리 API (5개)
- 백엔드 서버 ↔ 프론트엔드 클라이언트 간 통신 규약

### 1.3 기본 정보
- **Base URL:** `http://localhost:8080`
- **API 버전:** v1
- **통신 프로토콜:** HTTP/HTTPS
- **데이터 형식:** JSON
- **인코딩:** UTF-8

---

## 2. API 목록

| No | API명 | HTTP Method | Endpoint | 설명 |
|----|------|-------------|----------|------|
| 1 | 게시글 목록 조회 | GET | /api/posts | 전체 게시글 목록 조회 (최신순) |
| 2 | 게시글 상세 조회 | GET | /api/posts/{id} | 특정 게시글 상세 정보 조회 |
| 3 | 게시글 작성 | POST | /api/posts | 새 게시글 작성 |
| 4 | 게시글 수정 | PUT | /api/posts/{id} | 기존 게시글 수정 |
| 5 | 게시글 삭제 | DELETE | /api/posts/{id} | 기존 게시글 삭제 |

---

## 3. API 상세 명세

### 3.1 게시글 목록 조회

**기본 정보**
- **URL:** `/api/posts`
- **Method:** `GET`
- **설명:** 전체 게시글 목록을 최신순으로 조회합니다.

**Request**
- **Parameters:** 없음
- **Headers:**
  ```
  Content-Type: application/json
  ```

**Response (성공 시)**
- **HTTP Status:** `200 OK`
- **Body:**
  ```json
  {
    "posts": [
      {
        "id": 2,
        "title": "두 번째 게시글",
        "content": "두 번째 내용입니다.",
        "author": "김철수",
        "createdAt": "2025-10-17T14:30:00",
        "updatedAt": "2025-10-17T14:30:00"
      },
      {
        "id": 1,
        "title": "첫 번째 게시글",
        "content": "첫 번째 내용입니다.",
        "author": "홍길동",
        "createdAt": "2025-10-17T10:00:00",
        "updatedAt": "2025-10-17T10:00:00"
      }
    ],
    "total": 2
  }
  ```

**Response (에러 시)**
- **HTTP Status:** `500 Internal Server Error`
- **Body:**
  ```json
  {
    "error": "INTERNAL_SERVER_ERROR",
    "message": "서버 오류가 발생했습니다."
  }
  ```

---

### 3.2 게시글 상세 조회

**기본 정보**
- **URL:** `/api/posts/{id}`
- **Method:** `GET`
- **설명:** 특정 ID의 게시글 상세 정보를 조회합니다.

**Request**
- **Path Parameters:**
  - `id` (Long, 필수): 게시글 ID
- **Headers:**
  ```
  Content-Type: application/json
  ```

**Request 예시**
```
GET /api/posts/1
```

**Response (성공 시)**
- **HTTP Status:** `200 OK`
- **Body:**
  ```json
  {
    "id": 1,
    "title": "첫 번째 게시글",
    "content": "첫 번째 내용입니다.",
    "author": "홍길동",
    "createdAt": "2025-10-17T10:00:00",
    "updatedAt": "2025-10-17T10:00:00"
  }
  ```

**Response (에러 시)**
- **HTTP Status:** `404 Not Found`
- **Body:**
  ```json
  {
    "error": "POST_NOT_FOUND",
    "message": "게시글을 찾을 수 없습니다."
  }
  ```

---

### 3.3 게시글 작성

**기본 정보**
- **URL:** `/api/posts`
- **Method:** `POST`
- **설명:** 새로운 게시글을 작성합니다.

**Request**
- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body:**
  ```json
  {
    "title": "새 게시글 제목",
    "content": "새 게시글 내용입니다.",
    "author": "이영희"
  }
  ```

**Request 필드 설명**
| 필드 | 타입 | 필수 | 길이 제약 | 설명 |
|------|------|------|----------|------|
| title | String | O | 1~200자 | 게시글 제목 |
| content | String | O | 1자 이상 | 게시글 내용 |
| author | String | O | 1~100자 | 작성자명 |

**Response (성공 시)**
- **HTTP Status:** `201 Created`
- **Body:**
  ```json
  {
    "id": 3,
    "title": "새 게시글 제목",
    "content": "새 게시글 내용입니다.",
    "author": "이영희",
    "createdAt": "2025-10-17T15:00:00",
    "updatedAt": "2025-10-17T15:00:00"
  }
  ```

**Response (에러 시)**
- **HTTP Status:** `400 Bad Request`
- **Body:**
  ```json
  {
    "error": "INVALID_INPUT",
    "message": "제목은 필수 항목입니다."
  }
  ```

**Validation 규칙**
- 제목: 빈 값 불가, 최대 200자
- 내용: 빈 값 불가
- 작성자: 빈 값 불가, 최대 100자

---

### 3.4 게시글 수정

**기본 정보**
- **URL:** `/api/posts/{id}`
- **Method:** `PUT`
- **설명:** 기존 게시글의 제목과 내용을 수정합니다.

**Request**
- **Path Parameters:**
  - `id` (Long, 필수): 게시글 ID
- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body:**
  ```json
  {
    "title": "수정된 제목",
    "content": "수정된 내용입니다."
  }
  ```

**Request 예시**
```
PUT /api/posts/1
```

**Request 필드 설명**
| 필드 | 타입 | 필수 | 길이 제약 | 설명 |
|------|------|------|----------|------|
| title | String | O | 1~200자 | 수정할 제목 |
| content | String | O | 1자 이상 | 수정할 내용 |

**Response (성공 시)**
- **HTTP Status:** `200 OK`
- **Body:**
  ```json
  {
    "id": 1,
    "title": "수정된 제목",
    "content": "수정된 내용입니다.",
    "author": "홍길동",
    "createdAt": "2025-10-17T10:00:00",
    "updatedAt": "2025-10-17T15:30:00"
  }
  ```

**Response (에러 시)**
- **HTTP Status:** `404 Not Found`
- **Body:**
  ```json
  {
    "error": "POST_NOT_FOUND",
    "message": "게시글을 찾을 수 없습니다."
  }
  ```

---

### 3.5 게시글 삭제

**기본 정보**
- **URL:** `/api/posts/{id}`
- **Method:** `DELETE`
- **설명:** 기존 게시글을 삭제합니다.

**Request**
- **Path Parameters:**
  - `id` (Long, 필수): 게시글 ID
- **Headers:**
  ```
  Content-Type: application/json
  ```

**Request 예시**
```
DELETE /api/posts/1
```

**Response (성공 시)**
- **HTTP Status:** `204 No Content`
- **Body:** 없음

**Response (에러 시)**
- **HTTP Status:** `404 Not Found`
- **Body:**
  ```json
  {
    "error": "POST_NOT_FOUND",
    "message": "게시글을 찾을 수 없습니다."
  }
  ```

---

## 4. HTTP 상태 코드

| 상태 코드 | 설명 | 사용 시점 |
|----------|------|----------|
| 200 OK | 요청 성공 | 조회, 수정 성공 |
| 201 Created | 생성 성공 | 게시글 작성 성공 |
| 204 No Content | 성공 (응답 본문 없음) | 삭제 성공 |
| 400 Bad Request | 잘못된 요청 | Validation 실패 |
| 404 Not Found | 리소스 없음 | 게시글 미존재 |
| 500 Internal Server Error | 서버 오류 | 예상치 못한 오류 |

---

## 5. 에러 코드

| 에러 코드 | HTTP Status | 설명 |
|----------|-------------|------|
| INVALID_INPUT | 400 | 입력 값 검증 실패 |
| POST_NOT_FOUND | 404 | 게시글을 찾을 수 없음 |
| INTERNAL_SERVER_ERROR | 500 | 서버 내부 오류 |

---

## 6. 공통 사항

### 6.1 날짜/시간 형식
- **형식:** ISO 8601 (예: `2025-10-17T14:30:00`)
- **타임존:** UTC+9 (KST, 한국 표준시)

### 6.2 페이징
- **현재 버전:** 페이징 미지원
- **향후 계획:** v2.0에서 페이징 추가 예정 (page, size 파라미터)

### 6.3 정렬
- **기본 정렬:** 작성일시 내림차순 (최신순)
- **정렬 컬럼:** `createdAt DESC`

### 6.4 보안
- **현재 버전:** 인증/인가 미적용
- **향후 계획:** v2.0에서 JWT 토큰 인증 추가 예정

---

## 7. 변경 이력

| 버전 | 날짜 | 변경 내용 | 작성자 |
|------|------|----------|--------|
| v1.0 | 2025-10-17 | 초안 작성 | PM |

---

## 8. 승인

| 역할 | 이름 | 서명 | 날짜 |
|------|------|------|------|
| 작성자 (PM) | | | 2025-10-17 |
| 검토자 (개발팀장) | | | |
| 승인자 (프로젝트 관리자) | | | |

---

**문서 끝**
