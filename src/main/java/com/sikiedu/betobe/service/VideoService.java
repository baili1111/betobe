package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.utils.PageBean;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/5 16:04:37
 * @description
 */
public interface VideoService {
	/**
	 * 保存一条 video
	 * @param video
	 */
	void saveVideo(Video video);

	/**
	 * 通过id 查找video
	 * @param id
	 * @return
	 */
	Video findVideoById(String id);

	/**
	 * 查询前 count条 video数据
	 * @param count
	 * @return
	 */
	List<Video> findNormalVideo(int count);

	/**
	 * 查找所有video
	 * @return
	 */
	List<Video> findAllVideo();

	/**
	 * 最新视频
	 * @return
	 */
	List<Video> findNewestVideoList(int num);

	/**
	 * 最受欢迎视频
	 * @return
	 */
	List<Video> findPopularVideoList(int num);

	/**
	 * 最受欢迎视频的排序分页
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageBean getPopularVideoListPageBean(Integer currentPage, int pageSize);

	/**
	 * 最新视频排序分页
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageBean getNewestVideoListPageBean(Integer currentPage, int pageSize);

	/**
	 * 用户发布的视频
	 * @param currentPage
	 * @param pageSize
	 * @param id
	 * @return
	 */
	PageBean getNormalPageBeanByUserId(Integer currentPage, int pageSize, String id);

	/**
	 * 通过id删除视频
	 * @param id
	 * @return
	 */
	Video deleteVideoById(String id);

	/**
	 * 分页查询用户喜欢的视频
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	PageBean getNormalUserLikeVideoPageBeanByUserId(Integer currentPage, int pageSize, String userId);

	/**
	 * 用户发布视频的个数
	 * @param userId
	 * @return
	 */
	int getUserSubmissionVideoNum(String userId);

	/**
	 * 用户喜爱视频的个数
	 * @param userId
	 * @return
	 */
	int getUserFavoriteVideoNum(String userId);
}
