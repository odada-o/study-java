# 01. Java 소개 및 개발 환경

> **학습 목표**: Java가 무엇인지 이해하고, 첫 번째 프로그램을 작성해봅니다.

---

## 📚 Java란 무엇인가?

### 1. Java의 탄생

**Java**는 1995년 썬 마이크로시스템즈(Sun Microsystems)에서 제임스 고슬링(James Gosling)이 개발한 프로그래밍 언어입니다.

현재는 오라클(Oracle)이 관리하고 있으며, 전 세계에서 가장 많이 사용되는 프로그래밍 언어 중 하나입니다.

### 2. Java의 특징

#### ✅ 객체지향 언어 (Object-Oriented)
- 모든 것을 **객체(Object)**로 표현
- 코드 재사용성과 유지보수성이 뛰어남
- 실제 세계를 프로그래밍으로 표현하기 쉬움

#### ✅ 플랫폼 독립적 (Platform Independent)
- **"Write Once, Run Anywhere"** (한 번 작성하면 어디서든 실행)
- Windows, macOS, Linux 등 어떤 운영체제에서도 동일하게 실행
- JVM(Java Virtual Machine) 덕분에 가능!

#### ✅ 자동 메모리 관리
- **가비지 컬렉터(Garbage Collector)**가 자동으로 메모리 관리
- 메모리 누수(memory leak) 걱정 감소
- 개발자는 비즈니스 로직에 집중 가능

#### ✅ 안정성과 보안성
- 강력한 타입 체크 (Strong Type Checking)
- 예외 처리 메커니즘
- 보안 관련 기능 내장

#### ✅ 풍부한 라이브러리와 프레임워크
- Java API: 수많은 내장 라이브러리
- Spring, Hibernate 등 강력한 프레임워크
- 거대한 개발자 커뮤니티

---

## 🔧 JDK, JRE, JVM의 차이

Java 개발을 시작하기 전에 꼭 알아야 할 3가지 개념입니다!

### 1. JVM (Java Virtual Machine)

```
┌─────────────────────────┐
│   Java 프로그램 (.class) │
└───────────┬─────────────┘
            │
            ▼
┌─────────────────────────┐
│         JVM             │  ◀── Java 실행 엔진
│  (플랫폼에 맞게 실행)     │
└───────────┬─────────────┘
            │
            ▼
┌─────────────────────────┐
│    운영체제 (OS)         │
│ Windows/macOS/Linux     │
└─────────────────────────┘
```

- **역할**: Java 바이트코드(.class 파일)를 해석하고 실행하는 가상 머신
- **특징**: 운영체제에 맞게 바이트코드를 기계어로 변환
- **결과**: Java가 플랫폼 독립적일 수 있는 핵심 이유!

### 2. JRE (Java Runtime Environment)

```
┌─────────────────────────┐
│         JRE             │
│  ┌─────────────────┐   │
│  │      JVM        │   │  ◀── 실행 환경
│  └─────────────────┘   │
│  ┌─────────────────┐   │
│  │  Java 라이브러리 │   │
│  └─────────────────┘   │
└─────────────────────────┘
```

- **역할**: Java 프로그램을 **실행**하는 데 필요한 환경
- **포함**: JVM + Java 표준 라이브러리
- **사용**: Java 프로그램을 실행만 하고 싶을 때 (개발 안 함)

### 3. JDK (Java Development Kit)

```
┌─────────────────────────┐
│         JDK             │
│  ┌─────────────────┐   │
│  │      JRE        │   │
│  │  ┌──────────┐   │   │
│  │  │   JVM    │   │   │  ◀── 개발 도구
│  │  └──────────┘   │   │
│  └─────────────────┘   │
│  ┌─────────────────┐   │
│  │ 개발 도구        │   │
│  │ (javac, java 등)│   │
│  └─────────────────┘   │
└─────────────────────────┘
```

- **역할**: Java 프로그램을 **개발**하는 데 필요한 도구 모음
- **포함**: JRE + 컴파일러(javac) + 디버거 + 기타 개발 도구
- **사용**: Java 프로그램을 개발할 때 필수!

### 📌 정리

| 구분 | 포함 내용 | 용도 |
|------|----------|------|
| **JVM** | Java 실행 엔진 | 바이트코드 실행 |
| **JRE** | JVM + 표준 라이브러리 | Java 프로그램 실행 |
| **JDK** | JRE + 개발 도구 | Java 프로그램 개발 |

**💡 핵심**: 개발자는 **JDK**를 설치해야 합니다!

---

## 💻 Java 프로그램 실행 과정

Java 프로그램이 어떻게 실행되는지 단계별로 알아봅시다.

```
1️⃣ 작성                2️⃣ 컴파일               3️⃣ 실행
┌──────────────┐      ┌──────────────┐      ┌──────────────┐
│ HelloWorld.java│  →  │ HelloWorld.class│ →  │   JVM이      │
│ (소스 코드)    │      │ (바이트코드)   │      │   실행       │
└──────────────┘      └──────────────┘      └──────────────┘
     (개발자)            javac 명령어           java 명령어
```

### 1단계: 소스 코드 작성 (.java)

```java
// HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");
    }
}
```

- 사람이 읽을 수 있는 Java 소스 코드
- 파일 확장자: `.java`

### 2단계: 컴파일 (.class)

```bash
javac HelloWorld.java
```

- **javac** (Java Compiler): 소스 코드를 바이트코드로 변환
- 결과물: `HelloWorld.class` 파일 생성
- 바이트코드: JVM이 이해할 수 있는 중간 코드 (0과 1)

### 3단계: 실행

```bash
java HelloWorld
```

- **java** 명령어: JVM이 바이트코드를 실행
- 결과: 화면에 "Hello, Java!" 출력

---

## 🎯 첫 번째 Java 프로그램 작성하기

이제 직접 Java 프로그램을 작성해봅시다!

### HelloWorld.java

```java
// 이 파일은 Example01_HelloWorld.java에서 확인할 수 있습니다
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Java World!");
    }
}
```

### 코드 설명 (한 줄씩!)

```java
public class HelloWorld {
```
- `public`: 이 클래스는 어디서든 접근 가능 (공개)
- `class`: 클래스를 선언하는 키워드
- `HelloWorld`: 클래스 이름 (파일 이름과 동일해야 함!)

```java
    public static void main(String[] args) {
```
- `public`: 메서드를 외부에서 호출 가능
- `static`: 객체 생성 없이 호출 가능
- `void`: 이 메서드는 값을 반환하지 않음
- `main`: Java 프로그램의 **시작점** (진입점, Entry Point)
- `String[] args`: 명령줄 인자를 받는 매개변수

```java
        System.out.println("Hello, Java World!");
```
- `System`: Java 시스템 클래스
- `out`: 표준 출력 스트림 (화면)
- `println`: 괄호 안의 내용을 출력하고 줄바꿈
- `"Hello, Java World!"`: 출력할 문자열 (큰따옴표로 감쌈)

```java
    }
}
```
- 중괄호 `{}`로 코드 블록을 구분

---

## 🚀 실행 방법

### 방법 1: 터미널에서 실행

```bash
# 1. 해당 폴더로 이동
cd 01-기초/01-Java소개

# 2. 컴파일
javac Example01_HelloWorld.java

# 3. 실행
java Example01_HelloWorld
```

### 방법 2: VS Code에서 실행

1. Java 파일 열기
2. 우측 상단의 ▶️ 버튼 클릭
3. 또는 `Ctrl+F5` (macOS: `Cmd+F5`)

---

## 🧩 연습 문제

`Exercise01.java` 파일을 열어서 다음 문제를 풀어보세요!

1. "안녕하세요, Java!"를 출력하는 프로그램 작성
2. 자신의 이름을 출력하는 프로그램 작성
3. 여러 줄을 출력하는 프로그램 작성

---

## 📝 핵심 정리

✅ Java는 **객체지향**, **플랫폼 독립적**인 언어  
✅ **JDK**(개발 도구) > **JRE**(실행 환경) > **JVM**(실행 엔진)  
✅ Java 프로그램은 **작성 → 컴파일 → 실행** 3단계  
✅ 모든 Java 프로그램은 `main` 메서드에서 시작  
✅ `System.out.println()`으로 화면에 출력  

---

## ❓ 궁금한 점

- "왜 클래스 이름과 파일 이름이 같아야 하나요?"
- "main 메서드는 왜 저렇게 복잡한가요?"
- "print와 println의 차이는 무엇인가요?"

➡️ 궁금한 점이 있다면 언제든지 질문하세요! 🙋‍♂️

---

**다음 학습**: 02. 변수와 자료형

