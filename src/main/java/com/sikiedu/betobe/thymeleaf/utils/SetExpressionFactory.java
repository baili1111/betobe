package com.sikiedu.betobe.thymeleaf.utils;

import com.sikiedu.betobe.utils.MyArrayListUtils;
import com.sikiedu.betobe.utils.MySetUtils;
import com.sikiedu.betobe.utils.MyStringUtils;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/4/29 16:46:38
 * @description
 */
public class SetExpressionFactory implements IExpressionObjectFactory {

	public static final String SET_NAME = "setUtils";

	public static final String STRING_NAME = "stringUtils";

	public static final String ARRAYLIST_NAME = "arrayListUtils";

	private static final MySetUtils mySetUtils = new MySetUtils();

	private static final MyStringUtils myStringUtils = new MyStringUtils();

	private static final MyArrayListUtils myArrayListUtils = new MyArrayListUtils();

	private static final Set<String> target;

	static {
		final Set<String> objectNames = new LinkedHashSet<>();
		objectNames.add(SET_NAME);
		objectNames.add(STRING_NAME);
		objectNames.add(ARRAYLIST_NAME);
		target = Collections.unmodifiableSet(objectNames);
	}

	// 返回该工厂类能创建的工具类对象的集合。
	@Override
	public Set<String> getAllExpressionObjectNames() {
		return target;
	}

	// 根据表达式的名称， 创建工具类对象
	@Override
	public Object buildObject(IExpressionContext context, String expressionObjectName) {
		if (SET_NAME.equals(expressionObjectName)) {
			return mySetUtils;
		}
		if (STRING_NAME.equals(expressionObjectName)) {
			return myStringUtils;
		}
		if (ARRAYLIST_NAME.equals(expressionObjectName)) {
			return myArrayListUtils;
		}
		return null;
	}

	// 返回该工具对象是否可缓存
	@Override
	public boolean isCacheable(String expressionObjectName) {
		if (expressionObjectName != null && "setUtils".equals(expressionObjectName)) {
			return true;
		}
		if (expressionObjectName != null && "stringUtils".equals(expressionObjectName)) {
			return true;
		}
		if (expressionObjectName != null && "arrayListUtils".equals(expressionObjectName)) {
			return true;
		}
		return false;
	}
}
