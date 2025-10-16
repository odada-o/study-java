# 🔌 REST API 설계 가이드

> PM이 알아야 할 RESTful API 설계 원칙과 실전 가이드

---

## 🎯 학습 목표

- ✅ RESTful API의 기본 원칙 이해
- ✅ 프론트엔드-백엔드 간 API 계약 명세 작성
- ✅ 실전에서 사용 가능한 API 문서 작성

---

## 📚 REST API 기초

### REST란?

**REST** (Representational State Transfer)
- 웹의 장점을 최대한 활용하는 아키텍처 스타일
- 자원(Resource)을 URL로 표현
- HTTP 메서드로 행위(Action) 표현

### HTTP 메서드

| 메서드 | 의미 | 용도 | 예시 |
|--------|------|------|------|
| **GET** | 조회 (Read) | 리소스 조회 | `GET /posts` - 게시글 목록 |
| **POST** | 생성 (Create) | 새 리소스 생성 | `POST /posts` - 게시글 작성 |
| **PUT** | 전체 수정 | 리소스 전체 교체 | `PUT /posts/1` - 게시글 전체 수정 |
| **PATCH** | 부분 수정 | 리소스 일부 수정 | `PATCH /posts/1` - 제목만 수정 |
| **DELETE** | 삭제 (Delete) | 리소스 삭제 | `DELETE /posts/1` - 게시글 삭제 |

### HTTP 상태 코드

| 코드 | 의미 | 사용 상황 |
|------|------|----------|
| **200** OK | 성공 | 요청 성공 (GET, PUT, PATCH) |
| **201** Created | 생성됨 | 리소스 생성 성공 (POST) |
| **204** No Content | 내용 없음 | 삭제 성공 (DELETE) |
| **400** Bad Request | 잘못된 요청 | 유효성 검증 실패 |
| **401** Unauthorized | 인증 실패 | 로그인 필요 |
| **403** Forbidden | 권한 없음 | 접근 권한 없음 |
| **404** Not Found | 없음 | 리소스를 찾을 수 없음 |
| **409** Conflict | 충돌 | 중복된 데이터 (이메일 중복 등) |
| **500** Internal Server Error | 서버 에러 | 서버 내부 오류 |

---

## 🎨 RESTful URL 설계 원칙

### 1. 명사 사용 (동사 ❌)

❌ **나쁜 예시:**
```
GET  /getPosts
POST /createPost
GET  /deletePost?id=1
```

✅ **좋은 예시:**
```
GET    /posts         # 게시글 목록
POST   /posts         # 게시글 생성
DELETE /posts/1       # 게시글 삭제
```

### 2. 복수형 사용

❌ **나쁜 예시:**
```
GET /post/1
GET /user/1
```

✅ **좋은 예시:**
```
GET /posts/1
GET /users/1
```

### 3. 계층 구조 표현

✅ **리소스 관계 표현:**
```
GET /posts/1/comments         # 게시글 1의 댓글 목록
GET /posts/1/comments/5       # 게시글 1의 댓글 5 상세
POST /posts/1/comments        # 게시글 1에 댓글 작성
```

### 4. 소문자, 하이픈 사용

❌ **나쁜 예시:**
```
/userProfiles
/user_profiles
```

✅ **좋은 예시:**
```
/user-profiles
```

### 5. 필터/정렬/검색은 쿼리 파라미터

```
GET /posts?page=1&size=20&sort=createdAt,desc
GET /posts?search=spring&category=tech
GET /users?role=admin&active=true
```

---

## 📋 API 명세 템플릿

### 기본 템플릿

```markdown
### [API 이름]

**엔드포인트**: [HTTP 메서드] `/api/[경로]`

**설명**: [이 API가 하는 일]

**인증**: [필요 여부] - `Bearer {token}` / 불필요

**권한**: [필요한 역할] - ADMIN / USER / 없음

---

#### 요청 (Request)

**Path Parameters**:
- `{id}` (Integer, 필수): [설명]

**Query Parameters**:
- `page` (Integer, 선택, 기본값: 0): 페이지 번호
- `size` (Integer, 선택, 기본값: 20): 페이지 크기

**Request Body**:
```json
{
  "field1": "value1",  // 설명
  "field2": 123        // 설명
}
```

**Validation**:
- `field1`: 필수, 3-100자
- `field2`: 필수, 양수

---

#### 응답 (Response)

**성공 (200 OK)**:
```json
{
  "data": {
    ...
  },
  "message": "성공 메시지"
}
```

**실패 (400 Bad Request)**:
```json
{
  "error": "ERROR_CODE",
  "message": "에러 메시지",
  "details": [
    "field1: 3자 이상이어야 합니다"
  ]
}
```

---

#### 예시 (Example)

**Request**:
```bash
curl -X GET "https://api.example.com/api/posts?page=0" \
  -H "Authorization: Bearer eyJhbGc..."
```

**Response**:
```json
{
  "data": [...],
  "pagination": {...}
}
```
```

---

## 💼 실전 예시: 블로그 API

### 1. 게시글 목록 조회

```markdown
### 게시글 목록 조회

**엔드포인트**: `GET /api/posts`

**설명**: 게시글 목록을 페이징하여 조회합니다.

**인증**: 불필요

---

#### 요청 (Request)

**Query Parameters**:
- `page` (Integer, 선택, 기본값: 0): 페이지 번호 (0부터 시작)
- `size` (Integer, 선택, 기본값: 20): 페이지당 항목 수 (최대 100)
- `sort` (String, 선택, 기본값: "createdAt,desc"): 정렬 기준
  - 사용 가능: `createdAt`, `title`, `viewCount`
  - 방향: `asc` (오름차순), `desc` (내림차순)
- `search` (String, 선택): 검색 키워드 (제목, 내용에서 검색)
- `category` (String, 선택): 카테고리 필터

**예시 URL**:
```
GET /api/posts?page=0&size=20&sort=createdAt,desc
GET /api/posts?search=spring&category=tech
```

---

#### 응답 (Response)

**성공 (200 OK)**:
```json
{
  "content": [
    {
      "id": 1,
      "title": "Spring Boot 시작하기",
      "content": "Spring Boot는...",  // 100자로 자른 미리보기
      "author": {
        "id": 1,
        "name": "홍길동",
        "profileImage": "/uploads/profile.jpg"
      },
      "category": "tech",
      "viewCount": 42,
      "commentCount": 5,
      "createdAt": "2025-10-16T10:30:00",
      "updatedAt": "2025-10-16T14:20:00"
    }
  ],
  "pageable": {
    "currentPage": 0,
    "totalPages": 10,
    "totalElements": 195,
    "size": 20,
    "first": true,
    "last": false
  }
}
```

**실패 (400 Bad Request)** - 잘못된 정렬 기준:
```json
{
  "error": "INVALID_SORT_FIELD",
  "message": "유효하지 않은 정렬 필드입니다.",
  "details": ["사용 가능한 필드: createdAt, title, viewCount"]
}
```
```

---

### 2. 게시글 상세 조회

```markdown
### 게시글 상세 조회

**엔드포인트**: `GET /api/posts/{id}`

**설명**: 특정 게시글의 상세 정보를 조회합니다. 조회 시 조회수가 1 증가합니다.

**인증**: 불필요

---

#### 요청 (Request)

**Path Parameters**:
- `id` (Long, 필수): 게시글 ID

**예시 URL**:
```
GET /api/posts/1
```

---

#### 응답 (Response)

**성공 (200 OK)**:
```json
{
  "id": 1,
  "title": "Spring Boot 시작하기",
  "content": "# Spring Boot 소개\n\nSpring Boot는...",  // 전체 내용
  "author": {
    "id": 1,
    "name": "홍길동",
    "email": "hong@example.com",
    "profileImage": "/uploads/profile.jpg"
  },
  "category": "tech",
  "tags": ["spring", "java", "backend"],
  "images": [
    {
      "id": 1,
      "url": "/uploads/images/abc123.jpg",
      "alt": "Spring Boot 로고"
    }
  ],
  "viewCount": 43,  // 조회 후 증가
  "commentCount": 5,
  "createdAt": "2025-10-16T10:30:00",
  "updatedAt": "2025-10-16T14:20:00"
}
```

**실패 (404 Not Found)**:
```json
{
  "error": "POST_NOT_FOUND",
  "message": "게시글을 찾을 수 없습니다.",
  "details": ["ID: 999"]
}
```
```

---

### 3. 게시글 작성

```markdown
### 게시글 작성

**엔드포인트**: `POST /api/posts`

**설명**: 새로운 게시글을 작성합니다.

**인증**: 필요 - `Bearer {token}`

**권한**: USER 이상

---

#### 요청 (Request)

**Headers**:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json
```

**Request Body**:
```json
{
  "title": "Spring Boot 시작하기",
  "content": "# Spring Boot 소개\n\nSpring Boot는...",
  "category": "tech",
  "tags": ["spring", "java", "backend"],
  "status": "PUBLISHED"  // DRAFT(임시저장) 또는 PUBLISHED(발행)
}
```

**Validation**:
- `title`: 필수, 3-100자
- `content`: 필수, 10자 이상
- `category`: 필수, 허용 값: tech, life, review
- `tags`: 선택, 최대 5개
- `status`: 필수, DRAFT 또는 PUBLISHED

---

#### 응답 (Response)

**성공 (201 Created)**:
```json
{
  "id": 1,
  "title": "Spring Boot 시작하기",
  "content": "# Spring Boot 소개\n\nSpring Boot는...",
  "author": {
    "id": 1,
    "name": "홍길동"
  },
  "category": "tech",
  "tags": ["spring", "java", "backend"],
  "status": "PUBLISHED",
  "viewCount": 0,
  "createdAt": "2025-10-16T10:30:00",
  "updatedAt": "2025-10-16T10:30:00"
}
```

**실패 (400 Bad Request)** - 유효성 검증 실패:
```json
{
  "error": "VALIDATION_FAILED",
  "message": "입력값이 유효하지 않습니다.",
  "details": [
    "title: 제목은 3자 이상이어야 합니다",
    "content: 내용은 10자 이상이어야 합니다"
  ]
}
```

**실패 (401 Unauthorized)** - 인증 실패:
```json
{
  "error": "UNAUTHORIZED",
  "message": "인증이 필요합니다.",
  "details": ["로그인 후 이용해주세요"]
}
```
```

---

### 4. 게시글 수정

```markdown
### 게시글 수정

**엔드포인트**: `PUT /api/posts/{id}`

**설명**: 기존 게시글을 수정합니다. 작성자만 수정 가능합니다.

**인증**: 필요 - `Bearer {token}`

**권한**: 작성자 본인 또는 ADMIN

---

#### 요청 (Request)

**Path Parameters**:
- `id` (Long, 필수): 수정할 게시글 ID

**Request Body**:
```json
{
  "title": "Spring Boot 시작하기 (수정)",
  "content": "수정된 내용...",
  "category": "tech",
  "tags": ["spring", "java"],
  "status": "PUBLISHED"
}
```

---

#### 응답 (Response)

**성공 (200 OK)**:
```json
{
  "id": 1,
  "title": "Spring Boot 시작하기 (수정)",
  "content": "수정된 내용...",
  ...
  "updatedAt": "2025-10-16T15:00:00"  // 업데이트 시간 변경
}
```

**실패 (403 Forbidden)** - 권한 없음:
```json
{
  "error": "ACCESS_DENIED",
  "message": "게시글을 수정할 권한이 없습니다.",
  "details": ["작성자만 수정할 수 있습니다"]
}
```

**실패 (404 Not Found)**:
```json
{
  "error": "POST_NOT_FOUND",
  "message": "게시글을 찾을 수 없습니다.",
  "details": ["ID: 999"]
}
```
```

---

### 5. 게시글 삭제

```markdown
### 게시글 삭제

**엔드포인트**: `DELETE /api/posts/{id}`

**설명**: 게시글을 삭제합니다 (Soft Delete). 작성자만 삭제 가능합니다.

**인증**: 필요 - `Bearer {token}`

**권한**: 작성자 본인 또는 ADMIN

---

#### 요청 (Request)

**Path Parameters**:
- `id` (Long, 필수): 삭제할 게시글 ID

---

#### 응답 (Response)

**성공 (204 No Content)**:
- 응답 바디 없음

**실패 (403 Forbidden)**:
```json
{
  "error": "ACCESS_DENIED",
  "message": "게시글을 삭제할 권한이 없습니다."
}
```

**실패 (404 Not Found)**:
```json
{
  "error": "POST_NOT_FOUND",
  "message": "게시글을 찾을 수 없습니다."
}
```
```

---

## 🎨 전체 API 목록 정리

### 인증 API

| 메서드 | 엔드포인트 | 설명 | 인증 |
|--------|-----------|------|------|
| POST | `/api/auth/register` | 회원가입 | ❌ |
| POST | `/api/auth/login` | 로그인 | ❌ |
| POST | `/api/auth/logout` | 로그아웃 | ✅ |
| GET | `/api/auth/me` | 내 정보 조회 | ✅ |

### 게시글 API

| 메서드 | 엔드포인트 | 설명 | 인증 |
|--------|-----------|------|------|
| GET | `/api/posts` | 게시글 목록 | ❌ |
| GET | `/api/posts/{id}` | 게시글 상세 | ❌ |
| POST | `/api/posts` | 게시글 작성 | ✅ |
| PUT | `/api/posts/{id}` | 게시글 수정 | ✅ |
| DELETE | `/api/posts/{id}` | 게시글 삭제 | ✅ |

### 댓글 API

| 메서드 | 엔드포인트 | 설명 | 인증 |
|--------|-----------|------|------|
| GET | `/api/posts/{postId}/comments` | 댓글 목록 | ❌ |
| POST | `/api/posts/{postId}/comments` | 댓글 작성 | ✅ |
| PUT | `/api/comments/{id}` | 댓글 수정 | ✅ |
| DELETE | `/api/comments/{id}` | 댓글 삭제 | ✅ |

### 파일 업로드 API

| 메서드 | 엔드포인트 | 설명 | 인증 |
|--------|-----------|------|------|
| POST | `/api/upload/image` | 이미지 업로드 | ✅ |

---

## 🚀 AI에게 API 구현 요청하기

```markdown
"다음 API 명세를 바탕으로 Spring Boot Controller를 구현해줘.

[API 명세 붙여넣기]

요구사항:
1. Controller, Service, Repository 계층 분리
2. DTO와 Entity 분리
3. 유효성 검증 (@Valid)
4. 적절한 HTTP 상태 코드 반환
5. 예외 처리 (GlobalExceptionHandler)

각 클래스의 역할을 주석으로 설명해줘."
```

---

## 📚 다음 단계

1. **데이터베이스 설계**: `데이터베이스-스키마-설계.md`에서 DB 스키마 설계
2. **API 구현**: AI에게 API 명세를 전달하고 구현 요청
3. **테스트**: Postman으로 API 테스트

---

**💡 핵심**: 명확한 API 명세는 프론트엔드-백엔드 협업의 핵심입니다!

