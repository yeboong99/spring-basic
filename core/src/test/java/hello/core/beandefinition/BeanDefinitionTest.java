package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {

    // 아래 가져오는 설정파일이 java파일이면 아래와 같이 하고,
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // xml이라도 설정정보를 따로 읽어올 수도 있다.
    // GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    // 컨테이너는 빈이 어떤 파일로 정의되어있는지 알 필요가 없음. 빈이 어떻게 생겨먹어야하는지만 알면 됨. -> BeanDefinition개념 자체가 추상화한 것에 의존하는 개념임.
    // 다시말해 스프링 컨테이너는 설정정보가 자바 코드인지, XML인지, 기타 다른 형식의 파일인지 알 필요가 없음. 오직 BeanDefinition만 알면 된다.
    // 실질적으로 생성되는 Bean은 이 메타정보(BeanDefinition)를 기반으로 생성된다.
    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {  // 내가 애플리케이션으로 생성한 빈이라면 해당 빈 메타정보의 이름과 해당 객체 출력
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        " beanDefinition = " + beanDefinition);
            }
        }
    }
}

// 추가 학습내용
// 스프링에는 빈을 등록하는 방법이 크게 2가지가 있다.
// 1. 직접 빈을 등록하는 방법 : xml설정파일과 같이 빈을 그대로 써주는것.
// 2. 팩토리 메서드 방식 : AppConfig.java 클래스 파일과 같이 bean을 제공할 생성 함수를 정의해두는 방법.