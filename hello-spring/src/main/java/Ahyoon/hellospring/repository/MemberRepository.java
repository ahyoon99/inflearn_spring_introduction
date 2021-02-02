package Ahyoon.hellospring.repository;

import java.util.List;
import java.util.Optional;

import Ahyoon.hellospring.domain.Member;

public interface MemberRepository {	// 회원을 저장하는 역할은 MemberRepository가 한다. 하지만 구현은 메모리에 한다 or DB랑 연동해서 Jdbc에 한다

	Member save(Member member);
	Optional<Member> findById(Long id);
	Optional<Member> findByName(String name);
	List<Member> findAll();

}
