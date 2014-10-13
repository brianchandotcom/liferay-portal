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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.taglib.aui.base.BaseNavBarTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 */
public class NavBarTag extends BaseNavBarTag implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		setNamespacedAttribute(
			request, "responsiveButtons", _responsiveButtonsSB.toString());

		return super.doEndTag();
	}

	public StringBundler getResponsiveButtonsSB() {
		return _responsiveButtonsSB;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_responsiveButtonsSB.setIndex(0);
	}

	@Override
	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	private final StringBundler _responsiveButtonsSB = new StringBundler();

}