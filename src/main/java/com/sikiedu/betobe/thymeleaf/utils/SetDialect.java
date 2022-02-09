package com.sikiedu.betobe.thymeleaf.utils;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * @author zhu
 * @date 2021/4/29 16:35:34
 * @description
 */
public class SetDialect extends AbstractDialect implements IExpressionObjectDialect {

	private final IExpressionObjectFactory OBJECT_FACTORY = new SetExpressionFactory();

	public SetDialect(String name) {
		super(name);
	}

	@Override
	public IExpressionObjectFactory getExpressionObjectFactory() {
		return OBJECT_FACTORY;
	}

}
