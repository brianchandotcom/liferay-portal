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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.BaseBodyTagSupport;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.util.PortalIncludeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconListTag extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doAfterBody() {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper iconCount =
			(IntegerWrapper)httpServletRequest.getAttribute(
				"liferay-ui:icon-list:icon-count");

		Boolean singleIcon = (Boolean)httpServletRequest.getAttribute(
			"liferay-ui:icon-list:single-icon");

		if ((iconCount != null) && (iconCount.getValue() == 1) &&
			(singleIcon == null)) {

			bodyContent.clearBody();

			httpServletRequest.setAttribute(
				"liferay-ui:icon-list:single-icon", Boolean.TRUE);

			return EVAL_BODY_AGAIN;
		}

		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			IntegerWrapper iconCount =
				(IntegerWrapper)httpServletRequest.getAttribute(
					"liferay-ui:icon-list:icon-count");

			httpServletRequest.removeAttribute(
				"liferay-ui:icon-list:icon-count");

			Boolean singleIcon = (Boolean)httpServletRequest.getAttribute(
				"liferay-ui:icon-list:single-icon");

			httpServletRequest.removeAttribute(
				"liferay-ui:icon-list:single-icon");

			JspWriter jspWriter = pageContext.getOut();

			if ((iconCount != null) && (iconCount.getValue() > 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				if (!FileAvailabilityUtil.isAvailable(
						pageContext.getServletContext(), getStartPage())) {

					jspWriter.write(
						"<ul class=\"list-unstyled taglib-icon-list\">");
				}
				else {
					PortalIncludeUtil.include(pageContext, _startPage);
				}
			}

			writeBodyContent(jspWriter);

			if ((iconCount != null) && (iconCount.getValue() > 1) &&
				((singleIcon == null) || _showWhenSingleIcon)) {

				if (!FileAvailabilityUtil.isAvailable(
						pageContext.getServletContext(), getEndPage())) {

					jspWriter.write("</ul>");
				}
				else {
					PortalIncludeUtil.include(pageContext, _endPage);
				}
			}

			httpServletRequest.removeAttribute(
				"liferay-ui:icon-list:showWhenSingleIcon");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_endPage = null;
			_showWhenSingleIcon = false;
			_startPage = null;
		}
	}

	@Override
	public int doStartTag() {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		httpServletRequest.setAttribute(
			"liferay-ui:icon-list:icon-count", new IntegerWrapper());
		httpServletRequest.setAttribute(
			"liferay-ui:icon-list:showWhenSingleIcon",
			String.valueOf(_showWhenSingleIcon));

		return EVAL_BODY_BUFFERED;
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setShowWhenSingleIcon(boolean showWhenSingleIcon) {
		_showWhenSingleIcon = showWhenSingleIcon;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}

		return _endPage;
	}

	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}

		return _startPage;
	}

	private static final String _END_PAGE = "/html/taglib/ui/icon_list/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/icon_list/start.jsp";

	private String _endPage;
	private boolean _showWhenSingleIcon;
	private String _startPage;

}