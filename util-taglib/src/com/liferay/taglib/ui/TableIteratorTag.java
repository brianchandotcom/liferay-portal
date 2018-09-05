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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class TableIteratorTag extends TagSupport {

	@Override
	public int doAfterBody() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.setAttribute(
				"liferay-ui:table-iterator:listPos", String.valueOf(_listPos));

			PortalIncludeUtil.include(pageContext, getBodyPage());

			_listPos++;

			if (_listPos < _list.size()) {
				pageContext.setAttribute(
					"tableIteratorObj", _list.get(_listPos));
				pageContext.setAttribute(
					"tableIteratorPos", Integer.valueOf(_listPos));

				return EVAL_BODY_AGAIN;
			}

			return SKIP_BODY;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			if (!_list.isEmpty()) {
				PortalIncludeUtil.include(pageContext, getEndPage());
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_startPage = null;
				_bodyPage = null;
				_endPage = null;
				_list = null;
				_listPos = 0;
				_rowLength = 0;
				_rowPadding = "0";
				_rowValign = "middle";
				_rowBreak = null;
			}
		}
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if (!_list.isEmpty()) {
				HttpServletRequest request =
					(HttpServletRequest)pageContext.getRequest();

				request.setAttribute("liferay-ui:table-iterator:list", _list);
				request.setAttribute(
					"liferay-ui:table-iterator:rowBreak", _rowBreak);
				request.setAttribute(
					"liferay-ui:table-iterator:rowLength",
					String.valueOf(_rowLength));
				request.setAttribute(
					"liferay-ui:table-iterator:rowPadding", _rowPadding);
				request.setAttribute(
					"liferay-ui:table-iterator:rowValign", _rowValign);
				request.setAttribute("liferay-ui:table-iterator:width", _width);

				PortalIncludeUtil.include(pageContext, getStartPage());

				pageContext.setAttribute(
					"tableIteratorObj", _list.get(_listPos));
				pageContext.setAttribute(
					"tableIteratorPos", Integer.valueOf(_listPos));

				return EVAL_BODY_INCLUDE;
			}

			return SKIP_BODY;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getBodyPage() {
		if (Validator.isNull(_bodyPage)) {
			return _BODY_PAGE;
		}

		return _bodyPage;
	}

	public void setBodyPage(String bodyPage) {
		_bodyPage = bodyPage;
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setList(List<?> list) {
		_list = list;
	}

	public void setListType(String listType) {
	}

	public void setRowBreak(String rowBreak) {
		_rowBreak = HtmlUtil.unescape(rowBreak);
	}

	public void setRowLength(String rowLength) {
		_rowLength = GetterUtil.getInteger(rowLength);
	}

	public void setRowPadding(String rowPadding) {
		_rowPadding = rowPadding;
	}

	public void setRowValign(String rowValign) {
		_rowValign = rowValign;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setWidth(String width) {
		_width = width;
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

	private static final String _BODY_PAGE =
		"/html/taglib/ui/table_iterator/body.jsp";

	private static final String _END_PAGE =
		"/html/taglib/ui/table_iterator/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/table_iterator/start.jsp";

	private String _bodyPage;
	private String _endPage;
	private List<?> _list;
	private int _listPos;
	private String _rowBreak = "<br />";
	private int _rowLength;
	private String _rowPadding = "0";
	private String _rowValign = "middle";
	private String _startPage;
	private String _width = StringPool.BLANK;

}