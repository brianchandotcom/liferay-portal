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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.json.JSONArray;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ambrin Chaudhary
 */
public class AddMenuTag extends IconTag {

	public void setActionsJSONArray(JSONArray _actionsJSONArray) {
		this._actionsJSONArray = _actionsJSONArray;
	}

	@Override
	protected void cleanUp() {
		_actionsJSONArray = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:add-menu:actionsJSONArray", _actionsJSONArray);
	}

	private static final String _PAGE = "/html/taglib/ui/add_menu/page.jsp";

	private JSONArray _actionsJSONArray;

}