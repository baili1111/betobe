package com.sikiedu.betobe.social.weixin.config;

import com.sikiedu.betobe.properties.SecurityProperties;
import com.sikiedu.betobe.properties.WeixinProperties;
import com.sikiedu.betobe.social.Binding.SikieduConnectView;
import com.sikiedu.betobe.social.weixin.connection.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.web.servlet.View;

/**
 * @author zhu
 * @date 2021/4/12 15:08:01
 * @description
 */

@Configuration
@EnableSocial
public class WeixinConfiguration extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {

		WeixinProperties weixinProperties = securityProperties.getWeixinProperties();

		WeixinConnectionFactory weixinConnectionFactory = new WeixinConnectionFactory(
				weixinProperties.getProviderId(), weixinProperties.getAppId(), weixinProperties.getAppSecret());

		connectionFactoryConfigurer.addConnectionFactory(weixinConnectionFactory);

	}

	@Bean("connect/weixinConnected")
	public View weixinConnectedView(){
		return new SikieduConnectView();
	}
}
