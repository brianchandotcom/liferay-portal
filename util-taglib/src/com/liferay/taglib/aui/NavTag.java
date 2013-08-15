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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseNavTag;
import com.liferay.util.PwdGenerator;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 */
public class NavTag extends BaseNavTag {

	@Override
	public int doStartTag() throws JspException {
		if (getCollapsible()) {
			NavBarTag navBarTag = (NavBarTag)findAncestorWithClass(
				this, NavBarTag.class);

			if (navBarTag != null) {
				StringBundler responsiveButtonsSB =
					navBarTag.getResponsiveButtonsSB();

				responsiveButtonsSB.append("<a class=\"btn btn-navbar\" id=\"");
				responsiveButtonsSB.append(_getNamespacedId());
				responsiveButtonsSB.append("NavbarBtn\"");
				responsiveButtonsSB.append("data-navId=\"");
				responsiveButtonsSB.append(_getNamespacedId());
				responsiveButtonsSB.append("\">");

				String icon = getIcon();

				if (Validator.isNull(icon)) {
					responsiveButtonsSB.append(
						"<span class=\"icon-bar\"></span>");
					responsiveButtonsSB.append(
						"<span class=\"icon-bar\"></span>");
					responsiveButtonsSB.append(
						"<span class=\"icon-bar\"></span>");
				}
				else {
					responsiveButtonsSB.append("<i class=\"icon-");
					responsiveButtonsSB.append(icon);
					responsiveButtonsSB.append("\"></i>");
				}

				responsiveButtonsSB.append("</a>");
			}
		}

		return super.doStartTag();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_namespacedId = null;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(request, "id", _getNamespacedId());
	}

	private String _getNamespacedId() {
		if (Validator.isNull(_namespacedId)) {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			_namespacedId = getId();

			if (getUseNamespace() && (portletResponse != null) &&
				Validator.isNotNull(_namespacedId)) {

				_namespacedId = portletResponse.getNamespace() + _namespacedId;
			}

			if (Validator.isNull(_namespacedId)) {
				_namespacedId = PwdGenerator.getPassword(4);
			}
		}

		return _namespacedId;
	}

	private String _namespacedId = null;

}