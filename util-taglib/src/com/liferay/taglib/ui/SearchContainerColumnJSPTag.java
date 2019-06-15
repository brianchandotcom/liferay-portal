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
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.taglib.search.JSPSearchEntry;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * @author Raymond Augé
 */
public class SearchContainerColumnJSPTag<R> extends SearchContainerColumnTag {

	@Override
	public int doEndTag() {
		try {
			SearchContainerRowTag<R> searchContainerRowTag =
				(SearchContainerRowTag<R>)findAncestorWithClass(
					this, SearchContainerRowTag.class);

			ResultRow resultRow = searchContainerRowTag.getRow();

			if (index <= -1) {
				List<SearchEntry> searchEntries = resultRow.getEntries();

				index = searchEntries.size();
			}

			JSPSearchEntry jspSearchEntry = new JSPSearchEntry();

			jspSearchEntry.setAlign(getAlign());
			jspSearchEntry.setColspan(getColspan());
			jspSearchEntry.setCssClass(getCssClass());
			jspSearchEntry.setHref(String.valueOf(getHref()));
			jspSearchEntry.setPath(getPath());
			jspSearchEntry.setRequest(
				(HttpServletRequest)pageContext.getRequest());
			jspSearchEntry.setResponse(
				(HttpServletResponse)pageContext.getResponse());
			jspSearchEntry.setServletContext(pageContext.getServletContext());
			jspSearchEntry.setTruncate(getTruncate());
			jspSearchEntry.setValign(getValign());

			resultRow.addSearchEntry(index, jspSearchEntry);

			return EVAL_PAGE;
		}
		finally {
			index = -1;

			align = SearchEntry.DEFAULT_ALIGN;
			colspan = SearchEntry.DEFAULT_COLSPAN;
			cssClass = SearchEntry.DEFAULT_CSS_CLASS;
			name = StringPool.BLANK;
			_path = null;
			valign = SearchEntry.DEFAULT_VALIGN;
		}
	}

	@Override
	public int doStartTag() throws JspException {
		SearchContainerRowTag<R> searchContainerRowTag =
			(SearchContainerRowTag<R>)findAncestorWithClass(
				this, SearchContainerRowTag.class);

		if (searchContainerRowTag == null) {
			throw new JspTagException(
				"Requires liferay-ui:search-container-row");
		}

		if (!searchContainerRowTag.isHeaderNamesAssigned()) {
			List<String> headerNames = searchContainerRowTag.getHeaderNames();

			headerNames.add(name);
		}

		return EVAL_BODY_INCLUDE;
	}

	public Object getHref() {
		if (_href instanceof PortletURL) {
			_href = _href.toString();
		}

		return _href;
	}

	public String getPath() {
		return _path;
	}

	public void setHref(Object href) {
		_href = href;
	}

	public void setPath(String path) {
		_path = path;
	}

	private Object _href;
	private String _path;

}