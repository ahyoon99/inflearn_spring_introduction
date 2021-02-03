package Ahyoon.hellospring.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import Ahyoon.hellospring.domain.Member;
import Ahyoon.hellospring.repository.MemberRepository;
import Ahyoon.hellospring.repository.MemoryMemberRepository;

@SpringBootTest	// 이 어노테이션을 testcase에 달면 테스트를 실행할 때 진짜 spring을 띄어서 test하는 것이다.
@Transactional	// 이 어노테이션을 testcase에 달면 테스트를 실행할 때마다 트랜젝션을 먼저 실행하고 DB에 data를 insert 해준 다음에 끝나면 rollback해준다.
				// 그래서 DB에 넣어진 데이터가 모두 지워진다. 즉, DB에 데이터가 반영이 안된다.
				// 추가적으로, 이 어노테이션이 Test가 아닌 서비스(예시)같은 곳에 달리면 rollback하지 않고 정상적으로 돈다.
class MemberServiceIntegrationTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	@Test
	// @Commit	// @Transactional이 있어도 test후에 데이터가 commit된다. 즉, 데이터가 rollback 되지 않는다.
	void 회원가입() {
		// given
		Member member = new Member();
		member.setName("spring99");

		// when
		Long saveId = memberService.join(member);

		// then
		Member findMember = memberService.findOne(saveId).get();
		Assertions.assertEquals(member.getName(), findMember.getName());
	}

	@Test
	public void 중복_회원_예외(){
		// given
		Member member1 = new Member();
		member1.setName("spring98");

		Member member2 = new Member();
		member2.setName("spring98");

		// when
		memberService.join(member1);

		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		Assertions.assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
	}


}