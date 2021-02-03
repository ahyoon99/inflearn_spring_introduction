package Ahyoon.hellospring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)	// db가 알아서 id 1씩 증가시키면서 생성해준다.
	private long id;

	// @Column(name="username")	// db의 column명이 username이라면 이 코드를 쓴다. -> 매핑이 된다.
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
