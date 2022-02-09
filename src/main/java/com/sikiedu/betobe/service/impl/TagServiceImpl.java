package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.Tag;
import com.sikiedu.betobe.repository.TagRepository;
import com.sikiedu.betobe.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/2 16:05:39
 * @description
 */
@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Override
	public Tag findTagByTag(String tagString) {
		return tagRepository.findTagByTag(tagString);
	}

	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public List<Tag> findAllTag() {
		return (List<Tag>) tagRepository.findAll();
	}
}
