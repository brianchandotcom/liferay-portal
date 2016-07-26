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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseFieldsetTag;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FieldsetTag extends BaseFieldsetTag {

	@Override
	public int doStartTag() throws JspException {
		FieldsetGroupTag fieldsetGroupTag =
			(FieldsetGroupTag)findAncestorWithClass(
				this, FieldsetGroupTag.class);

		if (Validator.isNull(getMarkupView()) && (fieldsetGroupTag != null)) {
			setMarkupView(fieldsetGroupTag.getMarkupView());
		}

		return super.doStartTag();
	}

	@Override
	protected String getEndPage() {
		if (Validator.isNotNull(getMarkupView())) {
			return "/html/taglib/aui/fieldset/" + getMarkupView() + "/end.jsp";
		}

		return "/html/taglib/aui/fieldset/end.jsp";
	}

	@Override
	protected String getStartPage() {
		if (Validator.isNotNull(getMarkupView())) {
			return "/html/taglib/aui/fieldset/" + getMarkupView() +
				"/start.jsp";
		}

		return "/html/taglib/aui/fieldset/start.jsp";
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (Validator.isNull(getId()) && Validator.isNotNull(getLabel()) &&
			getCollapsible()) {

			setId(
				_getNamespace() +
					PortalUtil.getUniqueElementId(
						request, _getNamespace(),
						AUIUtil.normalizeId(getLabel())));
		}

		super.setAttributes(request);
	}

	private String _getNamespace() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			return portletResponse.getNamespace();
		}

		return StringPool.BLANK;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

}