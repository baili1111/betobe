package com.sikiedu.betobe.filter;

import com.sikiedu.betobe.utils.BaseDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author zhu
 * @date 2021/5/7 12:50:15
 * @description
 */

@Component
public class DataFilter implements Filter {

	@Autowired
	private BaseDataUtils baseDataUtils;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		baseDataUtils.getHeaderData(request);
		baseDataUtils.getFooterData(request);

		chain.doFilter(request, response);

	}
}
