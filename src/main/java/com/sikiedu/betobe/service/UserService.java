package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.utils.PageBean;

import java.util.List;

/**
 * @author zhu
 * @date 2021/4/6 21:27:16
 * @description
 */
public interface UserService {

	/**
	 * 通过 username 查找用户
	 * @param username 手机号
	 * @return User对象 或者 null
	 * */
	User findUserByUsername(String username);

	/**
	 * 保存 user
	 * @param user
	 */
	void saveUser(User user);

	/**
	 * 通过 phone 查找用户
	 * @param phone 手机号
	 * @return user
	 */
	User findUserByPhone(String phone);

	/**
	 * 重置密码功能
	 * 通过手机号和邮箱确定账户，修改密码
	 * @param phone
	 * @param email
	 * @param password
	 */
	void changeUserPasswordByPhoneAndEmail(String phone, String email, String password);

	/**
	 * 根据 用户id 查找用户
	 * @param id
	 * @return
	 */
	User findUserById(String id);

	/**
	 * 根据用户id查找该用户被谁关注着
	 * @param id database follow_id
	 *           user_id 关注 follow_id
	 * @return 查出user_id，在根据user_id去找user，然后返回List<User>
	 */
	List<User> findFollowersListById(String id);

	/**
	 * 查看当前用户有几个人关注他
	 * @param id database follow_id
	 *           user_id 关注 follow_id
	 * @return
	 */
	Integer findFollowersNumById(String id);

	/**
	 * 分页查询粉丝
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	PageBean getUserFollowerPageBeanByUserId(Integer currentPage, int pageSize, String userId);
}
