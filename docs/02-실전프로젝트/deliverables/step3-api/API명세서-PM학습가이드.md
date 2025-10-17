# API 명세서 - PM 학습 가이드

> 이 문서는 PM이 API 명세서를 이해하고 활용하기 위한 학습 자료입니다.
> 고객에게 제출하는 공식 문서는 **"API명세서.md"**를 사용하세요.

**작성일:** 2025-10-17

---

## 📌 왜 API 명세서를 작성하나요?

### "프론트엔드와 백엔드의 약속서!"

API 명세서는 **프론트엔드와 백엔드가 어떻게 데이터를 주고받을지** 정한 약속서입니다.

**실무 예시:**
```
PM: "프론트엔드와 백엔드, 어떻게 협업할까요?"
백엔드: "제가 /api/posts 주소로 게시글 목록 API 만들겠습니다."
프론트엔드: "좋아요! 그럼 JSON 형식으로 주세요."
PM: (API 명세서 작성)
    → GET /api/posts → JSON 형식으로 게시글 목록 반환
백엔드 + 프론트엔드: "명세서 보고 각자 개발하겠습니다!"
```

---

## 🎯 PM이 꼭 알아야 할 것

### 1. API 명세서의 역할

**프론트엔드 ↔ 백엔드 소통 다리:**
- API 명세 없으면: "이 API가 뭐 주는 거예요?" 매번 물어봄
- API 명세 있으면: 문서 보고 각자 개발

**동시 개발 가능:**
```
API 명세 확정 전:
→ 백엔드 완성 기다림 → 프론트엔드 시작 (순차 개발)
→ 개발 기간 4주

API 명세 확정 후:
→ 백엔드 + 프론트엔드 동시 개발 (병렬 개발)
→ 개발 기간 2주
```

**제안서 필수 자료:**
- 공공기관 제안서에 API 명세서 포함 필수
- "우리는 RESTful API 5개를 제공합니다" 증명

---

### 2. 실무 Tip

⭐ **RESTful API란?**
```
REST = Representational State Transfer
→ 자원(Resource)을 URI로 표현하고, HTTP Method로 행위를 정의

RESTful API 설계 원칙:
1. URL은 명사로 (동사 ❌)
   ❌ /getPost, /deletePost
   ✅ /posts, /posts/{id}

2. HTTP Method로 행위 표현
   GET: 조회
   POST: 생성
   PUT: 수정
   DELETE: 삭제

3. 복수형 사용
   ❌ /post
   ✅ /posts
```

**SimpleBlog RESTful API 예시:**
```
GET    /api/posts      → 게시글 목록 조회
POST   /api/posts      → 게시글 작성
GET    /api/posts/1    → 1번 게시글 조회
PUT    /api/posts/1    → 1번 게시글 수정
DELETE /api/posts/1    → 1번 게시글 삭제
```

⭐ **HTTP Status Code의 의미**
```
2xx: 성공
- 200 OK: 요청 성공
- 201 Created: 생성 성공
- 204 No Content: 성공 (응답 본문 없음)

4xx: 클라이언트 오류
- 400 Bad Request: 잘못된 요청
- 404 Not Found: 리소스 없음

5xx: 서버 오류
- 500 Internal Server Error: 서버 내부 오류
```

⭐ **JSON 형식**
```json
{
  "id": 1,
  "title": "게시글 제목",
  "author": "홍길동"
}
```
- JavaScript Object Notation
- 키-값 쌍으로 데이터 표현
- 웹에서 가장 많이 사용하는 데이터 형식

⭐ **Path Parameter vs Query Parameter**
```
Path Parameter (경로에 포함):
/api/posts/1
→ {id} = 1

Query Parameter (? 뒤에 추가):
/api/posts?page=1&size=10
→ page=1, size=10

언제 뭘 쓰나?
- Path Parameter: 리소스 식별 (특정 게시글)
- Query Parameter: 필터링, 정렬, 페이징
```

---

### 3. 제안서/PT에서 활용하는 법

**제안서 구성:**
```
제안서 목차:
1. 제안 요약
2. 사업 이해도
3. 기술 제안
   3.1 시스템 구성도
   3.2 요구사항 정의
   3.3 데이터베이스 설계
   3.4 API 명세서  ← API명세서.md 내용 여기 들어감!
        - API 목록
        - Request/Response 예시
        - HTTP Status Code
4. 프로젝트 수행 계획
5. 예산
```

**PT 발표 스크립트 예시:**
> "SimpleBlog 시스템은 **RESTful API 5개**를 제공합니다.
>
> **1. 게시글 목록 조회 (GET /api/posts)**
> - 최신순으로 정렬된 게시글 목록을 반환합니다.
> - 응답 시간: 50ms 이하 보장
>
> **2. 게시글 상세 조회 (GET /api/posts/{id})**
> - 특정 게시글의 상세 정보를 조회합니다.
>
> **3. 게시글 작성 (POST /api/posts)**
> - 제목, 내용, 작성자를 입력받아 새 게시글을 생성합니다.
> - Validation: 제목 최대 200자, 내용 필수
>
> **4. 게시글 수정 (PUT /api/posts/{id})**
> - 기존 게시글의 제목과 내용을 수정합니다.
>
> **5. 게시글 삭제 (DELETE /api/posts/{id})**
> - 기존 게시글을 삭제합니다.
>
> 모든 API는 **JSON 형식**으로 데이터를 주고받으며,
> 표준 **HTTP Status Code**를 사용하여
> 프론트엔드가 에러 처리를 명확하게 할 수 있습니다."

**공무원 질문 대응:**
```
Q: "API가 뭔가요?"
A: "Application Programming Interface의 약자로,
    프론트엔드와 백엔드가 데이터를 주고받는 통로입니다.
    마치 식당에서 메뉴판(API 명세서)을 보고
    주문(Request)하면 음식(Response)을 받는 것과 같습니다."

Q: "RESTful이 뭔가요?"
A: "REST는 웹 API 설계 원칙입니다.
    URL로 자원을 표현하고 (예: /posts),
    HTTP Method로 행위를 정의합니다 (GET=조회, POST=생성).
    RESTful API는 이 원칙을 따르는 API를 말합니다."

Q: "JSON이 뭔가요?"
A: "JavaScript Object Notation의 약자로,
    데이터를 키-값 쌍으로 표현하는 형식입니다.
    예: {\"title\": \"게시글\", \"author\": \"홍길동\"}
    사람이 읽기 쉽고 프로그램이 파싱하기도 쉬워서
    웹에서 가장 많이 사용됩니다."
```

---

## 🎤 공무원이 자주 하는 질문

### Q1: "프론트엔드와 백엔드를 따로 개발하나요?"

**A:** 네, API 명세서를 기준으로 분리 개발합니다.

```
전통적인 방식 (MVC):
→ 백엔드에서 HTML까지 생성
→ 백엔드와 프론트엔드가 강하게 결합
→ 모바일 앱 개발 시 또 다시 개발

현대적인 방식 (API 기반):
→ 백엔드는 JSON 데이터만 제공 (API)
→ 프론트엔드(웹, 앱)는 API 호출해서 화면 구성
→ 웹/앱 모두 같은 API 사용 가능
→ 백엔드 1번 개발로 여러 플랫폼 지원

SimpleBlog:
→ 백엔드: Spring Boot (API 5개)
→ 프론트엔드: React (향후 계획)
→ 모바일 앱: React Native (향후 계획)
→ 같은 API 재사용!
```

---

### Q2: "Validation은 왜 필요한가요?"

**A:** 잘못된 데이터가 DB에 저장되는 것을 막기 위해서입니다.

```
Validation 없으면:
→ 제목 빈 값으로 게시글 작성
→ DB에 빈 제목 저장
→ 화면에서 오류 발생
→ 사용자 경험 ↓

Validation 있으면:
→ 제목 빈 값으로 게시글 작성 시도
→ API가 400 Bad Request 반환
→ "제목은 필수 항목입니다" 에러 메시지
→ 사용자가 제목 입력
→ 정상 저장

SimpleBlog Validation:
- 제목: 1~200자, 필수
- 내용: 1자 이상, 필수
- 작성자: 1~100자, 필수
```

---

### Q3: "페이징은 왜 안 하나요?"

**A:** SimpleBlog는 학습용이라 간단히 하고, 실제 프로젝트에서는 필수입니다.

```
페이징 없으면:
→ 게시글 10,000개 → 한 번에 전체 조회
→ 응답 시간 10초
→ 메모리 부족
→ 서버 다운

페이징 있으면:
→ 게시글 10,000개 → 10개씩 조회
→ 응답 시간 0.05초
→ 메모리 효율적
→ 안정적인 서비스

실제 프로젝트 API:
GET /api/posts?page=1&size=10
→ 1페이지, 10개씩
→ 응답: {posts: [...], total: 10000, page: 1, totalPages: 1000}

SimpleBlog v2.0에서 추가 예정
```

---

### Q4: "인증은 어떻게 하나요?"

**A:** SimpleBlog v1.0은 인증 없고, v2.0에서 JWT 토큰 인증 추가 예정입니다.

```
인증 없으면:
→ 누구나 게시글 삭제 가능
→ 보안 위험 높음

인증 있으면:
→ 로그인 → JWT 토큰 발급
→ API 호출 시 토큰 포함
→ 서버가 토큰 검증
→ 본인 게시글만 삭제 가능

JWT (JSON Web Token):
Header.Payload.Signature
→ 사용자 정보를 암호화한 토큰
→ 로그인 없이도 사용자 식별 가능

실제 프로젝트:
POST /api/login → JWT 토큰 발급
GET /api/posts
Headers: Authorization: Bearer {token}
```

---

## 📊 API 명세서 작성 실전 팁

### 1. API 명세 작성 순서

```
1단계: API 목록 도출
요구사항: "게시글 CRUD"
→ API 5개:
   - GET /api/posts (목록)
   - GET /api/posts/{id} (상세)
   - POST /api/posts (작성)
   - PUT /api/posts/{id} (수정)
   - DELETE /api/posts/{id} (삭제)

2단계: Request 정의
각 API별로:
- URL, HTTP Method
- Path Parameter, Query Parameter
- Request Body (POST, PUT)

3단계: Response 정의
각 API별로:
- 성공 시 HTTP Status Code, Body
- 실패 시 HTTP Status Code, Error Message

4단계: Validation 규칙 정의
- 필수 항목
- 길이 제약
- 형식 제약 (이메일, 전화번호 등)

5단계: 예시 작성
- Request 예시
- Response 예시
- cURL 명령어 예시 (개발자 참고용)
```

---

### 2. URL 설계 원칙

```
✅ 좋은 URL 설계:
GET /api/posts          # 목록
GET /api/posts/1        # 1번 상세
POST /api/posts         # 작성
PUT /api/posts/1        # 1번 수정
DELETE /api/posts/1     # 1번 삭제

❌ 나쁜 URL 설계:
GET /api/getPost        # 동사 사용 ❌
GET /api/post           # 단수형 ❌
POST /api/createPost    # 동사 사용 ❌
GET /api/posts/delete/1 # HTTP Method 대신 URL에 동사 ❌
```

**RESTful URL 규칙:**
```
[ ] URL은 명사만 사용
[ ] 복수형 사용 (posts, users, comments)
[ ] 소문자 사용
[ ] 언더스코어(_) 대신 하이픈(-) 사용
[ ] 행위는 HTTP Method로 표현
[ ] 계층 구조는 /로 표현 (/api/posts/1/comments)
```

---

### 3. HTTP Method 선택 기준

```
GET: 조회 (Read)
- 데이터 변경 없음
- 캐싱 가능
- 여러 번 호출해도 같은 결과 (멱등성)
예: GET /api/posts

POST: 생성 (Create)
- 새 리소스 생성
- 여러 번 호출하면 여러 개 생성 (멱등성 ❌)
예: POST /api/posts

PUT: 수정 (Update)
- 기존 리소스 수정
- 여러 번 호출해도 같은 결과 (멱등성)
예: PUT /api/posts/1

DELETE: 삭제 (Delete)
- 기존 리소스 삭제
- 여러 번 호출해도 같은 결과 (멱등성)
예: DELETE /api/posts/1

PATCH: 부분 수정
- PUT과 유사하지만 일부 필드만 수정
예: PATCH /api/posts/1 (title만 수정)
```

---

### 4. Response 설계 원칙

```json
// ✅ 좋은 Response
{
  "id": 1,
  "title": "제목",
  "author": "홍길동",
  "createdAt": "2025-10-17T10:00:00"
}

// ❌ 나쁜 Response
{
  "result": "success",
  "data": {
    "postId": 1,
    "postTitle": "제목",
    "writerName": "홍길동",
    "createDate": "2025-10-17 10:00:00"
  }
}
```

**Response 설계 원칙:**
```
[ ] 필드명은 camelCase (title, createdAt)
[ ] 날짜/시간은 ISO 8601 형식 (2025-10-17T10:00:00)
[ ] 불필요한 Wrapping 제거 (result, data 등)
[ ] 일관된 필드명 사용 (id vs postId ❌)
```

---

### 5. Error Response 설계

```json
// ✅ 좋은 Error Response
{
  "error": "POST_NOT_FOUND",
  "message": "게시글을 찾을 수 없습니다.",
  "timestamp": "2025-10-17T10:00:00",
  "path": "/api/posts/999"
}

// ❌ 나쁜 Error Response
{
  "error": "error occurred"
}
```

**Error Response 포함 사항:**
```
[ ] error: 에러 코드 (대문자_언더스코어)
[ ] message: 사용자에게 보여줄 메시지
[ ] timestamp: 에러 발생 시간 (로그 추적용)
[ ] path: 에러 발생 API 경로 (디버깅용)
```

---

## 📁 문서 관리 Tip

### API 테스트 도구

```
개발 중 테스트:
- Postman (80%) ← 추천! GUI 기반, 편리함
- Insomnia (10%)
- cURL (10%) 명령줄 기반

자동화 테스트:
- JUnit + MockMvc (Spring Boot)
- Postman Collection Runner

성능 테스트:
- JMeter (동시 사용자 1,000명 테스트)
- k6 (JavaScript 기반, 클라우드 친화적)
```

### API 문서화 도구

```
공공기관 (60%):
- MS Word
- HWP
- API 명세서를 표로 작성

스타트업 (40%):
- Swagger (Springdoc OpenAPI) ← 추천!
  → 코드에서 자동으로 API 문서 생성
  → 화면에서 바로 테스트 가능
- Postman (API 문서 + 테스트)
- Notion API 페이지

SimpleBlog:
- Markdown (학습용, Git 버전 관리 가능)
- 실제 프로젝트에서는 Swagger 사용 권장
```

---

## ✅ API 명세서 작성 완료 체크리스트

```
[ ] 프로젝트 개요 작성 (Base URL, 데이터 형식)
[ ] API 목록 정의 (HTTP Method, Endpoint)
[ ] 각 API별 Request 정의 (Parameter, Body)
[ ] 각 API별 Response 정의 (성공/실패)
[ ] Validation 규칙 정의
[ ] HTTP Status Code 정의
[ ] Error Code 정의
[ ] Request/Response 예시 작성
[ ] 날짜/시간 형식 정의
[ ] 페이징/정렬 규칙 정의 (필요 시)
[ ] 보안 정책 정의 (인증/인가)
[ ] 프론트엔드 개발자 검토 완료
[ ] 백엔드 개발자 검토 완료
[ ] 승인란 서명 받음
[ ] Git 커밋 및 Tag 생성
```

---

## 🔧 API 테스트 방법 (Postman 예시)

PM도 간단한 API 테스트는 할 수 있어야 개발자와 소통이 편합니다!

### 1. Postman 설치
https://www.postman.com/downloads/

### 2. GET /api/posts 테스트
```
1. New Request 클릭
2. Method: GET 선택
3. URL: http://localhost:8080/api/posts
4. Send 클릭
5. Response 확인:
   Status: 200 OK
   Body: {posts: [...], total: 2}
```

### 3. POST /api/posts 테스트
```
1. Method: POST 선택
2. URL: http://localhost:8080/api/posts
3. Headers 탭:
   Key: Content-Type
   Value: application/json
4. Body 탭 → raw → JSON 선택:
   {
     "title": "테스트 제목",
     "content": "테스트 내용",
     "author": "홍길동"
   }
5. Send 클릭
6. Response 확인:
   Status: 201 Created
   Body: {id: 3, title: "테스트 제목", ...}
```

---

## 다음 단계

API 명세서 완료 후:
1. **기술 스택 선정** → Java, Spring Boot, MySQL 선택
2. **개발 환경 구축** → Spring Boot 프로젝트 생성
3. **백엔드 개발** → API 구현

---

**문서 끝**
