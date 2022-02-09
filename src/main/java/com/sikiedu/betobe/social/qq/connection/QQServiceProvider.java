package com.sikiedu.betobe.social.qq.connection;

import com.sikiedu.betobe.social.qq.api.QQ;
import com.sikiedu.betobe.social.qq.api.QQImpl;
import com.sikiedu.betobe.social.qq.template.QQOAuth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author zhu
 * @date 2021/4/10 17:27:06
 * @description
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	/*
	 * 用户发送请求，web将用户导向认证服务器的url地址中进行授权，然后返回web一个授权码（Authorization Code），
	 * web使用这个授权码（Authorization Code）去获取认证令牌（Access Token）
	 * 通过令牌（Access Token）去获取用户的openid
	 * OpenID是此网站上或应用中唯一对应用户身份的标识，网站或应用可将此ID进行存储，便于用户下次登录时辨识其身份，
	 * 或将其与用户在网站上或应用中的原有账号进行绑定。
	 *
	 * */

	// 将用户导向认证服务器中url地址，用户在该地址上进行授权
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
	// 在获取到令牌的时候，需要访问的url
	private static final String URL_ACCESSTOKEN = "https://graph.qq.com/oauth2.0/token";


	private String appId;

	public QQServiceProvider(String appId, String appSecret) {
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESSTOKEN));
		this.appId = appId;
	}

	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken, appId);
	}
}
