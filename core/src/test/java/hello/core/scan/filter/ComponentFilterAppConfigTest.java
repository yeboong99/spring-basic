package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        // 이 클래스를 어플리케이션 컨텍스트에 담는다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        // beanA는 @MyIncludeComponent를 붙여놨으므로 조회가 된다. isNotNull true로 테스트 통과
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull();

        // beanB는 @MyExcludeComponent를 붙여놨으므로 조회가 되지 않는다. 예외가 터지는데, NoSuchBeanDefinitionException이 터진다.
        org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );

    }

    @Configuration
    @ComponentScan( // 사실 @ComponentScan.Filter의 type옵션 디폴트값이 FilterType.ANNOTATION이라 생략 가능함. ( includeFilters 예시 )
            // 내가 만든 어노테이션 MyIncludeComponent이 붙은 컴포넌트에 대하여 includeFilter에 지정한다. 즉, @MyIncludeComponent가 붙을 경우 컴포넌트 스캔의 대상이 된다.
            includeFilters = @ComponentScan.Filter(classes = MyIncludeComponent.class),
            // 내가 만든 어노테이션 MyExcludeComponent이 붙은 컴포넌트에 대하여 excludeFilter로 지정한다. 즉, @MyExcludeComponent가 붙을 경우 컴포넌트 스캔 대상에서 제외된다.
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)    // FilterType 5가지 옵션
                                                                                                                            // - ANNOTATION : 기본값. 어노테이션을 인식해서 동작한다.
                                                                                                                            // - ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작한다.
                                                                                                                            // - ASPECTJ : AspectJ 패턴 사용
                                                                                                                            // - REGEX : 정규표현식// - CUSTOM : TypeFilter라는 인터페이스를 구현하여 사용
                                                                                                                            // - CUSTOM : TypeFilter라는 인터페이스를 구현하여 사용
            // 예를 들어, 내가 원하는 빈을 특정해서 빼고 싶다면 아래와 같이 사용할 수 있다.
//          excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class)
    )
    static class ComponentFilterAppConfig {

    }
}

