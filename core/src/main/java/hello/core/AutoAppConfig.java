package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//MemoryMemberRepository, RateDiscountPolicy, MemberServiceImple에 각각 @Component를 추가하여 컴포넌트스캔의 대상이 되게 하고,
//MemberServiceImpl에는 @Autowired를 추가한다.
@ComponentScan(
        //excludeFilters에 포함된 설정정보는 컴포넌트스캔 대상에서 제외된다. (AppConfig, TestConfig 등)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
public class AutoAppConfig {


}
