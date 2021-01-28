package Ahyoon.hellospring.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ahyoon.hellospring.domain.Member;
import Ahyoon.hellospring.repository.MemberRepository;
import Ahyoon.hellospring.repository.MemoryMemberRepository;

class MemberServiceTest {

	// MemberService.java의 memberRepository와 MemberServiceTest의 memberRepository가 다른 repository라는 문제점이 있다.
	// MemberService memberService = new MemberService();
	// MemoryMemberRepository memberRepository = new MemoryMemberRepository();

	// 그래서 17~18줄 코드를 22~29줄에 나와 있는 코드로 바꿔야 한다.

	MemberService memberService;
	MemoryMemberRepository memberRepository;

	@BeforeEach
	public void beforeEach(){
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}

	@AfterEach
	public void afterEach(){

		memberRepository.clearStore();
	}

	@Test
	void 회원가입() {
		// given
		Member member = new Member();
		member.setName("spring");

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
		member1.setName("spring");

		Member member2 = new Member();
		member2.setName("spring");

		// when
		memberService.join(member1);

		// 방법 1
		// try{
		// 	memberService.join(member2);
		// 	fail();	// 이 코드가 실행되면 fail 된 것이다.
		// } catch(IllegalStateException e){
		// 	Assertions.assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
		// }

		// 방법 2
		// assertThrows(IllegalStateException.class, () -> memberService.join(member2));

		// 방법 3
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		Assertions.assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
	}

	@Test
	void findMembers() {
	}

	@Test
	void findOne() {
	}
}