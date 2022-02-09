package com.sikiedu.betobe.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author zhu
 * @date 2021/4/3 16:47:57
 * @description
 */
@Entity
public class TestDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String str;

	protected TestDomain() {
	}

	public TestDomain(Long id, String str) {
		this.id = id;
		this.str = str;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
