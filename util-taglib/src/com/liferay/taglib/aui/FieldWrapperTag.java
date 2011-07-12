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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FieldWrapperTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setInlineField(boolean inlineField) {
		_inlineField = inlineField;
	}

	public void setInlineLabel(String inlineLabel) {
		_inlineLabel = inlineLabel;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLast(boolean last) {
		_last = last;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_first = false;
		_helpMessage = null;
		_inlineField = false;
		_inlineLabel = null;
		_label = null;
		_last = false;
		_name = null;
		_required = false;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String label = _label;

		if (label == null) {
			label = TextFormatter.format(_name, TextFormatter.K);
		}

		request.setAttribute("aui:field-wrapper:cssClass", _cssClass);
		request.setAttribute(
			"aui:field-wrapper:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:field-wrapper:first", String.valueOf(_first));
		request.setAttribute("aui:field-wrapper:helpMessage", _helpMessage);
		request.setAttribute(
			"aui:field-wrapper:inlineField", String.valueOf(_inlineField));
		request.setAttribute("aui:field-wrapper:inlineLabel", _inlineLabel);
		request.setAttribute("aui:field-wrapper:label", label);
		request.setAttribute("aui:field-wrapper:last", String.valueOf(_last));
		request.setAttribute("aui:field-wrapper:name", _name);
		request.setAttribute("aui:field-wrapper:required", String.valueOf(_required));
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE =
		"/html/taglib/aui/field_wrapper/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/field_wrapper/start.jsp";

	private String _cssClass;
	private boolean _first;
	private String _helpMessage;
	private boolean _inlineField;
	private String _inlineLabel;
	private String _label;
	private boolean _last;
	private String _name;
	private boolean _required;

}