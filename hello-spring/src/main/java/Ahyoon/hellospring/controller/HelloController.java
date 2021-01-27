package Ahyoon.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("hello")	// url 뒤에 hello가 오면 아래 코드 실행해준다.
	public String hello(Model model){
		model.addAttribute("data","hello!!!");
		return "hello";	// hello.html을 찾아서 랜더링 해준다.
	}

}
