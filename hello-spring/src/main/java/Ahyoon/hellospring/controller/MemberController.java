package Ahyoon.hellospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Ahyoon.hellospring.service.MemberService;

@Controller
public class MemberController {

	//private final MemberService memberService = new MemberService();	// 이렇게 하는 것보단 아래코드를 사용해라!
	private final MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService){
		this.memberService = memberService;
	}

}
