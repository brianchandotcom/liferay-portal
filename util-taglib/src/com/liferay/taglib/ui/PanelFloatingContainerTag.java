/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.PwdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 */
public class PanelFloatingContainerTag
	extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doAfterBody() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper panelCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:panel-container:panelCount" + _id);

		if ((panelCount != null) && (panelCount.getValue() == 1)) {

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
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			IntegerWrapper panelCount = (IntegerWrapper)request.getAttribute(
				"liferay-ui:panel-container:panelCount" + _id);

			request.removeAttribute(
				"liferay-ui:panel-container:panelCount" + _id);

			if ((panelCount != null) && (panelCount.getValue() >= 1)) {
				PortalIncludeUtil.include(pageContext, getStartPage());
			}

			writeBodyContent(pageContext.getOut());

			if ((panelCount != null) && (panelCount.getValue() >= 1)) {
				PortalIncludeUtil.include(pageContext, getEndPage());
			}

			request.removeAttribute("liferay-ui:panel-floating-container:id");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:trigger");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:accordion");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:persistState");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:paging");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:pagingElements");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:resultsPerPage");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:width");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:extended");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:cssClass");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (Validator.isNull(_id)) {
			_id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
		}

		request.setAttribute("liferay-ui:panel-floating-container:id", _id);
		request.setAttribute(
			"liferay-ui:panel-floating-container:trigger", _trigger);
		request.setAttribute(
			"liferay-ui:panel-floating-container:accordion",
			String.valueOf(_accordion));
		request.setAttribute(
			"liferay-ui:panel-floating-container:persistState",
			String.valueOf(_persistState));
		request.setAttribute(
			"liferay-ui:panel-floating-container:paging",
			String.valueOf(_paging));
		request.setAttribute(
			"liferay-ui:panel-floating-container:pagingElements",
			_pagingElements);
		request.setAttribute(
			"liferay-ui:panel-floating-container:resultsPerPage",
			String.valueOf(_resultsPerPage));
		request.setAttribute(
			"liferay-ui:panel-floating-container:width",
			String.valueOf(_width));
		request.setAttribute(
			"liferay-ui:panel-floating-container:extended", _extended);
		request.setAttribute(
			"liferay-ui:panel-floating-container:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:panel-container:panelCount" + _id,
			new IntegerWrapper());

		return EVAL_BODY_BUFFERED;
	}

	public String getId() {
		return _id;
	}

	public void setAccordion(boolean accordion) {
		_accordion = accordion;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setExtended(Boolean extended) {
		_extended = extended;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setPaging(boolean paging) {
		_paging = paging;
	}

	public void setPagingElements(String pagingElements) {
		_pagingElements = pagingElements;
	}

	public void setPersistState(boolean persistState) {
		_persistState = persistState;
	}

	public void setResultsPerPage(int resultsPerPage) {
		_resultsPerPage = resultsPerPage;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setTrigger(String trigger) {
		_trigger = trigger;
	}

	public void setWidth(int width) {
		_width = width;
	}

	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	private static final String _END_PAGE =
		"/html/taglib/ui/panel_floating_container/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/panel_floating_container/start.jsp";

	private boolean _accordion;
	private String _cssClass = StringPool.BLANK;
	private String _endPage;
	private Boolean _extended;
	private String _id;
	private boolean _paging;
	private String _pagingElements = "ul";
	private boolean _persistState;
	private int _resultsPerPage = 1;
	private String _startPage;
	private String _trigger;
	private int _width = 300;

}