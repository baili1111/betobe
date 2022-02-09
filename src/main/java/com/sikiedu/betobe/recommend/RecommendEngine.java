package com.sikiedu.betobe.recommend;

import com.sikiedu.betobe.domain.SubscribeUser;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/24 13:25:07
 * @description
 */
@Component
public class RecommendEngine {

	@Autowired
	private VideoService videoService;

	// 根据用户得到推荐的视频
	public List<Video> getSubscribeUserRecommendVideo(SubscribeUser user) {
		List<Video> videoList = videoService.findNewestVideoList(8);
		return videoList;
	}
}
