/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.RSSUtil;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class RSSTag extends IncludeTag {

	public void setBaseResourceURL(ResourceURL baseResourceURL) {
		_baseResourceURL = baseResourceURL;
	}

	public void setBaseURL(String baseURL) {
		_baseURL = baseURL;
	}

	public void setDelta(int delta) {
		_delta = delta;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFormat(String[] format) {
		_format = format;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	protected void cleanUp() {
		_baseResourceURL = null;
		_baseURL = null;
		_delta = _DEFAULT_RSS_DELTA;
		_displayStyle = _DEFAULT_RSS_DISPLAY_STYLE;
		_format = new String[] {_DEFAULT_RSS_FORMAT};
		_label = null;
		_name = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:rss:baseResourceURL", _baseResourceURL);
		request.setAttribute("liferay-ui:rss:baseURL", _baseURL);
		request.setAttribute("liferay-ui:rss:delta", String.valueOf(_delta));
		request.setAttribute("liferay-ui:rss:displayStyle", _displayStyle);
		request.setAttribute("liferay-ui:rss:format", _format);
		request.setAttribute("liferay-ui:rss:label", _label);
		request.setAttribute("liferay-ui:rss:name", _name);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final int _DEFAULT_RSS_DELTA = SearchContainer.DEFAULT_DELTA;

	private static final String _DEFAULT_RSS_DISPLAY_STYLE =
		RSSUtil.DISPLAY_STYLE_ABSTRACT;

	private static final String _DEFAULT_RSS_FORMAT =
		RSSUtil.FEED_FORMAT_DEFAULT;

	private static final String _PAGE = "/html/taglib/ui/rss/page.jsp";

	private ResourceURL _baseResourceURL;
	private String _baseURL;
	private int _delta;
	private String _displayStyle;
	private String[] _format;
	private String _label;
	private String _name;

}