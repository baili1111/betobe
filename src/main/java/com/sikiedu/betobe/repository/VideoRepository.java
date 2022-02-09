package com.sikiedu.betobe.repository;

import com.sikiedu.betobe.domain.Video;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/5 16:05:43
 * @description
 */
public interface VideoRepository extends CrudRepository<Video, Long> {

	@Query(value = "select * from video limit 0, ?1", nativeQuery = true)
	List<Video> findNormalVideo(int count);

	@Query(value = "select count(*) from video where category_id = ?1", nativeQuery = true)
	int getVideoTotalCountByCategoryId(String categoryId);

	@Query(value = "select * from video where category_id = ?3 limit ?1, ?2", nativeQuery = true)
	List<Video> getVideoPageListByCategoryId(Integer start, Integer pageSize, String categoryId);

	@Query(value = "select * from video order by create_time desc limit 0, ?1", nativeQuery = true)
	List<Video> findNewestVideoList(int num);

	@Query(value = "select * from video order by view_number desc limit 0, ?1", nativeQuery = true)
	List<Video> findPopularVideoList(int num);

	@Query(value = "select count(*) from video where sub_category_id = ?1", nativeQuery = true)
	int getVideoTotalCountBySubCategoryId(String subCategoryId);

	@Query(value = "select * from video where sub_category_id = ?3 limit ?1, ?2", nativeQuery = true)
	List<Video> getVideoPageListBySubCategoryId(Integer start, Integer pageSize, String subCategoryId);

	@Query(value = "select count(*) from video", nativeQuery = true)
	int getVideoTotalCount();

	@Query(value = "select * from video order by view_number limit ?1, ?2", nativeQuery = true)
	List<Video> getVideoPageListByPopular(Integer start, Integer pageSize);

	@Query(value = "select * from video order by create_time desc limit ?1, ?2", nativeQuery = true)
	List<Video> getVideoPageListByNewest(Integer start, Integer pageSize);

	@Query(value = "select count(*) from video where user_id = ?1", nativeQuery = true)
	int getVideoTotalCountByUserId(String id);

	@Query(value = "select * from video where user_id = ?3 limit ?1, ?2", nativeQuery = true)
	List<Video> getVideoPageListByUserId(Integer start, Integer pageSize, String id);

	@Query(value = "select count(*) from video v inner join user_favorite_video u on v.id = video_id where u.user_id = ?1", nativeQuery = true)
	int getUserLikeVideoTotalByUserId(String userId);

	@Query(value = "select * from video v inner join user_favorite_video u on v.id = video_id where u.user_id = ?3 limit ?1, ?2", nativeQuery = true)
	List<Video> getUserLikeVideoPageListByUserId(Integer start, Integer pageSize, String userId);
}
