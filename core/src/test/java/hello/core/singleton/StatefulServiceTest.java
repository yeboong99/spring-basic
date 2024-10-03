package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // Thread A : 고객 A가 10000원을 주문함.
        statefulService1.order("orderA", 10000);

        // Thread B : 고객 B가 20000원을 주문함.
        statefulService2.order("orderB", 20000);


        // Thread A : 고객 A의 주문 금액을 조회함.
        int price = statefulService2.getPrice();

        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

    }   // A사용자를 조회했는데 20000이 나온다. 고객 B가 고객 A의 금액을 바꿀 수 있다.
        // -> 같은 객체를 공유할 때는 다른 사용자가 상태를 변경할 수 있는 필드가 존재하면 안된다. 또는 변경할 수 없도록 설계해야 한다.
    // 실무에서도 종종 발생하는 문제. 공유 필드는 반드시 조심해야 한다. 스프링 빈은 항상 무상태(stateless)로 설계하자.

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}