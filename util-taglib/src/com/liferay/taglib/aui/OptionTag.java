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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseOptionTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class OptionTag extends BaseOptionTag {

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</option>");

		return EVAL_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		Object value = getValue();

		if (value == null) {
			value = getLabel();
		}

		boolean selected = getSelected();

		if (getUseModelValue()) {
			String selectValue = GetterUtil.getString(
				(String)httpServletRequest.getAttribute("aui:select:value"));

			if (Validator.isNotNull(selectValue)) {
				selected = selectValue.equals(String.valueOf(value));
			}
		}

		setNamespacedAttribute(
			httpServletRequest, "selected", String.valueOf(selected));
		setNamespacedAttribute(httpServletRequest, "value", value);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

}