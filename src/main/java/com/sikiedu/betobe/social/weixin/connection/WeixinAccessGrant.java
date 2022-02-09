package com.sikiedu.betobe.social.weixin.connection;

import org.springframework.social.oauth2.AccessGrant;

/**
 * @author zhu
 * @date 2021/4/12 14:07:59
 * @description
 */
public class WeixinAccessGrant extends AccessGrant {

	private String openId;

	public WeixinAccessGrant() {
		super("");
	}

	public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
		super(accessToken, scope, refreshToken, expiresIn);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
