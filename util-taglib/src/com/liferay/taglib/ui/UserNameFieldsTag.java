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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class UserNameFieldsTag extends IncludeTag {

	public Object getBean() {
		return _bean;
	}

	public Contact getContact() {
		return _contact;
	}

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setContact(Contact contact) {
		_contact = contact;
	}

	public void setUser(User user) {
		_user = user;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_bean = null;
		_contact = null;
		_user = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected User getUser() {
		if (_user == null) {
			try {
				return PortalUtil.getSelectedUser(getRequest());
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return _user;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		if (_bean == null) {
			_bean = pageContext.getAttribute("aui:model-context:bean");
		}

		httpServletRequest.setAttribute(
			"liferay-ui:user-name-fields:bean", _bean);
		httpServletRequest.setAttribute(
			"liferay-ui:user-name-fields:contact", _contact);
		httpServletRequest.setAttribute(
			"liferay-ui:user-name-fields:user", getUser());
	}

	private static final String _PAGE =
		"/html/taglib/ui/user_name_fields/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		UserNameFieldsTag.class);

	private Object _bean;
	private Contact _contact;
	private User _user;

}