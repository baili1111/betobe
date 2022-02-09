package com.sikiedu.betobe.repository;

import com.sikiedu.betobe.domain.SubscribeUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zhu
 * @date 2021/5/23 22:30:24
 * @description
 */
public interface SubscribeRepository extends CrudRepository<SubscribeUser, Long> {

	@Query(value = "select * from subscribe_user where email = ?1", nativeQuery = true)
	SubscribeUser findSubscribeByEmail(String email);
}
