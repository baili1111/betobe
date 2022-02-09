package com.sikiedu.betobe.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author zhu
 * @date 2021/4/8 21:54:34
 * @description
 */
// 让我们的配置生效
@EnableConfigurationProperties(SecurityProperties.class)
public class SikieduSecurityConfig {
}
