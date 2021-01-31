package Ahyoon.hellospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ahyoon.hellospring.domain.Member;
import Ahyoon.hellospring.repository.MemberRepository;
import Ahyoon.hellospring.repository.MemoryMemberRepository;

@Service	// Service 안에 Component가 들어 있다.
public class MemberService {

	// MemberService.java의 memberRepository와 MemberServiceTest의 memberRepository가 다른 repository라는 문제점이 있다.
	
	// 1번 코드
	// private final MemberRepository memberRepository = new MemoryMemberRepository();

	// 그래서 1번 코드를 2번 코드로 바꿔야 한다.
	/* 2번 코드 시작 */
	private final MemberRepository memberRepository;

	// memberService의 입장에서 member repository를 외부에서 넣어준다. -> DI(dependency injection)라고 한다.
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	/* 2번 코드 끝 */

	/**
	 * 회원가입
	 */
	public Long join(Member member){
		
		validateDuplicateMember(member);	// 같은 이름이 있는 중복 회원 X

		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {	// 같은 이름이 있는 중복 회원 X

		// 방법 1
		// Optional<Member> result = memberRepository.findByName(member.getName());
		// result.ifPresent(m->{
		// 	throw new IllegalStateException("이미 존재하는 회원입니다.");
		// });

		// 방법 2
		memberRepository.findByName(member.getName())
			.ifPresent(m->{
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			});
	}

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}
	
}
