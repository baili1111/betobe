package com.sikiedu.betobe.social.qq.connection;

import com.sikiedu.betobe.social.qq.api.QQ;
import com.sikiedu.betobe.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author zhu
 * @date 2021/4/11 13:29:09
 * @description
 */
public class QQAdapter implements ApiAdapter<QQ> {
	@Override
	public boolean test(QQ api) {
		return true;
	}

	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		// 获取userinfo
		QQUserInfo userInfo = api.getUserInfo();
		// 获取用户名称
		values.setDisplayName(userInfo.getNickname());
		// 获取头像
		values.setImageUrl(userInfo.getFigureurl_qq_1());
		// 获取个人主页
		values.setProfileUrl(null);
		// openid，用户在服务商中的唯一标识
		values.setProviderUserId(userInfo.getOpenId());
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {

	}
}
