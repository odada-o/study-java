# 데이터베이스 설계서

**프로젝트명:** SimpleBlog
**발주처:** ○○공공기관
**수행사:** (주)개발회사
**작성일:** 2025-10-17
**작성자:** DB설계팀
**버전:** 1.0

---

## 1. 개요

### 1.1 목적

SimpleBlog 프로젝트의 데이터 저장 및 관리를 위한 데이터베이스 구조를 정의한다.

### 1.2 범위

- 논리 ERD (Entity Relationship Diagram)
- 물리 ERD
- 테이블 정의서
- DDL (Data Definition Language)

---

## 2. 데이터베이스 정보

| 항목 | 내용 |
|------|------|
| DBMS | MySQL 8.0.35 |
| Character Set | utf8mb4 |
| Collation | utf8mb4_unicode_ci |
| Engine | InnoDB |
| Time Zone | Asia/Seoul |

---

## 3. ERD (Entity Relationship Diagram)

### 3.1 논리 ERD

```
┌─────────────────────────────────┐
│          게시글 (POSTS)          │
├─────────────────────────────────┤
│ PK  게시글ID                     │
│     제목                         │
│     내용                         │
│     작성자                       │
│     작성일시                     │
│     수정일시                     │
└─────────────────────────────────┘
```

### 3.2 물리 ERD

```
┌─────────────────────────────────┐
│            posts                 │
├─────────────────────────────────┤
│ PK  id (BIGINT)                  │
│     title (VARCHAR(200))         │
│     content (TEXT)               │
│     author (VARCHAR(100))        │
│     created_at (DATETIME)        │
│     updated_at (DATETIME)        │
│ IDX idx_created_at               │
└─────────────────────────────────┘
```

---

## 4. 테이블 정의서

### 4.1 posts (게시글)

**테이블 설명:** 블로그 게시글 정보를 저장하는 테이블

| 컬럼명 | 타입 | 길이 | NULL | 기본값 | 설명 |
|--------|------|------|------|--------|------|
| id | BIGINT | - | N | AUTO_INCREMENT | 게시글 ID (기본키) |
| title | VARCHAR | 200 | N | - | 제목 |
| content | TEXT | - | N | - | 내용 |
| author | VARCHAR | 100 | N | - | 작성자 |
| created_at | DATETIME | - | N | CURRENT_TIMESTAMP | 작성일시 |
| updated_at | DATETIME | - | N | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 수정일시 |

**제약조건:**
- PRIMARY KEY: id
- INDEX: idx_created_at (created_at DESC) - 최신순 정렬 성능 최적화

**데이터 예시:**
| id | title | content | author | created_at | updated_at |
|----|-------|---------|--------|------------|------------|
| 1 | SimpleBlog 오픈! | SimpleBlog 시스템이 오픈되었습니다. | 관리자 | 2025-10-17 09:00:00 | 2025-10-17 09:00:00 |
| 2 | 첫 번째 게시글 | 테스트 내용입니다. | 홍길동 | 2025-10-17 10:00:00 | 2025-10-17 10:00:00 |

---

## 5. DDL (Data Definition Language)

### 5.1 데이터베이스 생성

```sql
CREATE DATABASE IF NOT EXISTS simpleblog
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE simpleblog;
```

### 5.2 테이블 생성

```sql
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시글 ID',
    title VARCHAR(200) NOT NULL COMMENT '제목',
    content TEXT NOT NULL COMMENT '내용',
    author VARCHAR(100) NOT NULL COMMENT '작성자',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    INDEX idx_created_at (created_at DESC)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='게시글';
```

### 5.3 샘플 데이터 삽입

```sql
INSERT INTO posts (title, content, author) VALUES
('SimpleBlog 오픈!', 'SimpleBlog 시스템이 오픈되었습니다.', '관리자'),
('첫 번째 게시글', '테스트 내용입니다.', '홍길동'),
('두 번째 게시글', '안녕하세요!', '김철수');
```

---

## 6. 인덱스 전략

### 6.1 인덱스 목록

| 인덱스명 | 테이블 | 컬럼 | 타입 | 목적 |
|----------|--------|------|------|------|
| PRIMARY | posts | id | CLUSTERED | 기본키, 유일성 보장 |
| idx_created_at | posts | created_at | NONCLUSTERED | 최신순 정렬 성능 최적화 |

### 6.2 성능 예상

| 쿼리 | 예상 실행 계획 | 예상 응답 시간 |
|------|----------------|----------------|
| SELECT * FROM posts ORDER BY created_at DESC | idx_created_at 사용 | < 100ms |
| SELECT * FROM posts WHERE id = 1 | PRIMARY KEY 사용 | < 10ms |

---

## 7. 데이터 용량 산정

### 7.1 예상 데이터량

| 항목 | 예상값 |
|------|--------|
| 연간 게시글 수 | 2,000건 (50명 × 주 1회) |
| 5년 총 게시글 수 | 10,000건 |
| 게시글당 평균 크기 | 2KB |
| 총 데이터 크기 (5년) | 20MB |

### 7.2 디스크 용량 계획

| 구분 | 용량 | 비고 |
|------|------|------|
| 데이터 | 20MB | 5년 기준 |
| 인덱스 | 5MB | 약 25% |
| 로그 | 50MB | Binary Log |
| 여유 공간 | 200MB | 성능 확보 |
| **합계** | **275MB** | 1GB 할당 권장 |

---

## 8. 백업 및 복구 전략

### 8.1 백업 정책

| 백업 유형 | 주기 | 보관 기간 | 방법 |
|----------|------|----------|------|
| 전체 백업 | 매일 새벽 2시 | 30일 | mysqldump |
| 증분 백업 | 1시간마다 | 7일 | Binary Log |

### 8.2 복구 시나리오

**시나리오 1: 테이블 실수 삭제**
```sql
-- 전날 백업에서 복구
mysql -u root -p simpleblog < backup_2025-10-16.sql
```

**시나리오 2: 특정 시점 복구**
```bash
# Binary Log를 이용한 특정 시간 복구
mysqlbinlog --start-datetime="2025-10-17 14:00:00" \
            --stop-datetime="2025-10-17 14:30:00" \
            mysql-bin.000001 | mysql -u root -p
```

---

## 9. 보안 정책

### 9.1 접근 권한

| 사용자 | 권한 | 용도 |
|--------|------|------|
| root | ALL | DBA 전용 |
| simpleblog_app | SELECT, INSERT, UPDATE, DELETE | 애플리케이션 |
| simpleblog_read | SELECT | 읽기 전용 (BI 도구) |

### 9.2 권한 생성 SQL

```sql
-- 애플리케이션 사용자
CREATE USER 'simpleblog_app'@'%' IDENTIFIED BY 'STRONG_PASSWORD';
GRANT SELECT, INSERT, UPDATE, DELETE ON simpleblog.* TO 'simpleblog_app'@'%';

-- 읽기 전용 사용자
CREATE USER 'simpleblog_read'@'%' IDENTIFIED BY 'READ_PASSWORD';
GRANT SELECT ON simpleblog.* TO 'simpleblog_read'@'%';

FLUSH PRIVILEGES;
```

---

## 10. 변경 이력

| 버전 | 날짜 | 작성자 | 변경 내용 |
|------|------|--------|----------|
| 1.0 | 2025-10-17 | DB설계팀 | 최초 작성 |

---

## 11. 승인

| 역할 | 소속 | 이름 | 서명 | 날짜 |
|------|------|------|------|------|
| DBA | (주)개발회사 | | | |
| 개발팀장 | (주)개발회사 | | | |
| PM | (주)개발회사 | | | |
| 발주처 담당자 | ○○공공기관 | | | |

---

**문서 끝**
