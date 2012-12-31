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
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.RSSUtil;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class RSSTag extends IncludeTag {

	public void setDelta(int delta) {
		_delta = delta;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFeedType(String feedType) {
		_feedType = feedType;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setResourceURL(ResourceURL resourceURL) {
		_resourceURL = resourceURL;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		_delta = SearchContainer.DEFAULT_DELTA;
		_displayStyle = RSSUtil.DISPLAY_STYLE_DEFAULT;
		_feedType = RSSUtil.FEED_TYPE_DEFAULT;
		_message = "RSS";
		_name = null;
		_resourceURL = null;
		_url = null;
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
		request.setAttribute("liferay-ui:rss:message", _message);
		request.setAttribute("liferay-ui:rss:url", getURL());
	}

	private String getURL() {
		if (_resourceURL != null) {
			_resourceURL.setCacheability(ResourceURL.FULL);

			if ((_delta > 0) && (_delta != SearchContainer.DEFAULT_DELTA)) {
				_resourceURL.setParameter("max", String.valueOf(_delta));
			}

			if (Validator.isNotNull(_displayStyle) &&
				!_displayStyle.equals(RSSUtil.DISPLAY_STYLE_DEFAULT)) {

				_resourceURL.setParameter("displayStyle", _displayStyle);
			}

			if (Validator.isNotNull(_feedType) &&
				!_feedType.equals(RSSUtil.FEED_TYPE_DEFAULT)) {

				_resourceURL.setParameter(
					"type", RSSUtil.getFeedTypeFormat(_feedType));
				_resourceURL.setParameter(
					"version",
					String.valueOf(RSSUtil.getFeedTypeVersion(_feedType)));
			}

			if (Validator.isNotNull(_name)) {
				_resourceURL.setParameter("feedTitle", _name);
			}

			return _resourceURL.toString();
		}
		else if (Validator.isNotNull(_url)) {
			if ((_delta > 0) && (_delta != SearchContainer.DEFAULT_DELTA)) {
				_url = HttpUtil.addParameter(_url, "max", _delta);
			}

			if (Validator.isNotNull(_displayStyle) &&
				!_displayStyle.equals(RSSUtil.DISPLAY_STYLE_DEFAULT)) {

				_url = HttpUtil.addParameter(
					_url, "displayStyle", _displayStyle);
			}

			if (Validator.isNotNull(_feedType) &&
				!_feedType.equals(RSSUtil.FEED_TYPE_DEFAULT)) {

				_url = HttpUtil.addParameter(
					_url, "type", RSSUtil.getFeedTypeFormat(_feedType));
				_url = HttpUtil.addParameter(
					_url, "version",
					String.valueOf(RSSUtil.getFeedTypeVersion(_feedType)));
			}

			if (Validator.isNotNull(_name)) {
				_url = HttpUtil.addParameter(_url, "feedTitle", _name);
			}

			return _url;
		}

		return StringPool.BLANK;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/ui/rss/page.jsp";

	private int _delta = SearchContainer.DEFAULT_DELTA;
	private String _displayStyle = RSSUtil.DISPLAY_STYLE_DEFAULT;
	private String _feedType = RSSUtil.FEED_TYPE_DEFAULT;
	private String _message = "rss";
	private String _name;
	private ResourceURL _resourceURL;
	private String _url;

}