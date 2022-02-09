package com.sikiedu.betobe.social.weixin.connection;

import com.sikiedu.betobe.social.weixin.api.Weixin;
import com.sikiedu.betobe.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author zhu
 * @date 2021/4/12 14:54:13
 * @description
 */

/*
	多例的形式，每个用户都有自己的weixinAdapter
	微信api适配器，目的是将weixinUserInfo的信息，适配到connection中
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {


	private String openId;

	public WeixinAdapter() {
	}

	public WeixinAdapter(String openId){
		this.openId = openId;
	}

	@Override
	public boolean test(Weixin api) {
		return true;
	}

	@Override
	public void setConnectionValues(Weixin api, ConnectionValues values) {
		WeixinUserInfo weixinUserInfo = api.getUserInfo(openId);
		values.setDisplayName(weixinUserInfo.getNickname());
		values.setProviderUserId(weixinUserInfo.getOpenid());
		values.setImageUrl(weixinUserInfo.getHeadimgurl());
	}

	@Override
	public UserProfile fetchUserProfile(Weixin api) {
		return null;
	}

	@Override
	public void updateStatus(Weixin api, String message) {

	}
}
