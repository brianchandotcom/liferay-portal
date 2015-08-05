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

import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class UserNameFieldsTag extends IncludeTag {

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
		_bean = null;
		_contact = null;
		_user = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (_bean == null) {
			_bean = pageContext.getAttribute("aui:model-context:bean");
		}

		request.setAttribute("liferay-ui:user-name-fields:bean", _bean);
		request.setAttribute("liferay-ui:user-name-fields:contact", _contact);
		request.setAttribute("liferay-ui:user-name-fields:user", _user);
	}

	private static final String _PAGE =
		"/html/taglib/ui/user_name_fields/page.jsp";

	private Object _bean;
	private Contact _contact;
	private User _user;

}