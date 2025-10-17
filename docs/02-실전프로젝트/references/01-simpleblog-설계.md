# SimpleBlog 설계 문서

작성일: 2025-10-17

## 1. 요구사항 분석

### 기능 요구사항
- F-001: 게시글 목록 조회
- F-002: 게시글 상세 조회
- F-003: 게시글 작성
- F-004: 게시글 수정
- F-005: 게시글 삭제

### 비기능 요구사항
- Java 21 + Spring Boot 3.2 (전자정부 프레임워크 호환)
- RESTful API 방식
- MySQL 8.0
- 3계층 아키텍처 (Controller-Service-Repository)
- Spring Data JPA 사용

---

## 2. 데이터베이스 설계

### ERD (Entity Relationship Diagram)

```
┌─────────────────────────┐
│       posts             │
├─────────────────────────┤
│ 🔑 id (PK)              │
│ 📝 title                │
│ 📄 content              │
│ 👤 author               │
│ 📅 created_at           │
│ 📅 updated_at           │
└─────────────────────────┘
```

### 테이블 명세

#### posts 테이블

| 컬럼명 | 데이터타입 | 제약조건 | 설명 |
|--------|-----------|---------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 게시글 ID (고유번호) |
| title | VARCHAR(200) | NOT NULL | 제목 |
| content | TEXT | NOT NULL | 내용 |
| author | VARCHAR(100) | NOT NULL | 작성자 |
| created_at | DATETIME | NOT NULL | 작성일시 |
| updated_at | DATETIME | NOT NULL | 수정일시 |

### DDL (Data Definition Language)

```sql
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

---

## 3. API 명세서

### Base URL
```
http://localhost:8080/api
```

### 엔드포인트 목록

| 기능 | Method | URL | 설명 |
|------|--------|-----|------|
| 목록 조회 | GET | /api/posts | 전체 게시글 목록 |
| 상세 조회 | GET | /api/posts/{id} | 특정 게시글 조회 |
| 작성 | POST | /api/posts | 새 게시글 작성 |
| 수정 | PUT | /api/posts/{id} | 게시글 수정 |
| 삭제 | DELETE | /api/posts/{id} | 게시글 삭제 |

---

### API 상세 명세

#### 1. 게시글 목록 조회

**Endpoint**
```
GET /api/posts
```

**Request**
```http
GET /api/posts HTTP/1.1
Host: localhost:8080
```

**Response** (200 OK)
```json
[
  {
    "id": 1,
    "title": "첫 번째 게시글",
    "content": "안녕하세요",
    "author": "홍길동",
    "createdAt": "2025-10-17T10:00:00",
    "updatedAt": "2025-10-17T10:00:00"
  }
]
```

---

#### 2. 게시글 상세 조회

**Endpoint**
```
GET /api/posts/{id}
```

**Path Parameters**
| 이름 | 타입 | 필수 | 설명 |
|------|------|-----|------|
| id | Long | O | 게시글 ID |

**Request**
```http
GET /api/posts/1 HTTP/1.1
Host: localhost:8080
```

**Response** (200 OK)
```json
{
  "id": 1,
  "title": "첫 번째 게시글",
  "content": "안녕하세요",
  "author": "홍길동",
  "createdAt": "2025-10-17T10:00:00",
  "updatedAt": "2025-10-17T10:00:00"
}
```

**Error Response** (404 Not Found)
```json
{
  "error": "Not Found",
  "message": "게시글을 찾을 수 없습니다: 1"
}
```

---

#### 3. 게시글 작성

**Endpoint**
```
POST /api/posts
```

**Request Body**
| 필드 | 타입 | 필수 | 제약조건 | 설명 |
|------|------|-----|---------|------|
| title | String | O | 최대 200자 | 제목 |
| content | String | O | - | 내용 |
| author | String | O | 최대 100자 | 작성자 |

**Request**
```http
POST /api/posts HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "title": "새 게시글",
  "content": "내용입니다",
  "author": "작성자"
}
```

**Response** (201 Created)
```json
{
  "id": 3,
  "title": "새 게시글",
  "content": "내용입니다",
  "author": "작성자",
  "createdAt": "2025-10-17T12:00:00",
  "updatedAt": "2025-10-17T12:00:00"
}
```

**Error Response** (400 Bad Request)
```json
{
  "error": "Validation Failed",
  "message": "제목은 필수입니다"
}
```

---

#### 4. 게시글 수정

**Endpoint**
```
PUT /api/posts/{id}
```

**Path Parameters**
| 이름 | 타입 | 필수 | 설명 |
|------|------|-----|------|
| id | Long | O | 게시글 ID |

**Request Body**
| 필드 | 타입 | 필수 | 제약조건 | 설명 |
|------|------|-----|---------|------|
| title | String | O | 최대 200자 | 제목 |
| content | String | O | - | 내용 |
| author | String | O | 최대 100자 | 작성자 |

**Request**
```http
PUT /api/posts/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "title": "수정된 제목",
  "content": "수정된 내용",
  "author": "홍길동"
}
```

**Response** (200 OK)
```json
{
  "id": 1,
  "title": "수정된 제목",
  "content": "수정된 내용",
  "author": "홍길동",
  "createdAt": "2025-10-17T10:00:00",
  "updatedAt": "2025-10-17T12:30:00"
}
```

**Error Response** (404 Not Found)
```json
{
  "error": "Not Found",
  "message": "게시글을 찾을 수 없습니다: 1"
}
```

---

#### 5. 게시글 삭제

**Endpoint**
```
DELETE /api/posts/{id}
```

**Path Parameters**
| 이름 | 타입 | 필수 | 설명 |
|------|------|-----|------|
| id | Long | O | 게시글 ID |

**Request**
```http
DELETE /api/posts/1 HTTP/1.1
Host: localhost:8080
```

**Response** (204 No Content)
```
(응답 본문 없음)
```

**Error Response** (404 Not Found)
```json
{
  "error": "Not Found",
  "message": "게시글을 찾을 수 없습니다: 1"
}
```

---

## 4. 아키텍처 설계

### 시스템 구조

```
[ 클라이언트 ]
      ↓
[ Controller ] ← API 요청 접수
      ↓
[ Service ]    ← 비즈니스 로직
      ↓
[ Repository ] ← 데이터 접근
      ↓
[ MySQL DB ]
```

### 패키지 구조

```
com.example.simpleblog
├── controller      # REST API 컨트롤러
├── service         # 비즈니스 로직
├── repository      # 데이터 접근 계층
├── domain          # 엔티티 (Post)
├── dto             # 데이터 전송 객체
└── config          # 설정 클래스
```

---

## 5. 기술 스택

### Backend
- Java 21 (LTS)
- Spring Boot 3.2.11
- Spring Data JPA
- Spring Security (기본 설정)
- Hibernate 6.x
- Gradle 8.5

### Database
- MySQL 8.0
- Docker (개발 환경)

### 개발 도구
- IntelliJ IDEA
- Postman (API 테스트)
- Docker Desktop

---

## 6. 보안 고려사항

1. **SQL Injection 방지**: JPA의 PreparedStatement 사용
2. **XSS 방지**: 입력값 검증
3. **CSRF 방지**: Spring Security 설정 (향후 적용)
4. **인증/인가**: 1차 개발에서는 제외, 2차에서 JWT 적용 예정

---

## 7. 향후 확장 계획

### Phase 2
- 회원 관리 (User 엔티티)
- JWT 인증/인가
- 댓글 기능

### Phase 3
- 파일 첨부
- 페이징 처리
- 검색 기능
- 카테고리 분류

---

## 부록: 용어 정리

- **ERD**: Entity Relationship Diagram (개체 관계도)
- **API**: Application Programming Interface
- **RESTful**: REST 아키텍처를 따르는 웹 서비스
- **JPA**: Java Persistence API (자바 영속성 API)
- **CRUD**: Create, Read, Update, Delete (생성, 조회, 수정, 삭제)
