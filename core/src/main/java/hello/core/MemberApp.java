package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // 스프링 컨테이너인 AplicationContext를 생성함. DI컨테이너의 역할을 하며, AppConfig의 어노테이션을 기반으로 한 빈들을 가져옴.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 키의 이름인 memberService를 가져와 반환타입인 MemberService에 가져온 객체를 담는 코드.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);


        //아래 코드들(로직)을 수정할 필요 없음. 스프링 컨테이너를 통해 가져온 객체를 그대로 이용함.
        Member member = new Member(1L, "memberA", Grade.VIP);

        memberService.join(member);


        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
