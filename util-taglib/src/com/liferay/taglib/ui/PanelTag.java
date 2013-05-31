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

import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.PwdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PanelTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (Validator.isNull(_id)) {
			_id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
		}

		BaseBodyTagSupport baseBodyTagSupport =
			(BaseBodyTagSupport)findAncestorWithClass(
				this, BaseBodyTagSupport.class);

		String parentId = StringPool.BLANK;

		if (baseBodyTagSupport instanceof PanelContainerTag) {
			PanelContainerTag panelContainerTag =
				(PanelContainerTag)baseBodyTagSupport;

			parentId = panelContainerTag.getId();
		}

		request.setAttribute("liferay-ui:panel:helpMessage", _helpMessage);
		request.setAttribute("liferay-ui:panel:id", _id);
		request.setAttribute("liferay-ui:panel:parentId", parentId);
		request.setAttribute("liferay-ui:panel:title", _title);
		request.setAttribute(
			"liferay-ui:panel:collapsible", String.valueOf(_collapsible));
		request.setAttribute("liferay-ui:panel:defaultState", _defaultState);
		request.setAttribute(
			"liferay-ui:panel:persistState", String.valueOf(_persistState));
		request.setAttribute("liferay-ui:panel:extended", _extended);
		request.setAttribute("liferay-ui:panel:cssClass", _cssClass);

		super.doStartTag();

		return EVAL_BODY_INCLUDE;
	}

	public void setCollapsible(boolean collapsible) {
		_collapsible = collapsible;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDefaultState(String defaultState) {
		_defaultState = defaultState;
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setExtended(Boolean extended) {
		_extended = extended;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setPersistState(boolean persistState) {
		_persistState = persistState;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		_collapsible = true;
		_cssClass = null;
		_defaultState = "open";
		_endPage = null;
		_extended = null;
		_helpMessage = null;
		_id = null;
		_persistState = true;
		_startPage = null;
		_title = null;
	}

	@Override
	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	@Override
	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	private static final String _END_PAGE = "/html/taglib/ui/panel/end.jsp";

	private static final String _START_PAGE = "/html/taglib/ui/panel/start.jsp";

	private boolean _collapsible = true;
	private String _cssClass;
	private String _defaultState = "open";
	private String _endPage;
	private Boolean _extended;
	private String _helpMessage;
	private String _id;
	private boolean _persistState = true;
	private String _startPage;
	private String _title;

}