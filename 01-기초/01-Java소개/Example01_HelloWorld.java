/**
 * 예제 01: Hello World
 * 
 * 목표: 첫 번째 Java 프로그램을 작성하고 실행해봅니다.
 * 
 * 학습 내용:
 * 1. Java 프로그램의 기본 구조
 * 2. main 메서드의 역할
 * 3. 화면 출력 방법 (println)
 */

public class Example01_HelloWorld {
    /**
     * main 메서드: Java 프로그램의 시작점 (Entry Point)
     * 
     * JVM은 프로그램을 실행할 때 가장 먼저 main 메서드를 찾아서 실행합니다.
     * 
     * @param args 명령줄에서 전달되는 인자 (지금은 사용하지 않음)
     */
    public static void main(String[] args) {
        // println: 괄호 안의 내용을 출력하고 줄을 바꿉니다
        System.out.println("Hello, Java World!");
        
        // 여러 줄 출력하기
        System.out.println("환영합니다!");
        System.out.println("Java 학습을 시작합니다.");
        
        // print vs println 비교
        System.out.print("이것은 줄바꿈 없이 ");
        System.out.print("출력됩니다. ");
        System.out.println("이제 줄바꿈!");
        
        System.out.println("다음 줄입니다.");
    }
}

/*
 * 💡 실행 방법:
 * 
 * 1. 터미널에서:
 *    javac Example01_HelloWorld.java
 *    java Example01_HelloWorld
 * 
 * 2. VS Code에서:
 *    우측 상단의 ▶️ 버튼 클릭 또는 Ctrl+F5
 * 
 * 📝 예상 출력:
 * Hello, Java World!
 * 환영합니다!
 * Java 학습을 시작합니다.
 * 이것은 줄바꿈 없이 출력됩니다. 이제 줄바꿈!
 * 다음 줄입니다.
 * 
 * 🎯 이해했다면:
 * - println과 print의 차이를 설명할 수 있나요?
 * - main 메서드는 왜 필요한가요?
 * - 문자열을 큰따옴표로 감싸는 이유는?
 */

