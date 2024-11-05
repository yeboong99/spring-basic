package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    // 생성자에 @Autowired 추가
    // 생성자에 @Autowired를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
    // 이 때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.
        // getBean(MemberRepository.class)와 동일하다고 이해하면 된다.
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // AppConfig의 싱글톤 테스트 용도. test/singleton/ConfigurationSingletonTest
    // (과연 memberService에서 생성되는 memberRepository객체와
    //      orderService에서 생성되는 memberRepository 객체가 싱글톤, 즉 메모리 주소값이 같을까?)
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
