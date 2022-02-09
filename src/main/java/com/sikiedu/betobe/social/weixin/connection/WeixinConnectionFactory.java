package com.sikiedu.betobe.social.weixin.connection;

import com.sikiedu.betobe.social.weixin.api.Weixin;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author zhu
 * @date 2021/4/12 14:56:22
 * @description
 */
public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {

	public WeixinConnectionFactory(String proivderId, String appId, String appSecret) {
		super(proivderId, new WeixinServiceProvider(appId, appSecret), new WeixinAdapter());
	}


	@Override
	public Connection<Weixin> createConnection(AccessGrant accessGrant) {

		return new OAuth2Connection<>(getProviderId(),
				extractProviderUserId(accessGrant),
				accessGrant.getAccessToken(),
				accessGrant.getRefreshToken(),
				accessGrant.getExpireTime(),
				getOAuth2ServiceProvider(),
				getApiAdapter(extractProviderUserId(accessGrant)));

	}

	@Override
	public Connection<Weixin> createConnection(ConnectionData data) {

		return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));

	}

	// 获取openId
	@Override
	protected String extractProviderUserId(AccessGrant accessGrant) {

		if (accessGrant instanceof WeixinAccessGrant) {
			return ((WeixinAccessGrant) accessGrant).getOpenId();
		}

		return null;

	}

	// 返回自己的ServiceProvider
	public OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<Weixin>)getServiceProvider();
	}

	// 返回weixinAdapter
	public ApiAdapter<Weixin> getApiAdapter(String providerUserId) {
		return new WeixinAdapter(providerUserId);
	}

}
