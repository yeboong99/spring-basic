package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;


//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private DiscountPolicy discountPolicy;

    @Autowired  // @Autowired를 이용하면 생성자에서 여러 의존관계도 한번에 주입받을 수 있다.
                // 생성자에 파라미터가 많아도 전부 찾아가서 자동으로 주입한다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // AppConfig의 싱글톤 테스트 용도. test/singleton/ConfigurationSingletonTest
    // (과연 memberService에서 생성되는 memberRepository객체와
    //      orderService에서 생성되는 memberRepository 객체가 싱글톤, 즉 메모리 주소값이 같을까?)
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
