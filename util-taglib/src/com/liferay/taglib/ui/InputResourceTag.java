/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.FieldWrapperTag;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InputResourceTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_id = null;
		_label = StringPool.BLANK;
		_title = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if ((_id != null) && Validator.isNull(_label)) {
			_label = TextFormatter.format(_id, TextFormatter.K);
		}

		if (Validator.isNull(_label)) {
			FieldWrapperTag parentFieldWrapperTag =
				(FieldWrapperTag)findAncestorWithClass(
					this, FieldWrapperTag.class);

			if (parentFieldWrapperTag != null) {
				_label = parentFieldWrapperTag.getLabel();

				if (_label.equals(parentFieldWrapperTag.getName())) {
					_label = StringPool.BLANK;
				}
			}
		}

		request.setAttribute("liferay-ui:input-resource:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-resource:id", _id);
		request.setAttribute("liferay-ui:input-resource:label", _label);
		request.setAttribute("liferay-ui:input-resource:title", _title);
		request.setAttribute("liferay-ui:input-resource:url", _url);
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_resource/page.jsp";

	private String _cssClass;
	private String _id;
	private String _label = StringPool.BLANK;
	private String _title;
	private String _url;

}