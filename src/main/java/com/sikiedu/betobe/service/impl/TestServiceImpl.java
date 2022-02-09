package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.TestDomain;
import com.sikiedu.betobe.repository.TestRepository;
import com.sikiedu.betobe.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhu
 * @date 2021/4/3 16:53:05
 * @description
 */
@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestRepository testRepository;

	@Override
	public void save(TestDomain testDomain) {
		testRepository.save(testDomain);
	}

	@Override
	public TestDomain find() {
		return testRepository.findById(2L).get();
	}
}
