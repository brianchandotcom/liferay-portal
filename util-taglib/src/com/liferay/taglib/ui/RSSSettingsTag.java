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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class RSSSettingsTag extends IncludeTag {

	public void setDelta(int delta) {
		_delta = delta;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyles(String[] displayStyles) {
		_displayStyles = displayStyles;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setFeedType(String feedType) {
		_feedType = feedType;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNameEnabled(boolean nameEnabled) {
		_nameEnabled = nameEnabled;
	}

	@Override
	protected void cleanUp() {
		_delta = SearchContainer.DEFAULT_DELTA;
		_displayStyle = RSSUtil.DISPLAY_STYLE_DEFAULT;
		_displayStyles = RSSUtil.DISPLAY_STYLES;
		_enabled = false;
		_feedType = RSSUtil.FEED_TYPE_DEFAULT;
		_name = null;
		_nameEnabled = false;
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
			"liferay-ui:rss-settings:delta", String.valueOf(_delta));
		request.setAttribute(
			"liferay-ui:rss-settings:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:rss-settings:displayStyles", _displayStyles);
		request.setAttribute(
			"liferay-ui:rss-settings:enabled", String.valueOf(_enabled));
		request.setAttribute("liferay-ui:rss-settings:feedType", _feedType);
		request.setAttribute("liferay-ui:rss-settings:name", _name);
		request.setAttribute(
			"liferay-ui:rss-settings:nameEnabled",
			String.valueOf(_nameEnabled));
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/ui/rss_settings/page.jsp";

	private int _delta = SearchContainer.DEFAULT_DELTA;
	private String _displayStyle = RSSUtil.DISPLAY_STYLE_DEFAULT;
	private String[] _displayStyles = RSSUtil.DISPLAY_STYLES;
	private boolean _enabled;
	private String _feedType = RSSUtil.FEED_TYPE_DEFAULT;
	private String _name;
	private boolean _nameEnabled;

}