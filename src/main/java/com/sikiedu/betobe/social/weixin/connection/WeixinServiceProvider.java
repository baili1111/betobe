package com.sikiedu.betobe.social.weixin.connection;

import com.sikiedu.betobe.social.weixin.api.Weixin;
import com.sikiedu.betobe.social.weixin.api.WeixinImpl;
import com.sikiedu.betobe.social.weixin.template.WeixinOAuth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * @author zhu
 * @date 2021/4/12 14:45:16
 * @description
 */
public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {

	// 获取授权码
	private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";

	// 获取令牌
	private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";


	public WeixinServiceProvider(String appId, String appSecret) {
		super(new WeixinOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
	}

	@Override
	public Weixin getApi(String accessToken) {
		return new WeixinImpl(accessToken);
	}

}
