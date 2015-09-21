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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public class SocialActivitiesTag extends IncludeTag {

	public void setActivities(List<SocialActivity> activities) {
		_activities = activities;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setDisplayRSSFeed(boolean displayRSSFeed) {
		_displayRSSFeed = displayRSSFeed;
	}

	public void setFeedDelta(int feedDelta) {
		_feedDelta = feedDelta;
	}

	public void setFeedDisplayStyle(String feedDisplayStyle) {
		_feedDisplayStyle = feedDisplayStyle;
	}

	public void setFeedEnabled(boolean feedEnabled) {
		_feedEnabled = feedEnabled;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setFeedURL(String)}
	 */
	@Deprecated
	public void setFeedLink(String feedLink) {
		_feedURL = feedLink;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setFeedURLMessage(String)}
	 */
	@Deprecated
	public void setFeedLinkMessage(String feedLinkMessage) {
		_feedURLMessage = feedLinkMessage;
	}

	public void setFeedResourceURL(ResourceURL feedResourceURL) {
		_feedResourceURL = feedResourceURL;
	}

	public void setFeedTitle(String feedTitle) {
		_feedTitle = feedTitle;
	}

	public void setFeedType(String feedType) {
		_feedType = feedType;
	}

	public void setFeedURL(String feedURL) {
		_feedURL = feedURL;
	}

	public void setFeedURLMessage(String feedURLMessage) {
		_feedURLMessage = feedURLMessage;
	}

	@Override
	protected void cleanUp() {
		_activities = null;
		_className = StringPool.BLANK;
		_classPK = 0;
		_displayRSSFeed = false;
		_feedDelta = 0;
		_feedDisplayStyle = null;
		_feedEnabled = false;
		_feedResourceURL = null;
		_feedTitle = null;
		_feedType = null;
		_feedURL = StringPool.BLANK;
		_feedURLMessage = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:social-activities:activities", _activities);
		request.setAttribute(
			"liferay-ui:social-activities:className", _className);
		request.setAttribute(
			"liferay-ui:social-activities:classPK", String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:social-activities:displayRSSFeed",
			String.valueOf(_displayRSSFeed));
		request.setAttribute(
			"liferay-ui:social-activities:feedDelta",
			String.valueOf(_feedDelta));
		request.setAttribute(
			"liferay-ui:social-activities:feedDisplayStyle", _feedDisplayStyle);
		request.setAttribute(
			"liferay-ui:social-activities:feedEnabled",
			String.valueOf(_feedEnabled));
		request.setAttribute(
			"liferay-ui:social-activities:feedResourceURL", _feedResourceURL);
		request.setAttribute(
			"liferay-ui:social-activities:feedTitle", _feedTitle);
		request.setAttribute(
			"liferay-ui:social-activities:feedType", _feedType);
		request.setAttribute("liferay-ui:social-activities:feedURL", _feedURL);
		request.setAttribute(
			"liferay-ui:social-activities:feedURLMessage", _feedURLMessage);
	}

	private static final String _PAGE =
		"/html/taglib/ui/social_activities/page.jsp";

	private List<SocialActivity> _activities;
	private String _className = StringPool.BLANK;
	private long _classPK;
	private boolean _displayRSSFeed;
	private int _feedDelta;
	private String _feedDisplayStyle;
	private boolean _feedEnabled;
	private ResourceURL _feedResourceURL;
	private String _feedTitle;
	private String _feedType;
	private String _feedURL = StringPool.BLANK;
	private String _feedURLMessage = StringPool.BLANK;

}