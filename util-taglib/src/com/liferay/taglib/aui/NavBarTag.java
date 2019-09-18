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

import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseNavBarTag;

import javax.servlet.http.HttpServletRequest;
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
		HttpServletRequest httpServletRequest = getRequest();

		setNamespacedAttribute(httpServletRequest, "dataTarget", _dataTarget);
		setNamespacedAttribute(
			httpServletRequest, "responsiveButtons",
			_responsiveButtonsSB.toString());
		setNamespacedAttribute(
			httpServletRequest, "selectedItemName", _selectedItemName);

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest httpServletRequest = getRequest();

		httpServletRequest.setAttribute(
			"aui:nav-bar:navItemCount", new IntegerWrapper());

		return super.doStartTag();
	}

	public StringBundler getResponsiveButtonsSB() {
		return _responsiveButtonsSB;
	}

	public void setDataTarget(String dataTarget) {
		_dataTarget = dataTarget;
	}

	public void setSelectedItemName(String selectedItemName) {
		_selectedItemName = selectedItemName;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_dataTarget = null;
		_responsiveButtonsSB.setIndex(0);
		_selectedItemName = null;
	}

	@Override
	protected String getPage() {
		String markupView = getMarkupView();

		if (Validator.isNotNull(markupView)) {
			return "/html/taglib/aui/nav_bar/" + markupView + "/page.jsp";
		}

		return "/html/taglib/aui/nav_bar/page.jsp";
	}

	@Override
	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	private String _dataTarget;
	private final StringBundler _responsiveButtonsSB = new StringBundler();
	private String _selectedItemName;

}