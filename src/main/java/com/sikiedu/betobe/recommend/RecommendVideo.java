package com.sikiedu.betobe.recommend;

import com.sikiedu.betobe.domain.Tag;
import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.service.UserService;
import com.sikiedu.betobe.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhu
 * @date 2021/5/8 16:22:09
 * @description
 */
@Component
public class RecommendVideo {

	@Autowired
	private RecommendByContentUtils recommendByContentUtils;

	@Autowired
	private VideoService videoService;

	@Autowired
	private UserService userService;

	//得到推荐的视频
	public List<Video> getRecommendVideoByContent(String userId, int videoNum) {
		//拿到所有的视频
		List<Video> videoList = videoService.findAllVideo();
		//针对用户推荐
		User user = userService.findUserById(userId);
		List<PVideo> target = new ArrayList<>();
		//如果视频没有被用户点赞或者踩，则可以推荐
		for(Video video:videoList) {
			//用户没有点赞或者踩这个视频
			if(user.getAgreeVideos().contains(video) || user.getDisagreeVideos().contains(video)) {
				continue;
			}
			Double pLike = PUserLikeThisVideo(video, userId);
			Double pDislike = PUserDislikeThisVideo(video, userId);
			//不喜欢比喜欢概率还要大,continue
			if(pDislike > pLike) {
				continue;
			}
			//放入Target中
			PVideo pVideo = new PVideo();
			pVideo.setpLike(pLike);
			pVideo.setpDislike(pDislike);
			pVideo.setVideo(video);
			target.add(pVideo);
		}

		//根据用户喜欢视频的概率进行排序
		PVideo pv[] = {};
		pv = target.toArray(pv);
		Arrays.sort(pv);
		List<Video> recommendList = new ArrayList<>();
		//获得前6名
		int count = pv.length;
		if(pv.length >= videoNum) {
			count = videoNum;
		}
		for (int i = 0; i < count; i++) {
			//System.out.println(pv[i].getpLike());
			recommendList.add(pv[i].getVideo());
		}
		//返回
		return recommendList;
	}

	// 使用基于内容的推荐 - 用户喜欢视频的概率
	public Double PUserLikeThisVideo(Video video, String userId) {
		// 用户喜欢一个视频的概率
		Double pUserLikeThisVideo = recommendByContentUtils.PUserLikeVideo(userId);
		Set<Tag> tags = video.getVideoTags();
		for (Tag tag : tags) {
			pUserLikeThisVideo *= recommendByContentUtils.PUserTargetLike(userId, tag.getTag());
		}
		return pUserLikeThisVideo;
	}

	// 使用基于内容的推荐 - 用户不用喜欢视频的概率
	public Double PUserDislikeThisVideo(Video video, String userId) {
		// 用户喜欢一个视频的概率
		Double pUserDislikeThisVideo = recommendByContentUtils.PUserDislikeVideo(userId);
		Set<Tag> tags = video.getVideoTags();
		for (Tag tag : tags) {
			pUserDislikeThisVideo *= recommendByContentUtils.PUserTargetDislike(userId, tag.getTag());
		}
		return pUserDislikeThisVideo;
	}


}
