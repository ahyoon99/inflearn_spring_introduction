package Ahyoon.hellospring.service;

import java.util.List;
import java.util.Optional;

import Ahyoon.hellospring.domain.Member;
import Ahyoon.hellospring.repository.MemberRepository;
import Ahyoon.hellospring.repository.MemoryMemberRepository;

public class MemberService {

	// MemberService.java의 memberRepository와 MemberServiceTest의 memberRepository가 다른 repository라는 문제점이 있다.
	// private final MemberRepository memberRepository = new MemoryMemberRepository();

	// 그래서 13줄 코드를 16~21줄 코드로 바꿔야 한다.
	private final MemberRepository memberRepository;

	// memberService의 입장에서 member repository를 외부에서 넣어준다. -> DI(dependency injection)라고 한다.
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

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
