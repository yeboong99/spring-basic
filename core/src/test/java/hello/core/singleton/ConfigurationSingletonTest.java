package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }
    // 이 세 가지의 빈이 모두 같은 빈이다.(같은 인스턴스이다.)
    // AppConfig를 보면 싱글톤 설정을 따로 하지 않았는데 자동으로 싱글톤으로 관리되는 것을 볼 수 있다.

    // AppConfig에 soutm(호출 시 로그남김)을 남겨서 확인해보면.. 사실 자바가 실행될 때
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository
    // 이렇게 5줄의 로그가 남아야 할 것 같은데..

    // 실제로 실행해보면
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // 이렇게 세 줄의 로그만 남아 있다. ( memberRepository가 첫 호출 시 한번만 실제로 호출되고 이후에는 호출되지 않음.)

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());    // getClass() : 클래스 타입이 뭔지 확인할 수 있는 함수
        // 순수한 클래스라면 아래와 같이 출력된다.
        // class hello.core.AppConfig

        // 그런데 예상과는 달리 클래스명에 CGLIB가 붙어있다.
        // CGLIB : 바이트코드 조작 라이브러리
        // AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다.
        // 이 다른 클래스가 싱글톤을 보장해주는데, 그 내부 기술은 매우 복잡함..
        // 예상으로는 memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면 기존의 빈을 찾아서 반환, 없다면 생성하는 로직이 있을 것..

        // 만약 이 테스트메서드 configurationDeep을 AppConfig의 @Configuration을 주석처리하고 실행하면 (@Bean 만으로 컨테이너에 적용하면)
        // CGLIB기술 없이 순수하게 실행되면서 싱글톤 보장이 깨져버린다. (memberRepository가 세번 실행되는 걸 확인할 수 있다.)
        // 해당 memoryMemberRepository들도 각각 전부 다른 인스턴스를 갖고 있다.
    }

}
