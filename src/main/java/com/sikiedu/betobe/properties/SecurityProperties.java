package com.sikiedu.betobe.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhu
 * @date 2021/4/8 21:54:07
 * @description
 */
@ConfigurationProperties(prefix = "sikiedu.security")
@Component
public class SecurityProperties {

	private QQProperties qqProperties = new QQProperties();

	private WeixinProperties weixinProperties = new WeixinProperties();

	private int rememberMeSeconds = 36000;

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	public QQProperties getQqProperties() {
		return qqProperties;
	}

	public void setQqProperties(QQProperties qqProperties) {
		this.qqProperties = qqProperties;
	}

	public WeixinProperties getWeixinProperties() {
		return weixinProperties;
	}

	public void setWeixinProperties(WeixinProperties weixinProperties) {
		this.weixinProperties = weixinProperties;
	}
}
