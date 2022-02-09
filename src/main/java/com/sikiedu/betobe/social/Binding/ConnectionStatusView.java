package com.sikiedu.betobe.social.Binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhu
 * @date 2021/4/14 19:35:52
 * @description
 */
@Component("connect/status")
public class ConnectionStatusView extends AbstractView {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
	                                       HttpServletResponse response) throws Exception {

		// 获得数据
		Map<String, List<Connection>> connectionMap = (Map<String, List<Connection>>) model.get("connectionMap");
		// 在前端返回一个信息，告诉微信是否绑定，qq是否绑定
		Map<String, Boolean> result = new HashMap<>();

		for (String key : connectionMap.keySet()) {
			//
			result.put(key, !CollectionUtils.isEmpty(connectionMap.get(key)));
		}

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(result));

	}
}
