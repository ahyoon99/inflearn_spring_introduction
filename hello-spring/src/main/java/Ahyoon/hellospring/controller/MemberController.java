package Ahyoon.hellospring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import Ahyoon.hellospring.domain.Member;
import Ahyoon.hellospring.service.MemberService;

@Controller
public class MemberController {

	// 1. 필드 주입 방식 : 별로 좋지 않은 방식이다.
	// @Autowired private final MemberService memberService;

	//private final MemberService memberService = new MemberService();	// 이렇게 하는 것보단 아래코드를 사용해라!
	private MemberService memberService;

	// 2. 생성자 주입 방식
	@Autowired
	public MemberController(MemberService memberService){
		this.memberService = memberService;
	}

	// 3. setter 주입 방식 : public이다 보니 중간에 내용이 바뀔 수도 있다. -> 별로 좋지 않은 방식이다.
	// @Autowired
	// public void setMemberService(MemberService memberService){
	// 	this.memberService = memberService;
	// }

	@GetMapping("members/new")	// GetMapping : 조회할 때 사용한다.
	public String createForm(){
		return "members/createMemberForm";
	}

	@PostMapping("/members/new")	// PostMapping : 데이터를 form같은 곳에 넣어서 전달 할 때 사용한다.
	public String create(MemberForm form){
		Member member = new Member();
		member.setName(form.getName());

		// System.out.println("memeber = "+member.getName());	// 확인용 코드

		memberService.join(member);

		return "redirect:/";	// 회원가입이 끝났으니 홈화면으로 돌려버리는 것이다.

	}

	@GetMapping("/members")
	public String list(Model model){
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}
}
