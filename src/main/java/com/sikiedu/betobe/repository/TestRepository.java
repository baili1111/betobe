package com.sikiedu.betobe.repository;

import com.sikiedu.betobe.domain.TestDomain;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zhu
 * @date 2021/4/3 16:53:50
 * @description
 */

public interface TestRepository extends CrudRepository<TestDomain, Long> {
}
