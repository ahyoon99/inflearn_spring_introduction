package Ahyoon.hellospring.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Ahyoon.hellospring.domain.Member;

class MemoryMemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();

	@AfterEach
	public void afterEach(){
		repository.clearStore();
	}

	@Test
	public void save(){

		Member member = new Member();
		member.setName("spring");

		repository.save(member);

		Member result = repository.findById(member.getId()).get();
		// 검증 방법 1
		// System.out.println("result = "+(result == member));

		// 검증 방법 2
		Assertions.assertEquals(member, result);

		// 검증 방법 3
		// Assertions.assertThat(member).isEqualTo(result);

		// 검증 방법 4 : 방법 3에서 static import를 해준다.
		// assertThat(member).isEqualTo(result);

	}

	@Test
	public void findByName(){

		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		Member result = repository.findByName("spring1").get();

		Assertions.assertEquals(member1, result);

	}

	@Test
	public void findAll(){
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		List<Member> result = repository.findAll();

		Assertions.assertEquals(2, result.size());

	}
}
