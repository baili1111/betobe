package com.sikiedu.betobe.social.qq.config;

import com.sikiedu.betobe.properties.QQProperties;
import com.sikiedu.betobe.properties.SecurityProperties;
import com.sikiedu.betobe.social.qq.connection.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.security.AuthenticationNameUserIdSource;

/**
 * @author zhu
 * @date 2021/4/11 13:38:45
 * @description
 */

@Configuration
@EnableSocial
@Order(2)
public class QQConfig extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	// 添加qq创建的Connection的工厂
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		QQProperties qqProperties = securityProperties.getQqProperties();
		// 创建ConnectionFactory
		QQConnectionFactory connectionFactory = new QQConnectionFactory(
				qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
		// 添加创建ConnectionFactory
		connectionFactoryConfigurer.addConnectionFactory(connectionFactory);
	}


	// 获取登录人
	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}
}
