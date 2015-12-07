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

import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.BaseBodyTagSupport;
import com.liferay.taglib.util.PortalIncludeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 */
public class PanelContainerTag extends BaseBodyTagSupport implements BodyTag {

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

			request.removeAttribute("liferay-ui:panel-container:id");
			request.removeAttribute("liferay-ui:panel-container:accordion");
			request.removeAttribute("liferay-ui:panel-container:persistState");
			request.removeAttribute("liferay-ui:panel-container:extended");
			request.removeAttribute("liferay-ui:panel-container:cssClass");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				cleanUp();
			}
		}
	}

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (Validator.isNull(_id)) {
			_id = StringUtil.randomId();
		}

		request.setAttribute("liferay-ui:panel-container:id", _id);
		request.setAttribute(
			"liferay-ui:panel-container:accordion", String.valueOf(_accordion));
		request.setAttribute(
			"liferay-ui:panel-container:persistState",
			String.valueOf(_persistState));
		request.setAttribute("liferay-ui:panel-container:extended", _extended);
		request.setAttribute("liferay-ui:panel-container:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:panel-container:panelCount" + _id,
			new IntegerWrapper());

		return EVAL_BODY_BUFFERED;
	}

	public String getId() {
		return _id;
	}

	public boolean isAccordion() {
		return _accordion;
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

	public void setMarkupView(String markupView) {
		_markupView = markupView;
	}

	public void setPersistState(boolean persistState) {
		_persistState = persistState;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	protected void cleanUp() {
		_accordion = false;
		_cssClass = null;
		_endPage = null;
		_extended = false;
		_id = null;
		_persistState = false;
		_startPage = null;
	}

	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			if (Validator.isNotNull(_markupView)) {
				return "/html/taglib/ui/panel_container/" + _markupView +
					"/end.jsp";
			}

			return "/html/taglib/ui/panel_container/end.jsp";
		}
		else {
			return _endPage;
		}
	}

	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			if (Validator.isNotNull(_markupView)) {
				return "/html/taglib/ui/panel_container/" + _markupView +
					"/start.jsp";
			}

			return "/html/taglib/ui/panel_container/start.jsp";
		}
		else {
			return _startPage;
		}
	}

	private boolean _accordion;
	private String _cssClass;
	private String _endPage;
	private Boolean _extended;
	private String _id;
	private String _markupView;
	private boolean _persistState;
	private String _startPage;

}