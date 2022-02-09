package com.sikiedu.betobe.config;

import com.sikiedu.betobe.thymeleaf.utils.SetDialect;
import com.sikiedu.betobe.utils.BaseDataUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhu
 * @date 2021/4/24 13:35:26
 * @description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * 用户访问该路径下的文件，就会去配置的本地资源中寻找
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 让springboot知道我们的资源文件夹
		registry.addResourceHandler("/upload/**")
				.addResourceLocations("file:D:/11111/IdeaProjects/betobe/out/production/classes/static/upload/");

	}

	// 扩大session的范围
	@Bean
	public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
		return new OpenEntityManagerInViewFilter();
	}

	// 配置方言对象
	@Bean
	@ConditionalOnMissingBean
	public SetDialect mySetDialect() {
		return new SetDialect("setUtils");
	}

	@Bean
	public BaseDataUtils baseDataUtils() {
		return new BaseDataUtils();
	}
}
