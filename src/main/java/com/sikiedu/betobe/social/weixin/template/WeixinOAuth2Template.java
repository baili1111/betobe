package com.sikiedu.betobe.social.weixin.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sikiedu.betobe.social.weixin.connection.WeixinAccessGrant;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author zhu
 * @date 2021/4/12 14:06:11
 * @description
 */
/*
	微信特有的令牌，带有openId
	正常的令牌是不带有openId的，所以需要自己实现一个特有的令牌
 */
public class WeixinOAuth2Template extends OAuth2Template {

	private static final String REFERSH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

	private String accessTokenUrl;
	private String clientId;
	private String clientSecret;

	private ObjectMapper objectMapper = new ObjectMapper();

	public WeixinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		this.accessTokenUrl = accessTokenUrl;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	// 用Code换取令牌
	@Override
	public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
		// 获取accessTokenURL
		StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);
		// 拼接参数
		// https://api.weixin.qq.com/sns/oauth2/access_token
		// ?appid=APPID
		accessTokenRequestUrl.append("?appid=" + clientId);
		// &secret=SECRET
		accessTokenRequestUrl.append("&secret=" + clientSecret);
		// &code=CODE
		accessTokenRequestUrl.append("&code=" + authorizationCode);
		// &grant_type=authorization_code
		accessTokenRequestUrl.append("&grant_type=authorization_code");
		// &redirect_uri=
		accessTokenRequestUrl.append("&redirect_uri=" + redirectUri);

		return getAccessToken(accessTokenRequestUrl);
	}

	// 发送请求，获取令牌
	private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {

		String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);
		/*
			{
				"access_token":"ACCESS_TOKEN",
				"expires_in":7200,
				"refresh_token":"REFRESH_TOKEN",
				"scope":"SCOPE",
				"unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
				"openid":"OPENID",
			}
		*/
		Map result = null;
		try {
			result = objectMapper.readValue(response, Map.class);
		} catch (IOException e) {
			throw new RuntimeException("微信登录中，获取accessToken失败");
		}

		WeixinAccessGrant accessToken = new WeixinAccessGrant(
				(String)result.get("access_token"),
				(String)result.get("scope"),
				(String)result.get("refresh_token"),
				new Long((Integer)result.get("expires_in")));

		accessToken.setOpenId((String)result.get("openid"));

		return accessToken;
	}

	// 解决收到的字符串是乱码
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate template = super.createRestTemplate();
		template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

		return template;
	}

	// 当令牌失效之后，会重新授权
	@Override
	public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {

		StringBuilder refreshTokenUrl = new StringBuilder(REFERSH_TOKEN_URL);
		// ?apppid=APPId
		refreshTokenUrl.append("?appid=" + clientId);
		// &grant_type=refresh_token
		refreshTokenUrl.append("&grant_type=refresh_token");
		// &refresh_token=REFRESH_TOKEN
		refreshTokenUrl.append("&refresh_token=" + refreshToken);

		return getAccessToken(refreshTokenUrl);
	}

	@Override
	public String buildAuthorizeUrl(OAuth2Parameters parameters) {
		return buildAuthenticateUrl(parameters);
	}

	@Override
	public String buildAuthenticateUrl(OAuth2Parameters parameters) {
		String url = super.buildAuthenticateUrl(parameters);
		url = url + "&appid=" + clientId + "&scope=snsapi_login";
		return url;
	}
}
