/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.core;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.TagSupport;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

/**
 * @author Shuyang Zhou
 */
public abstract class ConditionalTagSupport extends TagSupport {

	@Override
	public int doStartTag() throws JspTagException {
		_result = condition();

		if (_var != null) {
			pageContext.setAttribute(_var, _result, _scope);
		}

		if (_result) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();

		_result = false;
		_scope = PageContext.PAGE_SCOPE;
		_var = null;
	}

	public void setScope(String scope) {
		String scopeLowerCase = StringUtil.toLowerCase(scope);

		if (scopeLowerCase.equals("application")) {
			_scope = PageContext.APPLICATION_SCOPE;
		}
		else if (scopeLowerCase.equals("page")) {
			_scope = PageContext.PAGE_SCOPE;
		}
		else if (scopeLowerCase.equals("request")) {
			_scope = PageContext.REQUEST_SCOPE;
		}
		else if (scopeLowerCase.equals("session")) {
			_scope = PageContext.SESSION_SCOPE;
		}
	}

	public void setVar(String var) {
		_var = var;
	}

	protected abstract boolean condition();

	private boolean _result;
	private int _scope = PageContext.PAGE_SCOPE;
	private String _var;

}