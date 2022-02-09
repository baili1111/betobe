package com.sikiedu.betobe.timer;

import com.sikiedu.betobe.domain.SubscribeUser;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.utils.SendEmailManager;
import com.sikiedu.betobe.utils.UserRecommendVideoEntry;

import java.util.List;
import java.util.TimerTask;

/**
 * @author zhu
 * @date 2021/5/24 12:49:17
 * @description
 */
// 继承TimerTask，Java自带的定时调度系统
public class EmailTask extends TimerTask {

	private List<UserRecommendVideoEntry<SubscribeUser, List<Video>>> userEmails;

	// 定时器要执行的内容
	@Override
	public void run() {
		// 发送邮件

		// 获取所有关注了我们的人
		for (int i = 0; i < userEmails.size(); i++) {

			String emailAdr = userEmails.get(i).getKey().getEmail();
			List<Video> userVideoList = userEmails.get(i).getValue();
			// 给所有关注了我们的人发送邮件
			String title = "来自xxx的推送视频";
			String content = "您好：<br/><br/><p>根据我们对您的了解，给您推荐的视频如下：</p><br/>";
			for (int j = 0; j < userVideoList.size(); j++) {
				content += "<a href='http://www.pinzhi365.com/findVideoById?id='"+userVideoList.get(j).getId()+">"+userVideoList.get(j).getTitle()+"</a><br/>";
			}
			// 邮件的内容是针对于各个用户推荐的视频（每个用户不止推荐一个视频）
			SendEmailManager sendEmailManager = new SendEmailManager(emailAdr, title, content);
			sendEmailManager.start();
		}



	}

	public List<UserRecommendVideoEntry<SubscribeUser, List<Video>>> getUserEmails() {
		return userEmails;
	}

	public void setUserEmails(List<UserRecommendVideoEntry<SubscribeUser, List<Video>>> userEmails) {
		this.userEmails = userEmails;
	}
}
