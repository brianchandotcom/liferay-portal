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

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class ErrorTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			PortletRequest portletRequest =
				(PortletRequest)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			boolean includeEndPage = false;

			if (Validator.isNull(_key)) {
				if (!SessionErrors.isEmpty(portletRequest)) {
					includeEndPage = true;
				}
			}
			else {
				if (SessionErrors.contains(portletRequest, _key)) {
					includeEndPage = true;
				}
			}

			if (includeEndPage) {
				PortalIncludeUtil.include(pageContext, getEndPage());

				String errorMarkerKey = (String)request.getAttribute(
					"liferay-ui:error-marker:key");
				String errorMarkerValue = (String)request.getAttribute(
					"liferay-ui:error-marker:value");

				if (Validator.isNotNull(errorMarkerKey) &&
					Validator.isNotNull(errorMarkerValue)) {

					request.setAttribute(errorMarkerKey, errorMarkerValue);

					Object exception = getException(portletRequest);

					if (exception instanceof Exception) {
						request.setAttribute(
							"liferay-ui:error:exception", exception);
					}

					request.setAttribute(
						"liferay-ui:error:focusField", _focusField);
				}
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			PortletRequest portletRequest =
				(PortletRequest)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			request.setAttribute("liferay-ui:error:key", _key);
			request.setAttribute("liferay-ui:error:message", _message);
			request.setAttribute("liferay-ui:error:rowBreak", _rowBreak);
			request.setAttribute(
				"liferay-ui:error:translateMessage",
				String.valueOf(_translateMessage));

			if (Validator.isNotNull(_message)) {
				return SKIP_BODY;
			}

			if (SessionErrors.contains(portletRequest, _key)) {
				Object value = getException(portletRequest);

				PortalIncludeUtil.include(pageContext, getStartPage());

				if (value != null) {
					pageContext.setAttribute("errorException", value);
				}

				return EVAL_BODY_INCLUDE;
			}

			return SKIP_BODY;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setException(Class<?> exception) {
		_exception = exception;

		if (_exception != null) {
			_key = _exception.getName();
		}
	}

	public void setFocusField(String focusField) {
		_focusField = focusField;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setRowBreak(String rowBreak) {
		_rowBreak = HtmlUtil.unescape(rowBreak);
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setTranslateMessage(boolean translateMessage) {
		_translateMessage = translateMessage;
	}

	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	protected Object getException(PortletRequest portletRequest) {
		Object value = null;

		if (_exception != null) {
			value = SessionErrors.get(portletRequest, _exception.getName());
		}
		else {
			value = SessionErrors.get(portletRequest, _key);
		}

		return value;
	}

	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	private static final String _END_PAGE = "/html/taglib/ui/error/end.jsp";

	private static final String _START_PAGE = "/html/taglib/ui/error/start.jsp";

	private String _endPage;
	private Class<?> _exception;
	private String _focusField;
	private String _key;
	private String _message;
	private String _rowBreak = StringPool.BLANK;
	private String _startPage;
	private boolean _translateMessage = true;

}