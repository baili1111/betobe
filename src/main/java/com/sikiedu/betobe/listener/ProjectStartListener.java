package com.sikiedu.betobe.listener;

import com.sikiedu.betobe.domain.SubscribeUser;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.recommend.RecommendEngine;
import com.sikiedu.betobe.search.engine.SearchUtils;
import com.sikiedu.betobe.service.SubscribeService;
import com.sikiedu.betobe.service.VideoService;
import com.sikiedu.betobe.timer.EmailTask;
import com.sikiedu.betobe.utils.UserRecommendVideoEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * @author zhu
 * @date 2021/5/22 10:51:00
 * @description
 */
@Component
public class ProjectStartListener implements ApplicationListener<ContextRefreshedEvent> {


	@Autowired
	private VideoService videoService;

	@Autowired
	private SearchUtils searchUtils;

	@Autowired
	private SubscribeService subscribeService;

	@Autowired
	private RecommendEngine recommendEngine;

	// 在项目启动的时候会调用的函数
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//System.out.println("项目启动");

		// 获得词典 压缩倒排索引 --> 放置磁盘 --> 解压文件 --> 读取数据
		List<Video> videos = videoService.findAllVideo();
		for (Video video : videos) {
			try {
				searchUtils.getLexicon(video);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 如果更新词典

		// 视频增删改，更新词典

		// 给订阅了网站的人发送邮件
		Timer timer = new Timer();

		new Thread(()->{
			EmailTask emailTask = new EmailTask();
			List<UserRecommendVideoEntry<SubscribeUser, List<Video>>> list = new ArrayList<>();
			List<SubscribeUser> users = subscribeService.findAllSubscribeUsers();

			for (SubscribeUser user : users) {
				List<Video> videoList = recommendEngine.getSubscribeUserRecommendVideo(user);
				list.add(new UserRecommendVideoEntry<>(user, videoList));
			}

			emailTask.setUserEmails(list);
			long time = 30L * 24L * 60L * 60L * 1000L;
			// 任务，任务在第几毫秒之后开始，间隔多少毫秒再次运行
			timer.schedule(emailTask, time, time);

		}).start();

	}
}
