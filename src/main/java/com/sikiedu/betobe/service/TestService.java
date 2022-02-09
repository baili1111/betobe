package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.TestDomain;

/**
 * @author zhu
 * @date 2021/4/3 16:52:46
 * @description
 */
public interface TestService {
	void save(TestDomain testDomain);

	TestDomain find();
}
