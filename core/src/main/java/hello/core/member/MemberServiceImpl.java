package hello.core.member;

public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

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
