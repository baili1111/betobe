package com.sikiedu.betobe.config;

import com.sikiedu.betobe.handler.LoginFailureHandler;
import com.sikiedu.betobe.handler.LoginSuccessHandler;
import com.sikiedu.betobe.properties.SecurityProperties;
import com.sikiedu.betobe.session.ExpriedSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author zhu
 * @date 2021/4/3 10:42:50
 * @description
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SpringSocialConfigurer springSocialConfigurer;

	// 告诉SpringSecurity密码使用什么方式加密
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private LoginFailureHandler loginFailureHandler;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	@Qualifier("socialUserServiceImpl")
	private UserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 请求授权
		http.formLogin()
				// 自己的登录页面
				.loginPage("/loginBetobe")
				// 自己的表单登录的URL
				.loginProcessingUrl("/loginPage")
				// 登录成功的handler
				.successHandler(loginSuccessHandler)
				// 登录失败的handler
				.failureHandler(loginFailureHandler)
				// 记住我功能
				.and()
				.rememberMe()
				.tokenRepository(persistentTokenRepository())
				// 配置Token过期秒数
				.tokenValiditySeconds(securityProperties.getRememberMeSeconds())
				.userDetailsService(userDetailsService)
				.and()
				.sessionManagement()
				// Session失效
				//.invalidSessionUrl("/session/invalid")
				// 最大Session数量
				.maximumSessions(1)
				// Session过期
				.expiredSessionStrategy(new ExpriedSessionStrategy())
				.and()
				.and()
				.logout()
				.logoutUrl("/signOut")
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
				.authorizeRequests()
				.antMatchers("/forgotPassword", "/loginForgotPass", "/loginRegister", "/judgeSMS", "/sms", "/register", "/loginBetobe",

						// 静态资源
						"/scss/**", "/layerslider/**", "/layer/**", "/js/**", "/images/**", "/fonts/**", "/dist/**", "/css/**",
						"/api/**", "/bower_components/**").permitAll()
				// 所有请求
				.anyRequest()
				// 都需要身份认证
				.authenticated().and()
				// 跨站请求伪造的防护
				.csrf().disable()
				//
				.apply(springSocialConfigurer);

	}
}
