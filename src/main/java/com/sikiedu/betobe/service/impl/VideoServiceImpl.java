package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.repository.VideoRepository;
import com.sikiedu.betobe.service.VideoService;
import com.sikiedu.betobe.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author zhu
 * @date 2021/5/5 16:04:56
 * @description
 */
@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoRepository videoRepository;

	@Override
	public void saveVideo(Video video) {
		videoRepository.save(video);
	}

	@Override
	public Video findVideoById(String id) {
		return videoRepository.findById(new Long(id)).get();
	}

	@Override
	public List<Video> findNormalVideo(int count) {
		return videoRepository.findNormalVideo(count);
	}

	@Override
	public List<Video> findAllVideo() {
		return (List<Video>) videoRepository.findAll();
	}

	@Override
	public List<Video> findNewestVideoList(int num) {
		return videoRepository.findNewestVideoList(num);
	}

	@Override
	public List<Video> findPopularVideoList(int num) {
		return videoRepository.findPopularVideoList(num);
	}

	@Override
	public PageBean getPopularVideoListPageBean(Integer currentPage, int pageSize) {

		// 准备参数，totalCount
		int totalCount = videoRepository.getVideoTotalCount();
		// 封装pageBean
		PageBean pageBean = new PageBean(currentPage, totalCount, pageSize);
		// 封装pageBean里面的list
		List<Video> list = videoRepository.getVideoPageListByPopular(pageBean.getStart(), pageBean.getPageSize());
		pageBean.setList(list);

		return pageBean;
	}

	@Override
	public PageBean getNewestVideoListPageBean(Integer currentPage, int pageSize) {
		// 准备参数，totalCount
		int totalCount = videoRepository.getVideoTotalCount();
		// 封装pageBean
		PageBean pageBean = new PageBean(currentPage, totalCount, pageSize);
		// 封装pageBean里面的list
		List<Video> list = videoRepository.getVideoPageListByNewest(pageBean.getStart(), pageBean.getPageSize());
		pageBean.setList(list);

		return pageBean;
	}

	@Override
	public PageBean getNormalPageBeanByUserId(Integer currentPage, int pageSize, String id) {

		// 准备参数，totalCount
		int totalCount = videoRepository.getVideoTotalCountByUserId(id);
		// 封装pageBean
		PageBean pageBean = new PageBean(currentPage, totalCount, pageSize);
		// 封装pageBean里面的list
		List<Video> list = videoRepository.getVideoPageListByUserId(pageBean.getStart(), pageBean.getPageSize(), id);
		pageBean.setList(list);

		return pageBean;
	}

	@Override
	public Video deleteVideoById(String id) {

		Optional<Video> byId = videoRepository.findById(new Long(id));
		videoRepository.deleteById(new Long(id));
		return byId.get();
	}

	@Override
	public PageBean getNormalUserLikeVideoPageBeanByUserId(Integer currentPage, int pageSize, String userId) {
		// 准备参数，totalCount，该用户喜欢的视频个数
		int totalCount = videoRepository.getUserLikeVideoTotalByUserId(userId);
		// 封装pageBean
		PageBean pageBean = new PageBean(currentPage, totalCount, pageSize);
		// 封装pageBean里面的list
		List<Video> list = videoRepository.getUserLikeVideoPageListByUserId(pageBean.getStart(), pageBean.getPageSize(), userId);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public int getUserSubmissionVideoNum(String userId) {
		return videoRepository.getVideoTotalCountByUserId(userId);
	}

	@Override
	public int getUserFavoriteVideoNum(String userId) {
		return videoRepository.getUserLikeVideoTotalByUserId(userId);
	}


}
