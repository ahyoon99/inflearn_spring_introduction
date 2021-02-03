package Ahyoon.hellospring;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Ahyoon.hellospring.repository.JdbcMemberRepository;
import Ahyoon.hellospring.repository.JdbcTemplateMemberRepository;
import Ahyoon.hellospring.repository.JpaMemberRepository;
import Ahyoon.hellospring.repository.MemberRepository;
import Ahyoon.hellospring.repository.MemoryMemberRepository;
import Ahyoon.hellospring.service.MemberService;

@Configuration
public class SpringConfig {

	private final MemberRepository memberRepository;

	@Autowired
	public SpringConfig(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Bean
	public MemberService memberService(){
		return new MemberService(memberRepository);
	}

}
