package com.sikiedu.betobe.repository;

import com.sikiedu.betobe.domain.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zhu
 * @date 2021/5/2 16:07:39
 * @description
 */
public interface TagRepository extends CrudRepository<Tag, Long> {

	@Query(value = "select * from tag where tag = ?1", nativeQuery = true)
	Tag findTagByTag(String tagString);
}
