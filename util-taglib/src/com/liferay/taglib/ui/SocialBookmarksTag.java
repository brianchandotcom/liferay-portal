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

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class SocialBookmarksTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		if (_types.length == 0) {
			return EVAL_PAGE;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		if (_types.length == 0) {
			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setContentId(String contentId) {
		_contentId = contentId;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setTypes(String types) {
		_types = StringUtil.split(types);
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		_contentId = null;
		_displayStyle = null;
		_target = null;
		_title = null;
		_types = _SOCIAL_BOOKMARK_TYPES;
		_url = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:social-bookmark:contentId", _contentId);
		request.setAttribute("liferay-ui:social-bookmark:target", _target);
		request.setAttribute("liferay-ui:social-bookmark:title", _title);
		request.setAttribute("liferay-ui:social-bookmark:types", _types);
		request.setAttribute("liferay-ui:social-bookmark:url", _url);

		request.setAttribute(
			"liferay-ui:social-bookmarks:displayStyle", _displayStyle);
	}

	private static final String _PAGE =
		"/html/taglib/ui/social_bookmarks/page.jsp";

	private static final String[] _SOCIAL_BOOKMARK_TYPES = PropsUtil.getArray(
		PropsKeys.SOCIAL_BOOKMARK_TYPES);

	private String _contentId;
	private String _displayStyle;
	private String _target;
	private String _title;
	private String[] _types = _SOCIAL_BOOKMARK_TYPES;
	private String _url;

}