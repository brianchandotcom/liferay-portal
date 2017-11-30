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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class SocialBookmarksSettingsTag extends IncludeTag {

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setDisplayPosition(String displayPosition) {
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setEnabled(boolean enabled) {
	}

	public void setTypes(String types) {
		_types = types;
	}

	@Override
	protected void cleanUp() {
		_displayStyle = null;
		_types = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:social-bookmarks-settings:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:social-bookmarks-settings:types", _types);
	}

	private static final String _PAGE =
		"/html/taglib/ui/social_bookmarks_settings/page.jsp";

	private String _displayStyle;
	private String _types;

}