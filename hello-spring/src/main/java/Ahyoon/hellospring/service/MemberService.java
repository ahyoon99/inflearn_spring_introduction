package Ahyoon.hellospring.service;

import java.util.List;
import java.util.Optional;

import Ahyoon.hellospring.domain.Member;
import Ahyoon.hellospring.repository.MemberRepository;
import Ahyoon.hellospring.repository.MemoryMemberRepository;

public class MemberService {

	private final MemberRepository memberRepository = new MemoryMemberRepository();

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