/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassInvoker;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class InputPermissionsParamsTag extends TagSupport {

	public static String doTag(String modelName, PageContext pageContext)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			false, _doEndTagMethodKey, modelName, pageContext);

		if (returnObj != null) {
			return returnObj.toString();
		}
		else {
			return StringPool.BLANK;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			doTag(_modelName, pageContext);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setModelName(String modelName) {
		_modelName = modelName;
	}

	private static final String _TAG_CLASS =
		"com.liferay.portal.servlet.taglib.ui.InputPermissionsParamsTagUtil";

	private static MethodKey _doEndTagMethodKey = new MethodKey(
		_TAG_CLASS, "doEndTag", String.class, PageContext.class);

	private String _modelName;

}