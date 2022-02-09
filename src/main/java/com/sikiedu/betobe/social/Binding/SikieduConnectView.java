package com.sikiedu.betobe.social.Binding;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhu
 * @date 2021/4/14 20:28:11
 * @description
 */
public class SikieduConnectView extends AbstractView {
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
	                                       HttpServletResponse response) throws Exception {
		response.sendRedirect("/profileBindingSuccess");
	}
}
