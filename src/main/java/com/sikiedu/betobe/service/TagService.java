package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.Tag;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/2 16:05:28
 * @description
 */
public interface TagService {

	/**
	 * 通过tag名字查找 tag
	 * @param tagString
	 * @return
	 */
	Tag findTagByTag(String tagString);

	/**
	 * 保存一条tag
	 * @param tag
	 * @return
	 */
	Tag saveTag(Tag tag);

	/**
	 * 查找所有的tag并返回
	 * @return
	 */
	List<Tag> findAllTag();
}
