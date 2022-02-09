package com.sikiedu.betobe.repository;

import com.sikiedu.betobe.domain.Blog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/2 22:10:31
 * @description
 */
public interface BlogRepository extends CrudRepository<Blog, Long> {


	@Query(value = "select count(*) from blog", nativeQuery = true)
	int findAllBlogCount();

	@Query(value = "select * from blog limit ?1, ?2", nativeQuery = true)
	List<Blog> getAllBlogPageList(Integer start, Integer pageSize);
}
