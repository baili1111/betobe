package com.sikiedu.betobe.social.weixin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author zhu
 * @date 2021/4/12 13:19:05
 * @description
 */
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {

	private static final String URL_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

	private ObjectMapper objectMapper = new ObjectMapper();

	public WeixinImpl(String accessToken) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
	}

	// 目的，发送请求，然后获取weixinUserInfo数据
	@Override
	public WeixinUserInfo getUserInfo(String openId) {

		// 拼接自己的请求
		String url = URL_GET_USERINFO + openId;
		// 发送
		String response = getRestTemplate().getForObject(url, String.class);
		// 拿到返回值，将json转换为对象WeixinUserInfo
		WeixinUserInfo weixinUserInfo = null;

		try {
			weixinUserInfo = objectMapper.readValue(response, WeixinUserInfo.class);
		} catch (IOException e) {
			e.printStackTrace();
		}


		return weixinUserInfo;
	}

	// 解决收到的字符串是乱码
	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		// 乱码是因为默认实现的HttpMessageConverter是ISO-8859-1.而微信给我们的是、UTF-8
		List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
		messageConverters.remove(0);
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

		return messageConverters;
	}

}
