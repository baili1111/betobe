package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.repository.UserRepository;
import com.sikiedu.betobe.service.UserService;
import com.sikiedu.betobe.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhu
 * @date 2021/4/6 21:27:32
 * @description
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User findUserByPhone(String phone) {
		return userRepository.findUserByPhone(phone);
	}

	@Override
	@Transactional
	public void changeUserPasswordByPhoneAndEmail(String phone, String email, String password) {
		userRepository.changeUserPasswordByPhoneAndEmail(phone, email, password);
	}

	@Override
	public User findUserById(String id) {
		return userRepository.findById(new Long(id)).get();
	}

	@Override
	public List<User> findFollowersListById(String id) {
		List<BigInteger> userIdList = userRepository.findFollowersListById(new Long(id));

		List<User> userList = new ArrayList<>();

		for (BigInteger longId : userIdList) {
			User user = userRepository.findById(longId.longValue()).get();
			userList.add(user);
		}

		return userList;
	}

	@Override
	public Integer findFollowersNumById(String id) {
		return userRepository.findFollowersNumById(id);
	}

	@Override
	public PageBean getUserFollowerPageBeanByUserId(Integer currentPage, int pageSize, String userId) {

		int totalCount = userRepository.findFollowersNumById(userId);
		PageBean pageBean = new PageBean(currentPage, totalCount, pageSize);

		List<User> list = userRepository.getUserFollowerPageListByUserId(pageBean.getStart(), pageBean.getPageSize(), userId);
		pageBean.setList(list);

		return pageBean;
	}
}
