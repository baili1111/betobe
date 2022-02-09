package com.sikiedu.betobe.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author zhu
 * @date 2021/4/10 16:25:19
 * @description
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	/*
		https://graph.qq.com/user/get_user_info?
			access_token=*************&
			oauth_consumer_key=12345&
			openid=****************
	*/

	// 获取用户信息的链接
	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

	// 获取openid的链接
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	private String appId;

	private String openId;

	private ObjectMapper objectMapper = new ObjectMapper();

	// 赋值，获取openid
	public QQImpl(String accessToken, String appId) {
		// 自动拼接一个参数，将accessToken给拼接到url上
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		// 赋值appid
		this.appId = appId;
		// 赋值openid
		// 通过url获取openid

		// 拼接参数
		String url = String.format(URL_GET_OPENID, accessToken);

		// 发送请求，获取用户的openid
		String result = getRestTemplate().getForObject(url, String.class);
		// 处理返回值
		// callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		result = StringUtils.replace(result, "callback( ", "");
		result = StringUtils.replace(result, " )", "");

		OpenId id = null;
		try {
			id = objectMapper.readValue(result, OpenId.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 赋值openid
		this.openId = id.getOpenid();
	}


	// 获取用户信息
	@Override
	public QQUserInfo getUserInfo() {

		// 拼接参数
		String url = String.format(URL_GET_USERINFO, appId, openId);
		// 发送请求
		String result = getRestTemplate().getForObject(url, String.class);
		// 处理返回值
		QQUserInfo userInfo = null;
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userInfo;
	}
}
