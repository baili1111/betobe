package com.sikiedu.betobe.repository;

		import com.sikiedu.betobe.domain.User;
		import org.springframework.data.jpa.repository.Modifying;
		import org.springframework.data.jpa.repository.Query;
		import org.springframework.data.repository.CrudRepository;

		import java.math.BigInteger;
		import java.util.List;

/**
 * @author zhu
 * @date 2021/4/6 21:29:02
 * @description
 */
public interface UserRepository extends CrudRepository<User, Long> {

	@Query(value = "select * from user where username = ?1", nativeQuery = true)
	User findUserByUsername(String phone);

	@Query(value = "select * from user where phone = ?1", nativeQuery = true)
	User findUserByPhone(String phone);

	@Query(value = "update user set password = ?3 where phone = ?1 and email = ?2", nativeQuery = true)
	@Modifying
	void changeUserPasswordByPhoneAndEmail(String phone, String email, String password);

	@Query(value = "select user_id from user_follow where follow_id = ?", nativeQuery = true)
	List<BigInteger> findFollowersListById(Long id);

	@Query(value = "select count(*) from user_follow where follow_id = ?", nativeQuery = true)
	Integer findFollowersNumById(String id);

	@Query(value = "select * from user u inner join user_follow f on u.id = f.user_id where f.follow_id = ?3 limit ?1, ?2", nativeQuery = true)
	List<User> getUserFollowerPageListByUserId(Integer start, Integer pageSize, String userId);
}
