package com.sikiedu.betobe.social.qq.template;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author zhu
 * @date 2021/4/10 17:23:52
 * @description
 */
public class QQOAuth2Template extends OAuth2Template {


	public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		// 让我们的clientid以及clientSecret可以拼接过来
		setUseParametersForClientAuthentication(true);
	}

	// text/html
	@Override
	protected RestTemplate createRestTemplate() {

		RestTemplate template = super.createRestTemplate();
		// 添加text/html
		template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

		return template;
	}

	// 把请求的格式按照qq的标准，做了一些自定义信息
	// 自己处理请求
	// access_token=FE04************************CCE2        items[0]
	// expires_in=7776000       item[0]
	// refresh_token=88E4************************BE14       item[1]
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

		// 拿到这个请求获得的字符串
		String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		// 按照&进行切割

		String[] items = StringUtils.split(responseStr, "&");
		String[] item = StringUtils.split(items[1], "&");

		String access_token = StringUtils.replace(items[0], "access_token=", "");
		Long expires_in = new Long(StringUtils.replace(item[0], "expires_in=", ""));
		String refresh_token = StringUtils.replace(item[1], "refresh_token=", "");

		return new AccessGrant(access_token, null, refresh_token, expires_in);
	}

}
