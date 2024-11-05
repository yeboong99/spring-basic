package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        // AnnotationConfigApplicationContext를 사용하는 것은 기존과 동일하다.
        // 설정정보로 AutoAppConfig 클래스를 넘겨준다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        // 실행해보면 기존과 같이 잘 동작한다.

        assertThat(memberService).isInstanceOf(MemberService.class);
        // 로그 확인하기 - 컴포넌트 스캔이 잘 동작하여 Rate할인정책, MemberServiceImpl, MemoryMemerREpository, OrderServiceImplr객체가 잘 생성되었는지 확인해보자.
        // 확인할 로그 : classPathBeanDefinitionScanner -- ...
    }

    // @ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록한다.
    // 이 때 스프링 빈의 기본 이름은 클래스명을 사용하되, 맨 앞 글자만 소문자를 사용한다.
        // 빈 이름 기본 전략 : MemberServiceImpl클래스 -> memberServiceImpl
        // 만약 빈 이름을 직접 지정하고 싶으면
        // @Component('memberService2') 이런식으로 이름을 부여할 수 있다.

    // Autowired에 대한 의존관계 자동주입 이해 : MemberServiceImpl의 주석 확인
    // 파라미터가 많은 경우 OrderServiceImpl 주석 확인
}
