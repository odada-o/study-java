# API 테스트 도구 가이드

> PM이 알아야 할 실무 API 테스트 방법

작성일: 2025-10-17

---

## 🎯 요약

**API 테스트 도구는 개발 단계와 목적에 따라 달라집니다:**

- **개발 중**: Postman, Insomnia (GUI 기반, 쉬움)
- **자동화 테스트**: JUnit + MockMvc, REST Assured
- **성능 테스트**: JMeter, k6
- **API 문서화**: Swagger UI, Postman Collections

---

## 📊 실무 API 테스트 도구 (목적별)

### 1. 개발 중 수동 테스트 (가장 많이 사용)

#### Postman (80%) - 추천! ⭐

```
✅ 무료 (기본 기능)
✅ GUI 기반 - 사용하기 쉬움
✅ Collection 관리 (테스트 케이스 저장)
✅ 환경 변수 지원 (개발/운영 환경 분리)
✅ 팀 공유 가능
✅ API 문서 자동 생성
✅ 테스트 스크립트 작성 가능 (JavaScript)
```

**주요 기능:**
- **Collections**: API 요청을 폴더별로 정리
- **Environments**: dev/staging/prod 환경 분리
- **Pre-request Script**: 요청 전 실행 (토큰 발급 등)
- **Tests**: 응답 검증 (상태 코드, JSON 값 확인)
- **Mock Server**: 백엔드 없이 프론트엔드 개발 가능

**사용 예시:**
```javascript
// Pre-request Script (요청 전 실행)
pm.environment.set("timestamp", Date.now());

// Tests (응답 검증)
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response has data", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.data).to.be.an('array');
});
```

**다운로드:**
- https://www.postman.com/downloads/

---

#### Insomnia (15%)

```
✅ 무료 (기본 기능)
✅ 깔끔한 UI
✅ GraphQL 지원 우수
✅ 빠른 속도
❌ Postman보다 기능 적음
❌ 팀 협업 기능 제한적 (무료 버전)
```

**언제 사용?**
- GraphQL API 테스트
- Postman보다 가벼운 도구 원할 때
- 개인 프로젝트

**다운로드:**
- https://insomnia.rest/download

---

#### cURL (5%) - 커맨드 라인

```
✅ 모든 시스템에 기본 설치
✅ 스크립트 작성 가능
✅ CI/CD 파이프라인에 통합 쉬움
❌ GUI 없음 (터미널만)
❌ 초보자에게 어려움
```

**사용 예시:**
```bash
# GET 요청
curl http://localhost:8080/api/posts

# POST 요청 (JSON)
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "테스트 게시글",
    "content": "테스트 내용입니다.",
    "author": "테스터"
  }'

# PUT 요청
curl -X PUT http://localhost:8080/api/posts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "수정된 제목",
    "content": "수정된 내용입니다."
  }'

# DELETE 요청
curl -X DELETE http://localhost:8080/api/posts/1

# 응답 상태 코드 확인
curl -w "\n%{http_code}\n" http://localhost:8080/api/posts
```

---

### 2. 자동화 테스트 (개발자용)

#### JUnit + MockMvc (Spring Boot 표준)

```
✅ Spring Boot 기본 내장
✅ Controller 단위 테스트
✅ 실제 HTTP 없이 테스트 (빠름)
✅ CI/CD 통합 쉬움
❌ Java 코드 작성 필요 (PM은 안 해도 됨)
```

**테스트 코드 예시:**
```java
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 게시글_목록_조회() throws Exception {
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void 게시글_생성() throws Exception {
        String json = """
            {
                "title": "테스트 게시글",
                "content": "테스트 내용",
                "author": "테스터"
            }
            """;

        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists());
    }
}
```

---

#### REST Assured (Java 기반)

```
✅ BDD 스타일 테스트 작성
✅ 읽기 쉬운 코드
✅ 실제 HTTP 요청 테스트
❌ Java 코드 작성 필요
```

**테스트 코드 예시:**
```java
@Test
void 게시글_목록_조회() {
    given()
        .when()
            .get("/api/posts")
        .then()
            .statusCode(200)
            .body("data", hasSize(greaterThan(0)));
}
```

---

### 3. API 문서화 + 테스트

#### Swagger UI / OpenAPI

```
✅ 코드에서 자동으로 문서 생성
✅ 웹 브라우저에서 바로 테스트 가능
✅ API 명세서 = 테스트 도구
✅ 팀원과 공유 쉬움
❌ Spring Boot 설정 필요
```

**접속 URL:**
```
개발: http://localhost:8080/swagger-ui.html
운영: https://api.example.com/swagger-ui.html
```

**장점:**
- Postman 없이도 API 테스트 가능
- 프론트엔드 개발자가 직접 API 확인
- API 명세서 자동 업데이트 (코드 변경 시)

**Spring Boot 설정 (2차 개발 시 추가 예정):**
```java
// build.gradle
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.0'

// application.yml
springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

---

### 4. 성능 테스트 (부하 테스트)

#### Apache JMeter

```
✅ 무료, 오픈소스
✅ GUI 기반
✅ 동시 사용자 수 시뮬레이션
✅ 다양한 리포트 제공
❌ 학습 곡선 높음
```

**언제 사용?**
- 인수 테스트 전 성능 검증
- "동시 접속자 1,000명 처리 가능" 증명 필요
- 병목 지점 찾기

**테스트 시나리오 예시:**
```
시나리오: 게시글 조회 성능 테스트
- 동시 사용자: 100명
- Ramp-up 시간: 10초 (10초 동안 100명 접속)
- 반복 횟수: 10회
- 총 요청 수: 1,000회

목표:
- 평균 응답 시간 < 200ms
- 에러율 < 1%
```

---

#### k6 (최신 트렌드)

```
✅ 무료, 오픈소스
✅ JavaScript 기반 스크립트
✅ CLI 기반 (CI/CD 통합 쉬움)
✅ 가볍고 빠름
❌ GUI 없음
```

**테스트 스크립트 예시:**
```javascript
import http from 'k6/http';
import { check } from 'k6';

export let options = {
  vus: 100, // 가상 사용자 100명
  duration: '30s', // 30초 동안
};

export default function () {
  let res = http.get('http://localhost:8080/api/posts');
  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 200ms': (r) => r.timings.duration < 200,
  });
}
```

---

## 🔄 실무 API 테스트 흐름

```
1단계: 개발 중 수동 테스트 (매일)
   ↓
   Postman으로 API 동작 확인
   - 요청/응답 형식 검증
   - 에러 케이스 확인

2단계: 자동화 테스트 작성 (기능 개발 완료 시)
   ↓
   JUnit + MockMvc
   - Controller 단위 테스트
   - 주요 API 케이스 자동화

3단계: 통합 테스트 (스프린트 종료 시)
   ↓
   실제 환경에서 전체 API 테스트
   - 프론트엔드 ↔ 백엔드 연동 확인
   - DB 데이터 검증

4단계: 성능 테스트 (인수 테스트 전)
   ↓
   JMeter / k6
   - 부하 테스트 (동시 사용자 시뮬레이션)
   - 응답 시간 측정

5단계: 인수 테스트 (고객 납품 전)
   ↓
   고객과 함께 테스트
   - 요구사항 충족 확인
   - 시나리오 기반 테스트
```

---

## 🎯 SimpleBlog 프로젝트 - 권장 도구

### 현재 단계: API 명세서 작성 완료

**다음 단계에서 사용할 도구:**

1. **개발 중**: Postman ⭐
   - API 개발하면서 바로바로 테스트
   - Collection 만들어서 저장

2. **자동화 테스트**: JUnit + MockMvc
   - 주요 API만 테스트 코드 작성
   - 회귀 테스트 방지

3. **문서화**: Swagger UI (2차 개발 시)
   - 프론트엔드 개발자와 공유
   - API 명세서 자동 생성

---

## 📊 도구별 추천 상황

| 상황 | 추천 도구 | 이유 |
|------|-----------|------|
| 개발 중 테스트 | Postman | GUI, 쉬움, Collection 관리 |
| 빠른 확인 | cURL | 터미널에서 바로 실행 |
| 자동화 테스트 | JUnit + MockMvc | Spring Boot 표준 |
| API 문서화 | Swagger UI | 자동 생성, 웹에서 테스트 |
| 성능 테스트 | JMeter | GUI, 리포트 풍부 |
| CI/CD 통합 | k6, cURL | CLI 기반, 스크립트 작성 |
| 팀 공유 | Postman Collection | JSON 파일로 공유 |
| GraphQL | Insomnia | GraphQL 지원 우수 |

---

## ⚡ 실무 Tip

### 1. Postman Collection 관리

**폴더 구조 예시:**
```
SimpleBlog API Collection
├── 📁 게시글 (Posts)
│   ├── GET    목록 조회
│   ├── POST   게시글 생성
│   ├── GET    상세 조회
│   ├── PUT    게시글 수정
│   └── DELETE 게시글 삭제
├── 📁 댓글 (Comments) - 2차 개발
└── 📁 인증 (Auth) - 2차 개발
```

**환경 변수 설정:**
```
개발 환경 (Local)
- base_url: http://localhost:8080
- api_prefix: /api

운영 환경 (Production)
- base_url: https://api.simpleblog.go.kr
- api_prefix: /api
```

**사용법:**
```
요청 URL: {{base_url}}{{api_prefix}}/posts
→ http://localhost:8080/api/posts (개발)
→ https://api.simpleblog.go.kr/api/posts (운영)
```

---

### 2. 테스트 케이스 작성 원칙

**정상 케이스 (Happy Path):**
```
✅ 유효한 데이터로 요청
✅ 200/201 응답 확인
✅ 응답 데이터 형식 확인
```

**에러 케이스 (Error Path):**
```
❌ 필수 항목 누락 → 400 Bad Request
❌ 잘못된 ID → 404 Not Found
❌ 권한 없음 → 403 Forbidden (2차)
❌ 서버 오류 → 500 Internal Server Error
```

**경계값 테스트:**
```
📏 제목 200자 초과 → 400
📏 제목 0자 (빈 문자열) → 400
📏 제목 200자 정확히 → 200
```

---

### 3. API 테스트 체크리스트

**개발 완료 후 확인:**
```
[ ] HTTP 메서드 올바른지 (GET/POST/PUT/DELETE)
[ ] 요청 Content-Type: application/json
[ ] 응답 상태 코드 올바른지
[ ] 응답 JSON 형식 올바른지
[ ] 에러 메시지 명확한지
[ ] 필수 항목 누락 시 400 에러
[ ] 존재하지 않는 ID 요청 시 404 에러
[ ] 한글/특수문자 처리 정상인지
[ ] 페이징 동작 정상인지 (목록 조회)
[ ] 정렬 동작 정상인지 (최신순)
```

---

### 4. 제안서/PT에서 테스트 설명

**테스트 전략 설명 예시:**

> "SimpleBlog는 **3단계 테스트 전략**을 적용합니다.
>
> **1단계: 단위 테스트** (개발 단계)
> - JUnit + MockMvc로 각 API 동작 검증
> - 테스트 커버리지 80% 이상 목표
>
> **2단계: 통합 테스트** (스프린트 종료)
> - Postman으로 실제 환경 테스트
> - 프론트엔드 연동 확인
>
> **3단계: 성능 테스트** (인수 테스트 전)
> - JMeter로 동시 접속자 1,000명 시뮬레이션
> - 평균 응답 시간 200ms 이하 보장"

---

### 5. 공무원이 자주 하는 질문

**Q1: "API 테스트를 어떻게 하나요?"**
A: "Postman이라는 도구를 사용합니다.
   마치 웹 브라우저처럼 API에 요청을 보내고 응답을 확인할 수 있습니다.
   시연해드릴 수 있습니다."

**Q2: "성능 테스트는 어떻게 하나요?"**
A: "JMeter로 동시 접속자를 시뮬레이션합니다.
   예를 들어 1,000명이 동시에 게시글을 조회할 때
   서버가 정상적으로 응답하는지 확인합니다."

**Q3: "자동화 테스트가 뭔가요?"**
A: "개발자가 코드를 수정할 때마다 자동으로 테스트가 실행됩니다.
   이미 만든 기능이 망가지지 않았는지 자동으로 확인하는 거죠.
   사람이 일일이 클릭해서 테스트하는 시간을 절약합니다."

**Q4: "테스트 커버리지가 뭔가요?"**
A: "전체 코드 중 테스트로 검증한 비율입니다.
   80%라면 전체 코드의 80%를 테스트한다는 뜻입니다.
   일반적으로 70-80% 이상을 목표로 합니다."

---

## 📚 도구별 공식 문서

### 수동 테스트 도구
- **Postman**: https://www.postman.com/
- **Insomnia**: https://insomnia.rest/
- **cURL**: https://curl.se/

### 자동화 테스트 도구
- **JUnit**: https://junit.org/junit5/
- **MockMvc**: https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html
- **REST Assured**: https://rest-assured.io/

### API 문서화
- **Swagger**: https://swagger.io/
- **OpenAPI**: https://www.openapis.org/

### 성능 테스트
- **Apache JMeter**: https://jmeter.apache.org/
- **k6**: https://k6.io/

### 학습 자료
- **Postman Learning Center**: https://learning.postman.com/
- **API Testing Best Practices**: https://testautomationresources.com/api-testing/

---

## 🎬 Postman 시작하기 (실습)

### 1. 설치 및 설정

1. https://www.postman.com/downloads/ 에서 다운로드
2. 계정 생성 (또는 Skip)
3. Workspace 생성: "SimpleBlog"

---

### 2. Collection 만들기

1. 좌측 "Collections" 클릭
2. "+" 버튼 → "Create Collection"
3. 이름: "SimpleBlog API"

---

### 3. 첫 번째 요청 만들기

**게시글 목록 조회:**
```
Method: GET
URL: http://localhost:8080/api/posts

Save as: "게시글 목록 조회"
```

**게시글 생성:**
```
Method: POST
URL: http://localhost:8080/api/posts
Headers:
  Content-Type: application/json
Body (raw, JSON):
{
  "title": "Postman 테스트",
  "content": "Postman으로 작성한 게시글입니다.",
  "author": "테스터"
}

Save as: "게시글 생성"
```

---

### 4. 환경 변수 설정

1. 우측 상단 "Environments" 클릭
2. "+" 버튼 → "Create Environment"
3. 이름: "Local"
4. 변수 추가:
   ```
   Variable: base_url
   Initial Value: http://localhost:8080
   Current Value: http://localhost:8080
   ```

5. 요청 URL 수정:
   ```
   {{base_url}}/api/posts
   ```

---

### 5. 테스트 스크립트 추가

**Tests 탭에 추가:**
```javascript
// 상태 코드 확인
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

// 응답 시간 확인
pm.test("Response time is less than 500ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(500);
});

// JSON 형식 확인
pm.test("Response is JSON", function () {
    pm.response.to.be.json;
});
```

---

## ✅ 결론

**SimpleBlog 프로젝트 권장:**
1. **개발 중**: Postman (수동 테스트)
2. **자동화**: JUnit + MockMvc (회귀 테스트)
3. **문서화**: Swagger UI (2차 개발)

**실무 프로젝트:**
- 개발 중: Postman (80% 사용)
- 자동화: JUnit + MockMvc (CI/CD 통합)
- 성능: JMeter (인수 테스트 전)
- 문서화: Swagger UI (팀 공유)

**핵심 원칙:**
- 정상 케이스 + 에러 케이스 모두 테스트
- Postman Collection은 Git으로 버전 관리
- 자동화 테스트는 주요 API만 작성 (80% 목표)
