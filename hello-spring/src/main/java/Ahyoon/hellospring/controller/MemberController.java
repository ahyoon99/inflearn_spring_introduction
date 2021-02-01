package Ahyoon.hellospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

}
