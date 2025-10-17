# DB 설계서 - PM 학습 가이드

> 이 문서는 PM이 DB 설계서를 이해하고 활용하기 위한 학습 자료입니다.
> 고객에게 제출하는 공식 문서는 **"DB설계서.md"**를 사용하세요.

**작성일:** 2025-10-17

---

## 📌 왜 DB 설계서를 작성하나요?

### "데이터를 어떻게 저장할지 설계하자!"

DB 설계는 **건물 설계도**와 같습니다. 건물을 짓기 전에 설계도를 그리듯이, 개발 전에 DB 구조를 설계합니다.

**실무 예시:**
```
고객: "게시글을 저장하고 싶어요."
PM: "어떤 정보를 저장할까요?"
고객: "제목, 내용, 작성자, 작성일..."
PM: (DB 설계서 작성)
    → posts 테이블
    → 컬럼: id, title, content, author, created_at, updated_at
개발자: "DB 설계서 보고 테이블 만들겠습니다!"
```

---

## 🎯 PM이 꼭 알아야 할 것

### 1. DB 설계서의 역할

**개발 시작 전 필수 산출물:**
- 요구사항 정의서 → DB 설계서 → API 명세서 → 개발
- DB 구조 변경은 개발 비용 ↑ → 신중하게 설계

**제안서 필수 자료:**
- 공공기관 제안서에 ERD는 필수
- "우리는 데이터 구조를 이렇게 설계했습니다" 증명
- 기술 제안 30~40점 배점에 포함

**개발팀 간 소통:**
- 백엔드: DB 테이블 생성
- 프론트엔드: 어떤 데이터를 주고받는지 파악
- DBA: 성능 최적화 (인덱스, 파티션)

---

### 2. 실무 Tip

⭐ **DB 설계는 변경이 어렵다!**
```
요구사항 변경: 쉬움
API 변경: 보통
DB 구조 변경: 어려움 ← 이미 저장된 데이터 때문!

예시:
운영 중인 DB에 컬럼 추가:
→ 기존 데이터 100만 건 마이그레이션
→ 시스템 중단 시간 발생
→ 롤백 계획 필요
→ 비용 증가

따라서: DB 설계는 신중하게!
```

⭐ **ERD는 제안서 PT의 핵심**
```
공무원들은 ERD를 보고 기술력을 판단합니다.

❌ 나쁜 PT:
"저희는 최신 기술을 사용합니다!" (추상적)

✅ 좋은 PT:
"ERD를 보시면 posts 테이블에 6개 컬럼이 있고,
 created_at 컬럼에 인덱스를 걸어서 최신순 조회를 최적화했습니다.
 예상 데이터 10만 건 기준으로 조회 속도는 50ms 이하입니다."
→ 구체적! 신뢰감 ↑
```

⭐ **테이블 이름은 영어로**
```
❌ 나쁜 예: 게시글 (한글)
✅ 좋은 예: posts (영어 복수형)

이유:
1. DB는 영어 권장 (국제 표준)
2. 한글은 인코딩 문제 발생 가능
3. 개발자 간 소통 용이
```

⭐ **Primary Key는 필수**
```
Primary Key (기본키):
- 각 행을 고유하게 식별하는 컬럼
- posts 테이블의 id 컬럼

없으면:
- "1번 게시글 수정해줘" 불가능 (어떤 게 1번인지 모름)
- 중복 데이터 발생 가능
```

⭐ **인덱스로 성능 최적화**
```
인덱스 (Index):
- 책의 색인처럼 빠른 검색을 위한 구조
- created_at 컬럼에 인덱스 → 최신순 정렬 빠름

인덱스 없으면:
- 100만 건 데이터에서 검색 시 10초 소요
인덱스 있으면:
- 100만 건 데이터에서 검색 시 0.05초 소요

BUT:
- 인덱스 너무 많으면 INSERT/UPDATE 느려짐
- 자주 조회하는 컬럼에만 인덱스 설정
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
   3.3 데이터베이스 설계  ← DB설계서.md 내용 여기 들어감!
        - ERD 다이어그램 (필수!)
        - 테이블 정의서
        - 인덱스 설계
        - 백업 전략
   3.4 API 명세서
4. 프로젝트 수행 계획
5. 예산
```

**PT 발표 스크립트 예시:**
> "저희는 데이터베이스를 다음과 같이 설계했습니다.
>
> **posts 테이블**을 중심으로 게시글 정보를 관리합니다.
>
> **6개 컬럼**:
> - id: 자동 증가하는 고유 번호
> - title: 제목 (최대 200자)
> - content: 내용 (TEXT 타입으로 긴 글 지원)
> - author: 작성자 (최대 100자)
> - created_at: 작성일시 (자동 생성)
> - updated_at: 수정일시 (자동 업데이트)
>
> **성능 최적화**:
> - created_at 컬럼에 인덱스를 설정하여
>   최신순 조회 속도를 50ms 이하로 보장합니다.
>
> **안정성**:
> - MySQL 8.0의 InnoDB 엔진을 사용하여
>   트랜잭션과 크래시 복구를 지원합니다.
>
> **확장성**:
> - 초기에는 1개 테이블이지만,
>   향후 댓글(comments), 카테고리(categories) 테이블을
>   추가할 수 있도록 확장 가능하게 설계했습니다."

**공무원 질문 대응:**
```
Q: "데이터가 많아지면 느려지지 않나요?"
A: "인덱스를 설정했기 때문에 100만 건 데이터에서도
    조회 속도는 50ms 이하로 유지됩니다.
    또한 파티셔닝 기법으로 데이터를 분산 저장할 수 있습니다."

Q: "데이터 백업은 어떻게 하나요?"
A: "매일 자정에 전체 백업, 1시간마다 증분 백업을 수행합니다.
    백업 데이터는 30일간 보관하며,
    재해 발생 시 1시간 이내에 복구 가능합니다."

Q: "개인정보는 어떻게 보호하나요?"
A: "작성자(author) 컬럼은 암호화하여 저장하며,
    DB 접근은 VPN을 통해서만 가능하도록 제한합니다."
```

---

## 🎤 공무원이 자주 하는 질문

### Q1: "ERD가 뭔가요?"

**A:** Entity Relationship Diagram의 약자로, 데이터베이스 구조를 그림으로 표현한 것입니다.

```
ERD = 데이터베이스 설계도

건축물 설계도처럼:
- 어떤 테이블(건물)이 있는지
- 각 테이블에 어떤 컬럼(방)이 있는지
- 테이블 간 관계(복도)는 어떤지

를 시각적으로 보여줍니다.

SimpleBlog ERD 예시:
┌─────────────────┐
│     posts       │
├─────────────────┤
│ id (PK)         │ ← Primary Key
│ title           │
│ content         │
│ author          │
│ created_at      │
│ updated_at      │
└─────────────────┘
```

---

### Q2: "테이블이 1개밖에 없는데 괜찮나요?"

**A:** SimpleBlog는 학습용이라 1개지만, 실제 프로젝트는 10~20개입니다.

```
SimpleBlog (학습용):
- posts 테이블 1개
- 목적: PM이 DB 설계 개념 이해

실제 공공기관 프로젝트:
- 10~20개 테이블
- 예시: users, posts, comments, categories, files, logs, ...
- 테이블 간 관계 (1:N, N:M)

하지만!
DB 설계 방법론은 동일합니다:
1. 요구사항에서 엔티티(명사) 추출
2. 엔티티별 속성(컬럼) 정의
3. 엔티티 간 관계 정의
4. ERD 작성
5. DDL 작성
```

---

### Q3: "MySQL 대신 Oracle을 써야 하나요?"

**A:** 프로젝트 규모와 예산에 따라 다릅니다.

```
MySQL (무료):
- 중소규모 프로젝트
- 예산 제약 있는 경우
- 동시 접속 1,000명 이하
- 데이터 1,000만 건 이하
→ SimpleBlog는 MySQL 사용

Oracle (유료, 1년 라이선스 약 1,500만원):
- 대규모 프로젝트
- 금융, 공공기관 핵심 시스템
- 동시 접속 10,000명 이상
- 데이터 1억 건 이상
→ 고가용성, 백업/복구 기능 강력

PostgreSQL (무료):
- MySQL과 Oracle 중간
- 오픈소스지만 고급 기능 지원
- 최근 공공기관에서 많이 선택
```

**제안서에 작성하는 법:**
```
"본 프로젝트는 동시 접속자 500명, 데이터 100만 건 규모로,
 MySQL 8.0으로 충분히 구현 가능합니다.
 Oracle 대비 연간 1,500만원의 라이선스 비용을 절감할 수 있습니다."
```

---

### Q4: "컬럼 타입은 어떻게 정하나요?"

**A:** 저장할 데이터의 종류와 크기에 따라 결정합니다.

```
숫자:
- INT: 정수 (-21억 ~ 21억)
- BIGINT: 큰 정수 (-900경 ~ 900경) ← id는 BIGINT 권장
- DECIMAL: 돈 (소수점 정확도 필요)

문자:
- VARCHAR(길이): 가변 길이 문자열 (예: 제목 VARCHAR(200))
- TEXT: 긴 글 (예: 내용 TEXT)
- CHAR(길이): 고정 길이 (예: 우편번호 CHAR(5))

날짜/시간:
- DATE: 날짜만 (2025-10-17)
- DATETIME: 날짜+시간 (2025-10-17 14:30:00)
- TIMESTAMP: DATETIME과 유사 (자동 업데이트 가능)

불린:
- BOOLEAN: 참/거짓 (예: 공개 여부)
```

**SimpleBlog posts 테이블:**
```sql
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,        -- 큰 숫자
    title VARCHAR(200) NOT NULL,                 -- 제목 최대 200자
    content TEXT NOT NULL,                       -- 긴 글
    author VARCHAR(100) NOT NULL,                -- 작성자 최대 100자
    created_at DATETIME NOT NULL,                -- 작성일시
    updated_at DATETIME NOT NULL                 -- 수정일시
);
```

---

### Q5: "인덱스를 왜 created_at에만 거나요?"

**A:** 자주 검색하는 컬럼에만 인덱스를 설정합니다.

```
SimpleBlog의 주요 기능:
1. 게시글 목록 조회 (최신순 정렬) ← created_at으로 정렬!
2. 게시글 상세 조회 (id로 조회) ← id는 이미 Primary Key (자동 인덱스)
3. 게시글 작성/수정/삭제 (id로 조회)

따라서:
- created_at 컬럼에 인덱스 설정 ✅ (최신순 정렬 빠르게)
- title, content 컬럼에는 인덱스 불필요 ❌ (검색 기능 없음)

만약 "제목으로 검색" 기능 추가하면:
- title 컬럼에도 인덱스 설정 필요
```

**인덱스 설정 기준:**
```
인덱스 설정해야 할 컬럼:
[ ] WHERE 절에 자주 사용하는 컬럼
[ ] ORDER BY 절에 자주 사용하는 컬럼
[ ] JOIN 조건에 사용하는 컬럼

인덱스 설정하면 안 되는 컬럼:
[ ] 데이터 종류가 적은 컬럼 (예: 성별 M/F)
[ ] 자주 수정되는 컬럼
[ ] 테이블 데이터가 적은 경우 (1만 건 이하)
```

---

## 📊 DB 설계 실전 팁

### 1. 테이블 설계 순서

```
1단계: 요구사항에서 엔티티(명사) 추출
요구사항: "게시글을 작성하고 조회한다"
→ 엔티티: 게시글 (posts)

2단계: 엔티티별 속성(컬럼) 정의
게시글이 가져야 할 정보:
- 고유 번호 → id
- 제목 → title
- 내용 → content
- 작성자 → author
- 작성일시 → created_at
- 수정일시 → updated_at

3단계: 컬럼별 타입/제약조건 정의
id: BIGINT, AUTO_INCREMENT, PRIMARY KEY
title: VARCHAR(200), NOT NULL
content: TEXT, NOT NULL
author: VARCHAR(100), NOT NULL
created_at: DATETIME, NOT NULL, DEFAULT CURRENT_TIMESTAMP
updated_at: DATETIME, NOT NULL, ON UPDATE CURRENT_TIMESTAMP

4단계: 인덱스 설계
created_at: INDEX (최신순 조회 성능 향상)

5단계: ERD 작성
테이블 구조를 그림으로 표현

6단계: DDL 작성
CREATE TABLE SQL 작성
```

---

### 2. 컬럼 이름 규칙 (Naming Convention)

```
✅ 권장:
- snake_case: created_at, updated_at (단어 사이 언더스코어)
- 영어 소문자
- 약어 지양 (reg_dt ❌ → created_at ✅)

❌ 비권장:
- camelCase: createdAt (Java 스타일, DB에서는 잘 안 씀)
- UPPER_CASE: CREATED_AT (옛날 스타일)
- 한글: 작성일시 (인코딩 문제)
```

**SimpleBlog 컬럼 이름:**
```sql
id              ✅ (짧고 명확)
title           ✅ (짧고 명확)
content         ✅ (짧고 명확)
author          ✅ (짧고 명확)
created_at      ✅ (snake_case)
updated_at      ✅ (snake_case)
```

---

### 3. 제약조건 (Constraints) 이해

```
PRIMARY KEY (기본키):
- 각 행을 고유하게 식별
- 중복 불가, NULL 불가
- 예: id BIGINT PRIMARY KEY

NOT NULL:
- 빈 값 불가
- 예: title VARCHAR(200) NOT NULL

DEFAULT:
- 기본값 설정
- 예: created_at DATETIME DEFAULT CURRENT_TIMESTAMP

AUTO_INCREMENT:
- 자동 증가 (1, 2, 3, ...)
- 예: id BIGINT AUTO_INCREMENT

UNIQUE:
- 중복 불가 (단, NULL 허용)
- 예: email VARCHAR(100) UNIQUE

FOREIGN KEY (외래키):
- 다른 테이블 참조
- 예: user_id BIGINT REFERENCES users(id)
  (SimpleBlog에서는 사용 안 함, 1개 테이블만 있어서)
```

---

### 4. 성능 최적화 체크리스트

```
[ ] Primary Key 설정 (필수)
[ ] 자주 검색하는 컬럼에 인덱스 설정
[ ] VARCHAR 길이 적절히 설정 (너무 크지 않게)
[ ] TEXT는 꼭 필요한 경우만 사용 (성능 저하)
[ ] DATETIME vs TIMESTAMP 선택 (TIMESTAMP가 더 가벼움)
[ ] utf8mb4 인코딩 사용 (이모지 지원)
[ ] InnoDB 엔진 사용 (트랜잭션 지원)
```

---

### 5. 백업 전략

```
실무 백업 전략:

일일 백업 (Full Backup):
- 매일 자정에 전체 DB 백업
- 30일간 보관
- 비용: 스토리지 비용

증분 백업 (Incremental Backup):
- 1시간마다 변경된 데이터만 백업
- 7일간 보관
- 복구 시간 단축

바이너리 로그 (Binary Log):
- 모든 변경 사항 기록
- 특정 시점으로 복구 가능 (Point-in-Time Recovery)

재해 복구 계획 (DR, Disaster Recovery):
- 주 센터 장애 시 부 센터로 전환
- RTO (Recovery Time Objective): 1시간 이내
- RPO (Recovery Point Objective): 데이터 손실 10분 이내
```

**제안서에 작성하는 법:**
```
"데이터 백업은 다음과 같이 수행합니다:
1. 매일 자정 전체 백업 (30일 보관)
2. 1시간마다 증분 백업 (7일 보관)
3. 재해 발생 시 1시간 이내 복구 가능
4. 데이터 손실 최대 10분 이내"
```

---

## 📁 문서 관리 Tip

### ERD 도구 선택

```
공공기관 제안서 (60%):
- ERWin (유료, 약 200만원)
- PowerDesigner (유료, 약 150만원)
- 이유: 공무원들이 익숙함, 전문적인 느낌

스타트업 (40%):
- dbdiagram.io (무료) ← 추천!
- draw.io (무료)
- MySQL Workbench (무료)

SimpleBlog:
- Markdown + DDL (무료, Git 버전 관리 가능)
- 학습용이라 텍스트 기반으로 충분
```

---

### 버전 관리

```
v1.0 (2025-10-17): 초안 작성 (posts 테이블 1개)
v1.1 (2025-10-20): 인덱스 추가 (created_at)
v1.2 (2025-10-22): 컬럼 타입 변경 (title VARCHAR(100) → 200)
v2.0 (2025-10-25): 고객 최종 승인 (베이스라인)
```

---

## ✅ DB 설계서 작성 완료 체크리스트

```
[ ] 프로젝트 개요 작성
[ ] 테이블 목록 정의
[ ] 각 테이블별 컬럼 정의 (이름, 타입, 제약조건, 설명)
[ ] Primary Key 설정
[ ] 인덱스 설계
[ ] ERD 작성 (논리 ERD, 물리 ERD)
[ ] DDL 작성 (CREATE TABLE SQL)
[ ] 백업 전략 작성
[ ] 보안 정책 작성
[ ] 용량 산정 작성
[ ] 고객 검토 완료
[ ] 내부 리뷰 완료 (DBA 검토)
[ ] 승인란 서명 받음
[ ] Git 커밋 및 Tag 생성
```

---

## 🔧 DDL 실행 방법 (참고)

개발자가 실제로 DB에 테이블을 생성하는 방법:

```bash
# 1. MySQL 접속
mysql -u root -p

# 2. 데이터베이스 생성
CREATE DATABASE simpleblog;
USE simpleblog;

# 3. DDL 실행
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_created_at (created_at DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

# 4. 테이블 생성 확인
SHOW TABLES;
DESC posts;
```

PM은 이 과정을 직접 하진 않지만, **"DDL을 실행하면 테이블이 생성된다"** 정도는 알아야 개발자와 소통 가능합니다.

---

## 다음 단계

DB 설계서 완료 후:
1. **API 명세서** → RESTful API 정의
2. **기술 스택 선정** → 개발 환경 결정
3. **개발 환경 구축** → MySQL 설치, Spring Boot 프로젝트 생성

---

**문서 끝**
