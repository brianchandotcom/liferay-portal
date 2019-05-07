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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseFieldWrapperTag;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FieldWrapperTag extends BaseFieldWrapperTag {

	@Override
	protected String getEndPage() {
		if (Objects.equals(getInlineLabel(), "right")) {
			return super.getEndPage();
		}

		return null;
	}

	@Override
	protected String getStartPage() {
		if (Validator.isNotNull(getLabel()) &&
			!Objects.equals(getInlineLabel(), "right")) {

			return super.getStartPage();
		}

		return null;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</div>");

		return EVAL_PAGE;
	}

	@Override
	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"");

		String controlGroupCss = "lfr-ddm-field-group";

		if (getInlineField()) {
			controlGroupCss = controlGroupCss.concat(
				" lfr-ddm-field-group-inline");
		}

		if (Validator.isNotNull(getInlineLabel())) {
			controlGroupCss = controlGroupCss.concat(" form-inline");
		}

		jspWriter.write(controlGroupCss);

		jspWriter.write(StringPool.SPACE);

		jspWriter.write(
			AUIUtil.buildCss(
				"field-wrapper", getDisabled(), getFirst(), getLast(),
				getCssClass()));

		jspWriter.write("\" ");

		jspWriter.write(AUIUtil.buildData((Map<String, Object>)getData()));

		jspWriter.write(">");

		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		String label = getLabel();

		if (label == null) {
			label = TextFormatter.format(getName(), TextFormatter.K);
		}

		setNamespacedAttribute(httpServletRequest, "label", label);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

}