package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
*  스프링 컨테이너에 빈으로 4가지 메서드 등록. 기본적으로 빈의 이름은 메서드 이름으로 저장됨.
*  AppConfig, memberService, orderSerivce, memberRepository, discountPolicy 5가지 등록될거임.
* */

@Configuration
public class AppConfig {

    @Bean   // Key로 memberService, Value로 MemberServiceImpl객체로 등록됨.
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean   // Key로 memberRepository, Value로 MemoryMemberRepository객체가 등록됨.
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean   // Key로 orderService, Value로 OrderServiceImpl 객체가 등록됨.
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean   // Key로 discountPolicy, Value로 RateDiscountPolicy 객체가 등록됨.
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
//        return new FixDiscountPolicy();
    }

}
