package com.sikiedu.betobe.utils;

import com.sikiedu.betobe.domain.SubCategory;
import com.sikiedu.betobe.domain.User;

import java.util.Set;
import java.util.function.Function;

/**
 * @author zhu
 * @date 2021/4/29 16:53:10
 * @description
 */
public class MySetUtils {

	// 在set集合中判断是否存在User对象
	public boolean hasUser(Set<User> set, User user) {

		//System.out.println("setUtils");
		boolean b = set.stream().anyMatch(s -> s.getUsername().equals(user.getUsername()));

		return b;
/*
		for (User u : set) {
			if (u.getUsername().equals(user.getUsername())) {
				return true;
			}
		}
		return false;*/
	}

	// 返回Set集合中的id和subCategory
	public String getSubCategory(Set<SubCategory> set) {

		StringBuilder target = new StringBuilder();

		for (SubCategory subCategory : set) {

			target.append(subCategory.getId());
			target.append(",");
			target.append(subCategory.getSubCategory());
			target.append("|");
		}
		// 1,MAD|2,MMD|3,Short|4,Other|
		if (target.lastIndexOf("|") != -1) {
			target.deleteCharAt(target.lastIndexOf("|"));
		}

		return target.toString();

	}
}
