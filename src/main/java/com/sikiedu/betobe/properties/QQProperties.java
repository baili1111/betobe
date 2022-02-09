package com.sikiedu.betobe.properties;

/**
 * @author zhu
 * @date 2021/4/10 16:40:40
 * @description
 */
public class QQProperties {

	// App唯一标识
	private String appId = "100550231";
	private String appSecret = "69b6ab57b22f3c2fe6a6149274e3295e";
	// QQ供应商
	private String providerId = "callback.do";
	// 拦截器拦截的请求
	private String filterProcessesUrl = "/qqLogin";

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
}
