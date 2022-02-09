package com.sikiedu.betobe.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author zhu
 * @date 2021/4/14 16:17:10
 * @description
 */
public class ExpriedSessionStrategy implements SessionInformationExpiredStrategy {

	// 如果有相同的用户登录，处理的方法，踢出上一个用户
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		System.out.println("并发登录");
		event.getResponse().sendRedirect("/loginBetobe");
	}
}
