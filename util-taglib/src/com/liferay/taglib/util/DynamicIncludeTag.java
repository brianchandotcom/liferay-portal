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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.taglib.DynamicIncludeUtil;
import com.liferay.taglib.TagSupport;
import com.liferay.taglib.servlet.PipingServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Carlos Sierra Andrés
 */
public class DynamicIncludeTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		DynamicIncludeUtil.include(
			getRequest(), getResponse(), getKey(), _ascendingPriority);

		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		if (!DynamicIncludeUtil.hasDynamicInclude(getKey())) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	public boolean getAscendingPriority() {
		return _ascendingPriority;
	}

	public String getKey() {
		return _key;
	}

	public void setAscendingPriority(boolean ascendingPriority) {
		_ascendingPriority = ascendingPriority;
	}

	public void setKey(String key) {
		_key = key;
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest)pageContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return PipingServletResponse.createPipingServletResponse(pageContext);
	}

	private boolean _ascendingPriority = true;
	private String _key;

}