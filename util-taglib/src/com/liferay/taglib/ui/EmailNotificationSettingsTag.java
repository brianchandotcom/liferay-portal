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

import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EmailNotificationSettingsTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setEmailBody(String emailBody) {
		_emailBody = emailBody;
	}

	public void setEmailDefinitionTerms(
		Map<String, String> emailDefinitionTerms) {

		_emailDefinitionTerms = emailDefinitionTerms;
	}

	public void setEmailEnabled(boolean emailEnabled) {
		_emailEnabled = emailEnabled;
	}

	public void setEmailParam(String emailParam) {
		_emailParam = emailParam;
	}

	public void setEmailSubject(String emailSubject) {
		_emailSubject = emailSubject;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setShowEmailEnabled(boolean showEmailEnabled) {
		_showEmailEnabled = showEmailEnabled;
	}

	@Override
	protected void cleanUp() {
		_emailDefinitionTerms = null;
		_emailBody = null;
		_emailEnabled = false;
		_emailParam = null;
		_emailSubject = null;
		_languageId = null;
		_showEmailEnabled = true;
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
			"liferay-ui:email-notification-settings:emailDefinitionTerms",
			_emailDefinitionTerms);
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailBody", _emailBody);
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailEnabled",
			String.valueOf(_emailEnabled));
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailParam", _emailParam);
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailSubject",
			_emailSubject);
		request.setAttribute(
			"liferay-ui:email-notification-settings:languageId", _languageId);
		request.setAttribute(
			"liferay-ui:email-notification-settings:showEmailEnabled",
			_showEmailEnabled);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/ui/email_notification_settings/page.jsp";

	private String _emailBody;
	private Map<String, String> _emailDefinitionTerms;
	private boolean _emailEnabled;
	private String _emailParam;
	private String _emailSubject;
	private String _languageId;
	private boolean _showEmailEnabled;

}