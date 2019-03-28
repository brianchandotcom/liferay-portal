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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.BaseBodyTagSupport;
import com.liferay.taglib.util.IncludeTag;

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
			_id = StringUtil.randomId();
		}

		if (Validator.isNull(_parentId)) {
			BaseBodyTagSupport baseBodyTagSupport =
				(BaseBodyTagSupport)findAncestorWithClass(
					this, BaseBodyTagSupport.class);

			if (baseBodyTagSupport instanceof PanelContainerTag) {
				PanelContainerTag panelContainerTag =
					(PanelContainerTag)baseBodyTagSupport;

				_accordion = panelContainerTag.isAccordion();
				_parentId = panelContainerTag.getId();
			}
		}

		request.setAttribute(
			"liferay-ui:panel:accordion", String.valueOf(_accordion));
		request.setAttribute(
			"liferay-ui:panel:collapsible", String.valueOf(_collapsible));
		request.setAttribute("liferay-ui:panel:cssClass", _cssClass);
		request.setAttribute("liferay-ui:panel:defaultState", _defaultState);
		request.setAttribute("liferay-ui:panel:extended", _extended);
		request.setAttribute("liferay-ui:panel:helpMessage", _helpMessage);
		request.setAttribute("liferay-ui:panel:iconCssClass", _iconCssClass);
		request.setAttribute("liferay-ui:panel:id", _id);
		request.setAttribute("liferay-ui:panel:parentId", _parentId);
		request.setAttribute(
			"liferay-ui:panel:persistState", String.valueOf(_persistState));
		request.setAttribute("liferay-ui:panel:state", _state);
		request.setAttribute("liferay-ui:panel:title", _title);

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

	public void setIconCssClass(String iconCssClass) {
		_iconCssClass = iconCssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setMarkupView(String markupView) {
		_markupView = markupView;
	}

	public void setParentId(String parentId) {
		_parentId = parentId;
	}

	public void setPersistState(boolean persistState) {
		_persistState = persistState;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_accordion = false;
		_collapsible = true;
		_cssClass = null;
		_defaultState = "open";
		_endPage = null;
		_extended = null;
		_helpMessage = null;
		_iconCssClass = null;
		_id = null;
		_markupView = null;
		_parentId = StringPool.BLANK;
		_persistState = true;
		_startPage = null;
		_state = null;
		_title = null;
	}

	@Override
	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			if (Validator.isNotNull(_markupView)) {
				return "/html/taglib/ui/panel/" + _markupView + "/end.jsp";
			}

			return "/html/taglib/ui/panel/end.jsp";
		}

		return _endPage;
	}

	@Override
	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			if (Validator.isNotNull(_markupView)) {
				return "/html/taglib/ui/panel/" + _markupView + "/start.jsp";
			}

			return "/html/taglib/ui/panel/start.jsp";
		}

		return _startPage;
	}

	private boolean _accordion;
	private boolean _collapsible = true;
	private String _cssClass;
	private String _defaultState = "open";
	private String _endPage;
	private Boolean _extended;
	private String _helpMessage;
	private String _iconCssClass;
	private String _id;
	private String _markupView;
	private String _parentId = StringPool.BLANK;
	private boolean _persistState = true;
	private String _startPage;
	private String _state;
	private String _title;

}