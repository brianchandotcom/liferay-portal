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

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.search.IconSearchEntry;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * @author Eudaldo Alonso
 */
public class SearchContainerColumnIconTag<R> extends SearchContainerColumnTag {

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

			if (resultRow.isRestricted()) {
				_href = null;
			}

			IconSearchEntry iconSearchEntry = new IconSearchEntry();

			iconSearchEntry.setAlign(getAlign());
			iconSearchEntry.setColspan(getColspan());
			iconSearchEntry.setCssClass(getCssClass());
			iconSearchEntry.setRequest(
				(HttpServletRequest)pageContext.getRequest());
			iconSearchEntry.setResponse(
				(HttpServletResponse)pageContext.getResponse());
			iconSearchEntry.setToggleRowChecker(isToggleRowChecker());

			ServletContext servletContext = ServletContextPool.get(
				PortalUtil.getServletContextName());

			iconSearchEntry.setServletContext(servletContext);

			iconSearchEntry.setIcon(_icon);
			iconSearchEntry.setValign(getValign());

			resultRow.addSearchEntry(index, iconSearchEntry);

			return EVAL_PAGE;
		}
		finally {
			index = -1;
			_icon = null;

			align = SearchEntry.DEFAULT_ALIGN;
			colspan = SearchEntry.DEFAULT_COLSPAN;
			cssClass = SearchEntry.DEFAULT_CSS_CLASS;
			_href = null;
			name = null;
			_toggleRowChecker = false;
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
			String name = getName();

			if (Validator.isNotNull(name)) {
				List<String> headerNames =
					searchContainerRowTag.getHeaderNames();

				headerNames.add(name);
			}
		}

		return EVAL_BODY_INCLUDE;
	}

	public Object getHref() {
		if (_href instanceof PortletURL) {
			_href = _href.toString();
		}

		return _href;
	}

	public String getIcon() {
		return _icon;
	}

	public boolean isToggleRowChecker() {
		return _toggleRowChecker;
	}

	public void setHref(Object href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setToggleRowChecker(boolean toggleRowChecker) {
		_toggleRowChecker = toggleRowChecker;
	}

	private Object _href;
	private String _icon;
	private boolean _toggleRowChecker;

}