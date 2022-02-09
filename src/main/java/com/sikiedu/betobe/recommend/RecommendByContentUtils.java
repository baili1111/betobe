package com.sikiedu.betobe.recommend;

import com.sikiedu.betobe.domain.Tag;
import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.service.TagService;
import com.sikiedu.betobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zhu
 * @date 2021/5/8 14:26:13
 * @description
 */
@Component
public class RecommendByContentUtils {

	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;


	// 用户喜欢一个视频的概率
	public Double PUserLikeVideo(String userId) {
		// 用户喜欢视频个数 / (用户喜欢视频个数 + 用户不喜欢视频个数) | (视频的总个数)
		User user = userService.findUserById(userId);
		int likeVideo = user.getAgreeVideos().size();
		int dislikeVideo = user.getDisagreeVideos().size();

		if (likeVideo + dislikeVideo == 0 || likeVideo == 0) {
			return 0.5D;
		}

		return 1.0 * likeVideo / (likeVideo + dislikeVideo);
	}


	// 获得用户喜欢一个标签的概率
	public Double PUserTargetLike(String userId, String target){

		// 套公式
		Double answer = 1.0 * ((CountTermsUserLike(userId, target)) + 1) / AllTermsUserLikeCount(userId) + getAllTagsCount();

		//answer = 1.0 * (CountTerms+1) / AllTerms + |V|

		return answer;

	}

	// |V| 为所有文档中出现的不同词的数量
	private Long getAllTagsCount() {

		return (long)tagService.findAllTag().size();
	}

	// AllTerms 返回c类文档中所有的词数量
	private Long AllTermsUserLikeCount(String userId) {
		// 喜欢的文档中所有的词数量
		return userLikeDocsCount(userId);
	}

	// 返回该用户喜欢的文档（视频）中所有的词数量（标签）
	private long userLikeDocsCount(String userId) {
		long target = 0L;
		// 用户->喜欢的视频->标签

		// 拿到用户
		User user = userService.findUserById(userId);

		// 拿到用户下面的所有喜欢的视频
		Set<Video> videos = user.getAgreeVideos();

		// 遍历视频
		for (Video video : videos) {
			// 重复的也要计算
			/*
			for (Tag videoTag : video.getVideoTags()) {
				target++;
			}
			*/
			target += video.getVideoTags().size();
		}
		return target;
	}

	// CountTerms 返回词i在c类文档中出现的次数
	private Long CountTermsUserLike(String userId, String target) {
		// target在喜欢的文档中出现的数量
		return targetInLikeDocsCount(userId, target);
	}

	// 返回该字符串在用户喜欢的文档（视频）中出现的次数
	private long targetInLikeDocsCount(String userId, String target) {
		long count = 0L;
		// 拿到用户
		User user = userService.findUserById(userId);

		// 拿到用户喜欢的视频的集合
		Set<Video> videos = user.getAgreeVideos();

		// 遍历集合，拿到用户喜欢的视频中所有的标签
		for (Video video : videos) {
			Set<Tag> tags = video.getVideoTags();
			// 如果标签中的tag和传入的target相同，计数器++
			for (Tag tag : tags) {
				if (tag.getTag().toUpperCase().equals(target.toUpperCase())) {
					count++;
				}
			}
		}

		return count;
	}

	/**************************************不喜欢********************************************/


	// 用户不喜欢一个视频的概率
	public Double PUserDislikeVideo(String userId) {
		// 用户喜欢视频个数 / (用户喜欢视频个数 + 用户不喜欢视频个数) | (视频的总个数)
		User user = userService.findUserById(userId);
		int likeVideo = user.getAgreeVideos().size();
		int dislikeVideo = user.getDisagreeVideos().size();

		if (likeVideo + dislikeVideo == 0 || dislikeVideo == 0) {
			return 0.5D;
		}

		return 1.0 * dislikeVideo / (likeVideo + dislikeVideo);
	}

	// 用户不喜欢一个标签的概率
	public Double PUserTargetDislike(String userId, String target) {

		Double answer = 1.0 * (CountTermsUserDislike(userId, target) + 1) / (AllTermsUserDislikeCount(userId) + getAllTagsCount());

		//answer = 1.0 * (CountTerms+1) / AllTerms + |V|
		return answer;
	}

	// AllTerms 返回c类文档中所有的词数量
	private long AllTermsUserDislikeCount(String userId) {
		// 不喜欢的文档中所有的词数量
		return userDislikeDocsCount(userId);
	}

	// 不喜欢的文档中所有的词数量
	private long userDislikeDocsCount(String userId) {

		long target = 0L;
		// 拿到用户
		User user = userService.findUserById(userId);

		// 拿到用户下面的所有不喜欢的视频
		Set<Video> videos = user.getDisagreeVideos();

		// 遍历视频
		for (Video video : videos) {
			// 遍历视频下的标签
			target += video.getVideoTags().size();
 		}

		return target;
	}

	// CountTerms 返回词i在c类文档中出现的次数
	private long CountTermsUserDislike(String userId, String target) {

		// target在不喜欢的文档中出现的数量

		return targetInDislikeDocs(userId, target);
	}

	// 返回该字符串target在用户不喜欢的文档中出现的次数
	private long targetInDislikeDocs(String userId, String target) {
		long count = 0L;

		// 拿到用户
		User user = userService.findUserById(userId);

		// 拿到用户不喜欢的视频的集合
		for (Video video : user.getDisagreeVideos()) {
			Set<Tag> tags = video.getVideoTags();
			// 遍历视频的所有标签
			for (Tag tag : tags) {
				// 如果标签中的tag和传入的target相同，计数器++
				if (tag.getTag().toUpperCase().equals(target.toUpperCase())) {
					count++;
				}
			}
		}

		return count;
	}

}
