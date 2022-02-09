package com.sikiedu.betobe.social.qq.config;

import com.sikiedu.betobe.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author zhu
 * @date 2021/4/11 13:38:36
 * @description
 */

@EnableSocial
@Configuration
@Order(1)
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private ConnectionSignUp connectionSignUp;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	// 登录之后，直接将QQ的数据保存在数据库中，创建用户
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
				dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setConnectionSignUp(connectionSignUp);
		return repository;
	}


	// 改变拦截的请求 /auth -> /qqLogin
	@Bean
	public SpringSocialConfigurer sikieduSocialSecurityConfig() {
		String filterProcessesUrl = securityProperties.getQqProperties().getFilterProcessesUrl();
		SikieduSpringSocialConfigurer configurer = new SikieduSpringSocialConfigurer(filterProcessesUrl);
		return configurer;
	}

	// 在注册的过程中，拿到了这个SpringSocial中的信息

	// 业务完成之后，把用户的id传给了SpringSocial
	@Bean
	public ProviderSignInUtils providerSignInUtils() {
		return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
	}

	// 打开ConnectController
	@Bean
	public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}
}
