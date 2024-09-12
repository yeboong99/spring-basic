package hello.core.singleton;

public class SingletonService {

    // 자기 자신을 static으로 인스턴스를 하나 가진다.
    private static final SingletonService instance = new SingletonService();

    // public으로 이 static 인스턴스를 받을 수 있는 메서드를 만들어둔다.
    public static SingletonService getInstance() {
        return instance;
    }

    // 아래는 생성자인데, private 생성자를 이용하면 현재 클래스(SingletonService) 외의 그 어떤 곳에서도 객체를 생성해낼 수 없다. 이렇게 하면 new 키워드로 외부에서 생성하는 것도 막는다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}

// 이렇게 하면 이 클래스 밖 그 어떤 곳에서도 이 클래스의 인스턴스를 생성할 수 없으며, 자바를 실행하면서 인스턴스가 딱 한개 생성된다.
// 또한 이 인스턴스를 꺼낼 수 있는(참조할 수 있는) 방법은 오직 getInstance()로밖에 할 수 없다.

