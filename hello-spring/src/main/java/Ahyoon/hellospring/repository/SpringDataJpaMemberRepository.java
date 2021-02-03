package Ahyoon.hellospring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Ahyoon.hellospring.domain.Member;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>,MemberRepository {

	@Override
	Optional<Member> findByName(String name);	// select m from Member m where m.name = ? 으로 쿼리가 짜진다.

	// + 예시
	// Optional<Member> findByNameAndId(String name, String id); // 인터페이스 이름만으로도 개발이 끝난 것이다.

}
