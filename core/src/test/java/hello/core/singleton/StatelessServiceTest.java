package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatelessServiceTest {
    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulServiceTest.TestConfig.class);
        StatelessService statelessService1 = ac.getBean(StatelessService.class);
        StatelessService statelessService2 = ac.getBean(StatelessService.class);

        // 고객 A, B 주문 가격을 각각 공유되지 않는 변수에 넣어 사용.

        // Thread A : 고객 A가 10000원을 주문함.
        int userAPrice = statelessService1.order("orderA", 10000);

        // Thread B : 고객 B가 20000원을 주문함.
        int userBPrice = statelessService2.order("orderB", 20000);


        // Thread A : 고객 A의 주문 금액을 조회함.

        System.out.println("userAPrice = " + userAPrice);


    }   // 아예 공유되지 않는 변수에 담아서 해당 값을 사용하도록 한다. B가 객체의 필드값을 변경해도 A의 가격정보에 변경을 줄 수 없도록.

    static class TestConfig {

        @Bean
        public StatelessService statefulService() {
            return new StatelessService();
        }
    }
}