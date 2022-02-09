package com.sikiedu.betobe.social.qq.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author zhu
 * @date 2021/4/11 14:08:41
 * @description
 */
public class SikieduSpringSocialConfigurer extends SpringSocialConfigurer {

	private String filterProcessesUrl;

	public SikieduSpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	// 将默认的拦截改为qqLogin
	@Override
	protected <T> T postProcess(T object) {
		// 获得filter
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		// 设置字段
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return (T) filter;
	}
}
