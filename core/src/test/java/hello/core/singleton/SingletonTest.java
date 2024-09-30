package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링이 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 1. 조회 : 호출할 때마다 객체 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 : 호출할 때마다 객체 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조 값이 다른가 ?
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletoneServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);;

        // assertThat의 isSameAs는 인스턴스 자체가 같은지 확인한다.
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        // 2. 조회
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조 값이 다른가 ?
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // assertThat의 isSameAs로 검증
        Assertions.assertThat(memberService1).isSameAs(memberService2);

    }

    // 싱글톤을 스프링 컨테이너로 빈을 통해 관리. 두 번 조회할 때 같은 객체를 가져다 사용함. 메모리 주소도 같은 것을 확인할 수 있음.
    // 이미 만들어진 객체를 공유하여 사용하는 모습. (효율적 재사용)

    // 참고 : 스프링의 기본 빈 등록 방식은 싱글톤임. 싱글톤 방식만 지원하는 것은 아님. 요청할 때마다 새로운 객체를 생성해서 사용할 수도 있는데, 이는 빈 스코프에서 다룸.

}
