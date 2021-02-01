package Ahyoon.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Ahyoon.hellospring.repository.MemberRepository;
import Ahyoon.hellospring.repository.MemoryMemberRepository;
import Ahyoon.hellospring.service.MemberService;

@Configuration
public class SpringConfig {

	@Bean
	public MemberService memberService(){
		return new MemberService(memberRepository());
	}

	@Bean
	public MemberRepository memberRepository(){
		return new MemoryMemberRepository();
		// return new DBMemberRepository();
	}

}
