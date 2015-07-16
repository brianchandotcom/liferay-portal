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

import com.liferay.portal.kernel.servlet.taglib.ui.AddMenuItem;
import com.liferay.portal.kernel.util.*;
import com.liferay.taglib.BaseBodyTagSupport;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Ambrin Chaudhary
 */
public class AddMenuTag extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doAfterBody() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper addCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:add-menu:add-count");

		if (_menuItems != null) {
			addCount.setValue(_menuItems.size());

			setAttributes(request);

			return SKIP_BODY;
		}

		if ((addCount != null) && (addCount.getValue() == 0)) {
			bodyContent.clearBody();

			return EVAL_BODY_AGAIN;
		}
		else {
			return SKIP_BODY;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			return processEndTag();
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
		}
	}

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute(
			"liferay-ui:add-menu:add-count", new IntegerWrapper());

		request.setAttribute(
			"liferay-ui:add-menu:menuItemList", new ArrayList<AddMenuItem>());

		return EVAL_BODY_BUFFERED;
	}

	public void setMenuItems(List<AddMenuItem> _menuItems) {
		this._menuItems = _menuItems;
	}

	protected String getEndPage() {
		return "/html/taglib/ui/add_menu/end.jsp";
	}

	protected String getStartPage() {
		return "/html/taglib/ui/add_menu/start.jsp";
	}

	protected int processEndTag() throws Exception {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper addCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:add-menu:add-count");

		if ((addCount != null) && (addCount.getValue() > 0)) {
			PortalIncludeUtil.include(pageContext, getStartPage());

			PortalIncludeUtil.include(pageContext, getEndPage());

			request.removeAttribute("liferay-ui:add-menu:add-count");

			request.removeAttribute("liferay-ui:add-menu:menuItemList");
		}

		return EVAL_PAGE;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:add-menu-item:menuItemList", _menuItems);
	}

	private List<AddMenuItem> _menuItems;

}