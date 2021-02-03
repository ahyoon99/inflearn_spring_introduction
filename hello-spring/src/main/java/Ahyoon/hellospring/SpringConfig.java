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

	private EntityManager em;

	@Autowired
	public SpringConfig(EntityManager em) {
		this.em = em;
	}

	@Bean
	public MemberService memberService(){
		return new MemberService(memberRepository());
	}

	@Bean
	public MemberRepository memberRepository(){
		// return new MemoryMemberRepository();
		// return new DBMemberRepository();
		// return new JdbcMemberRepository(dataSource);
		//return new JdbcTemplateMemberRepository(dataSource);
		return new JpaMemberRepository(em);
	}

}
